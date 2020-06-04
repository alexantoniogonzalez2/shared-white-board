// Author: Alex Gonzalez Login ID: aagonzalez
// Purpose: Assignment 2 - COMP90015: Distributed Systems

package remote;
// Libraries
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteUser extends Remote {
    void newObject(Object object) throws RemoteException;

    void setId(int id) throws RemoteException;

    void reloadUser() throws RemoteException;

    int getId() throws RemoteException;

    String getUsername() throws RemoteException;

    void notifyKickOut() throws RemoteException;

    void notifyCloseApplication() throws RemoteException;

    void reloadObject() throws RemoteException;

    void setEnable(boolean enable) throws RemoteException;
}