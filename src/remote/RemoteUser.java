package remote;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteUser extends Remote {
    void newObject(Object object) throws RemoteException;

    void setId(int id) throws RemoteException;

    void reloadUser() throws RemoteException;

    int getId() throws RemoteException;

    String getUsername() throws RemoteException;

    void notifyKickOff() throws RemoteException;

    void notifyCloseApplication() throws RemoteException;

    void reloadObject() throws RemoteException;

    void setEnable(boolean enable) throws RemoteException;
}