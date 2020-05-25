package client;


import remote.IRemoteEdition;
import whiteboard.GUI;
import whiteboard.Whiteboard;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class JoinWhiteboard {

    public static void main(String[] args){

        System.out.println("Server running...");
        GUI clientGUI = new GUI("client");
        
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            IRemoteEdition clientRemoteEdition = (IRemoteEdition)registry.lookup("WhiteboardEdition");
            System.out.println("Board ready for edition");

            Whiteboard whiteboard = new Whiteboard();
            clientRemoteEdition.addTemperatureListener(whiteboard);

            whiteboard.setRemoteEdition(clientRemoteEdition);
            whiteboard.loadObjects();
            
            clientGUI.initGUI(whiteboard);
            clientGUI.setVisible(true);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        
    }
}