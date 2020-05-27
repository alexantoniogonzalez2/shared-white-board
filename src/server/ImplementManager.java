package server;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import client.ImplementUser;
import remote.Manager;
import remote.User;

public class ImplementManager extends UnicastRemoteObject implements Manager {

    private ArrayList<Object> objects = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private int idCount = 0;

    public ImplementManager() throws RemoteException {
    }

    @Override
    public ArrayList<Object> getObjects(){
        return this.objects;
    }

    @Override
    public void addObject(Object object){
        this.objects.add(object);
        notifyNewObject(object);
    }

    private void notifyNewObject(Object object) {
        for (User user : users)
            try {
                user.newObject(object);
            } catch (RemoteException exception) {
                users.remove(user);
            }
    }

    @Override
    public ArrayList<User> getUsers() {
        return this.users;
    }

    @Override
    public void addUser(User user) {
        try {
            user.setId(idCount);
        } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
        }
        users.add(user);
        this.idCount++;
        notifyReloadUser();
    }

    private void notifyReloadUser() {
        for (User user : users) {
            try {
                user.reloadUser();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        }
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
        notifyReloadUser();
    }


}