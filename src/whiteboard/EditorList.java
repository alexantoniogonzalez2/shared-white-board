package whiteboard;

import remote.RemoteManager;
import remote.RemoteUser;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class EditorList extends JPanel {

    private ArrayList<RemoteUser> remoteUsers = new ArrayList<>();
    private RemoteManager remoteManager = null;
    JTextArea textArea = new JTextArea();

    public EditorList(){
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setBorder(BorderFactory.createEmptyBorder());
        add(textArea);
    }

    public void loadUsers(){

        textArea.setText("");
        textArea.append("Users:\n");
        try {
            this.remoteUsers = this.remoteManager.getRemoteUsers();

            for (RemoteUser remoteUser : remoteUsers) {
                if (remoteUser.getId() == 1)
                    textArea.append("1: "+ remoteUser.getUsername()+ " (M)" + "\n");
                else
                    textArea.append(remoteUser.getId() + ": " + remoteUser.getUsername() + "\n");
            }

        } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
        }

        textArea.repaint();
        repaint();

    }

    public void setRemoteManager(RemoteManager remoteManager){
        this.remoteManager = remoteManager;
    }

}
