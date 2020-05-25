package remote;

import javax.swing.*;
import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IRemoteEdition extends Remote {

    void addShape(Shape shape) throws RemoteException;

    void addText(JTextField text) throws RemoteException;

    ArrayList<Object> getObjects() throws RemoteException;

    void addTemperatureListener(RemoteListener addTemperatureListener) throws RemoteException;

    void removeTemperatureListener(RemoteListener addTemperatureListener) throws RemoteException;


}
