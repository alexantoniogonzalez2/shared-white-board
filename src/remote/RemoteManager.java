// Author: Alex Gonzalez Login ID: aagonzalez
// Purpose: Assignment 2 - COMP90015: Distributed Systems

package remote;
// Libraries
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteManager extends Remote {

    void addObject(Object object) throws RemoteException;

    ArrayList<Object> getObjects() throws RemoteException;

    void addUser(RemoteUser remoteUser) throws RemoteException;

    ArrayList<RemoteUser> getRemoteUsers() throws RemoteException;

    void removeUser(RemoteUser remoteUser) throws RemoteException;

    boolean getApproval(String username) throws RemoteException;

    boolean kickOffUser(int userId) throws RemoteException;

    void closeApplication() throws RemoteException;

    void setObjects(ArrayList<Object> objects) throws RemoteException;

    void setEnable(boolean enable) throws RemoteException;
}
