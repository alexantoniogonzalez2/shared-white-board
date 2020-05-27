package client;


import remote.Manager;
import whiteboard.GUI;
import whiteboard.EditorList;
import whiteboard.Whiteboard;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.*;

public class JoinWhiteboard {

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

        try {

            //Lookup for the service
            String url = "rmi://" + ip + ":" + port + "/sharedWhiteboard";
            Remote remoteService = Naming.lookup(url);

            Manager remoteManager = (Manager) remoteService;
            ImplementUser user = new ImplementUser(username);
            Whiteboard whiteboard = new Whiteboard();
            EditorList editorList = new EditorList();


            //Create a temperature monitor and register it as a Listener
            user.setEditorList(editorList);
            editorList.setRemoteManager(remoteManager);
            whiteboard.setRemoteManager(remoteManager);

            remoteManager.addUser(user);
            user.setWhiteBoard(whiteboard);

            GUI userGUI = new GUI("User");
            whiteboard.loadObjects();
            editorList.loadUsers();
            userGUI.initGUI(whiteboard, editorList);
            userGUI.setVisible(true);


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
            e.printStackTrace();
        } catch (NotBoundException e) { //lookup
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}