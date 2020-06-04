// Author: Alex Gonzalez Login ID: aagonzalez
// Purpose: Assignment 2 - COMP90015: Distributed Systems

package client;
// Classes and interfaces
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

public class JoinWhiteboard {

    public static void main(String[] args){

        // Reading the port number and the ip
        String ip = "", username = "";
        int port = 0;
        boolean approval;

        try {
            ip = args[0];
            port = Integer.parseInt(args[1]);
            username = args[2];
        } catch (ArrayIndexOutOfBoundsException e ){
            showMessage("wrong_number_parameters");
        } catch (NumberFormatException e){
            showMessage("wrong_type_number");
        }

        try {

            //Lookup for the service
            String url = "rmi://" + ip + ":" + port + "/sharedWhiteboard";
            Remote remoteService = Naming.lookup(url);

            // It is created: a Remote Manager and a User.
            RemoteManager remoteManager = (RemoteManager) remoteService;
            User user = new User(username);

            // Requesting manager approval
            approval = remoteManager.getApproval(username);
            if (!approval)
                showMessage("not_approval");

            // It is created: a Whiteboard and a List Editor.
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
            GUI userGUI = new GUI("User",username);
            whiteboard.loadObjects();
            listEditor.loadUsers();
            userGUI.initGUI(whiteboard, listEditor, remoteManager);
            userGUI.setVisible(true);

            // Listener used when the client closes the connection. It sent a notification to Remote Manager.
            userGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        remoteManager.removeUser(user);
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