// Author: Alex Gonzalez Login ID: aagonzalez
// Purpose: Assignment 2 - COMP90015: Distributed Systems

package server;
// Classes and interfaces
import client.User;
import remote.RemoteManager;
import whiteboard.GUI;
import whiteboard.ListEditor;
import whiteboard.Whiteboard;
// Libraries
import javax.swing.*;
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
            showMessage("wrong_number_parameters");
        } catch (NumberFormatException e){
            showMessage("wrong_type_number");
        }

        // Server launch. The remote service is published.
        try {
            RemoteManager remoteManager = new Manager();
            LocateRegistry.createRegistry(port);
            String url = "rmi://" + ip + ":" + port + "/sharedWhiteboard";
            Naming.rebind(url, remoteManager);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        // A client is launched for the Manager. It becomes the first user.
        try {

            //Lookup for the service
            String url = "rmi://" + ip + ":" + port + "/sharedWhiteboard";
            Remote remoteService = Naming.lookup(url);

            // It is created: a Remote Manager, a User, a Whiteboard and a ListEditor.
            RemoteManager remoteManager = (RemoteManager) remoteService;
            User user = new User(username);
            Whiteboard whiteboard = new Whiteboard();
            ListEditor listEditor = new ListEditor();

            // The remote manager is passed to the whiteboard to receive method calls.
            user.setListEditor(listEditor);
            listEditor.setRemoteManager(remoteManager);
            whiteboard.setRemoteManager(remoteManager);

            // The Remote Manager receives an User for notifications.
            remoteManager.addUser(user);
            user.setWhiteBoard(whiteboard);

            // GUI launch
            GUI userGUI = new GUI("Manager",username);
            whiteboard.loadObjects();
            listEditor.loadUsers();
            userGUI.initGUI(whiteboard, listEditor, remoteManager);
            userGUI.setVisible(true);

            // Listener used when the client closes the connection. It sent a notification to Remote Manager.
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

        } catch (RemoteException e) { //various methods
            showMessage("not_connection");
        } catch (NotBoundException e) { //lookup
            showMessage("not_connection");
        } catch (MalformedURLException e) {
            showMessage("not_connection");
        }

    }

    // Message dialogs to communicate errors.
    protected static void showMessage (String type) {

        String errorMsg = "";

        switch (type) {
            case "wrong_number_parameters":
                errorMsg = "It was expected at least three arguments: host, port number (integer) " +
                        "and username.";
                break;
            case "wrong_type_number":
                errorMsg = "It was expected an integer number for the port argument.";
                break;
            case "not_approval":
                errorMsg = "Your access was not authorized.";
                break;
            case "not_connection":
                errorMsg = "It was not possible to connect to a remote server with the parameters provided.";
                break;
            default:
                System.out.println("No tracked error.");

        }

        JOptionPane.showMessageDialog(new JFrame(),errorMsg ,"Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);

    }
}