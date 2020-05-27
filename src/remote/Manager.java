package remote;

import client.ImplementUser;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Manager extends Remote {

    void addObject(Object object) throws RemoteException;

    ArrayList<Object> getObjects() throws RemoteException;

    void addUser(User user) throws RemoteException;

    ArrayList<User> getUsers() throws RemoteException;

    void removeUser(User user) throws RemoteException;

}
