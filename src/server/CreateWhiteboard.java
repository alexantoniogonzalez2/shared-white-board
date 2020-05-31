package server;

import client.User;
import remote.RemoteManager;
import whiteboard.GUI;
import whiteboard.EditorList;
import whiteboard.Whiteboard;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;

public class CreateWhiteboard {

    public static void main(String[] args){

        // Reading the port number and the ip
        String ip = "", username = "";
        int port = 0;

        try {
            ip = args[0];
            port = Integer.parseInt(args[1]);
            username = args[2];
        } catch (ArrayIndexOutOfBoundsException e ){
            //errorMessage("Wrong Number of Parameters");
            System.exit(1);
        } catch (NumberFormatException e){
            //errorMessage("Wrong Input Type for Port Number");
            System.exit(1);
        }


        // Server
        try {
            RemoteManager remoteManager = new Manager(); // throws RemoteException
            LocateRegistry.createRegistry(port);
            String url = "rmi://" + ip + ":" + port + "/sharedWhiteboard";
            Naming.rebind(url, remoteManager);
        } catch (RemoteException e) { // ImplRemoteEdition
            e.printStackTrace();
        } catch (MalformedURLException e) { //rebind
            e.printStackTrace();
        }


        // Client
        try {

            //Lookup for the service
            String url = "rmi://" + ip + ":" + port + "/sharedWhiteboard";
            Remote remoteService = Naming.lookup(url);

            RemoteManager remoteManager = (RemoteManager) remoteService;
            User user = new User(username);
            Whiteboard whiteboard = new Whiteboard();
            EditorList editorList = new EditorList();


            //Create a temperature monitor and register it as a Listener
            user.setEditorList(editorList);
            editorList.setRemoteManager(remoteManager);
            whiteboard.setRemoteManager(remoteManager);

            remoteManager.addUser(user);
            user.setWhiteBoard(whiteboard);

            GUI userGUI = new GUI("Manager",username);
            whiteboard.loadObjects();
            editorList.loadUsers();
            userGUI.initGUI(whiteboard, editorList, remoteManager);
            userGUI.setVisible(true);

            userGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        remoteManager.closeApplication();
                    } catch (RemoteException remoteException) {
                        remoteException.printStackTrace();
                    }
                    userGUI.setVisible(false);
                    userGUI.dispose();
                    System.exit(1);
                }
            });

        } catch (RemoteException e) { //Various methods
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }



    }
}