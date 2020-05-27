package remote;

import client.ImplementUser;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface User extends Remote {
    void newObject(Object object) throws RemoteException;

    void setId(int id) throws RemoteException;

    void reloadUser() throws RemoteException;

    int getId() throws RemoteException;

    String getUsername() throws RemoteException;
}