package server;

import remote.IRemoteEdition;
import whiteboard.GUI;
import whiteboard.Whiteboard;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CreateWhiteboard {

    public static void main(String[] args){

        System.out.println("Server running...");
        GUI serverGUI = new GUI("server");

        // Server
        try {
            IRemoteEdition remoteEdition = new RemoteEdition(); // throws RemoteException
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("WhiteboardEdition", remoteEdition);
            System.out.println("Remote edition ready");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }

        // Client
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            IRemoteEdition clientRemoteEdition = (IRemoteEdition)registry.lookup("WhiteboardEdition");
            System.out.println("Board ready for edition");

            Whiteboard whiteboard = new Whiteboard();
            clientRemoteEdition.addTemperatureListener(whiteboard);

            whiteboard.setRemoteEdition(clientRemoteEdition);
            whiteboard.loadObjects();

            serverGUI.initGUI(whiteboard);
            serverGUI.setVisible(true);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}