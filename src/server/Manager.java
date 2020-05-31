package server;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import remote.RemoteManager;
import remote.RemoteUser;

import javax.swing.*;

public class Manager extends UnicastRemoteObject implements RemoteManager {

    private ArrayList<Object> objects = new ArrayList<>();
    private ArrayList<RemoteUser> remoteUsers = new ArrayList<>();
    private int idCount = 0;

    public Manager() throws RemoteException {
    }

    @Override
    public ArrayList<Object> getObjects(){
        return this.objects;
    }

    @Override
    public void addObject(Object object){
        this.objects.add(object);
        this.notifyNewObject(object);
    }

    private void notifyNewObject(Object object) {
        for (RemoteUser remoteUser : remoteUsers)
            try {
                remoteUser.newObject(object);
            } catch (RemoteException exception) {
                remoteUsers.remove(remoteUser);
            }
    }

    @Override
    public ArrayList<RemoteUser> getRemoteUsers() {
        return this.remoteUsers;
    }

    @Override
    public void addUser(RemoteUser remoteUser) {
        this.idCount++;

        try {
            remoteUser.setId(idCount);
        } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
        }
        this.remoteUsers.add(remoteUser);
        this.notifyReloadUser();
    }

    private void notifyReloadUser() {
        for (RemoteUser remoteUser : remoteUsers) {
            try {
                remoteUser.reloadUser();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        }
    }

    @Override
    public void removeUser(RemoteUser remoteUser) {
        remoteUsers.remove(remoteUser);
        notifyReloadUser();
    }

    @Override
    public boolean getApproval(String username) {
        boolean answer = false;

        int result = JOptionPane.showOptionDialog(null,
                username + " is requesting access to the current board.",
                "Do you authorize their access? ",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Yes", "No"},
                "Yes");
        if (result == 0 )
            answer = true;

        return answer;
    }

    @Override
    public boolean kickOffUser(int userId) {

        boolean found = false;
        for (RemoteUser remoteUser : remoteUsers){
            try {
                if (remoteUser.getId() == userId){
                    found = true;
                    removeUser(remoteUser);
                    remoteUser.notifyKickOff();
                }
            } catch (RemoteException exception) {
                exception.printStackTrace();
            }
        }

        return found;

    }

    @Override
    public void closeApplication() {
        for (RemoteUser remoteUser : remoteUsers){
            try {
                remoteUser.notifyCloseApplication();
            } catch (RemoteException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void setObjects(ArrayList<Object> new_objects) {
        this.objects = new_objects;
        notifyReloadObject();

    }

    @Override
    public void setEnable(boolean color) {
        for (RemoteUser remoteUser : remoteUsers) {
            try {
                remoteUser.setEnable(color);
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        }

    }

    private void notifyReloadObject() {
        for (RemoteUser remoteUser : remoteUsers) {
            try {
                remoteUser.reloadObject();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        }
    }

}