package whiteboard;

import client.ImplementUser;
import remote.Manager;
import remote.User;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class EditorList extends JPanel {

    private ArrayList<User> users = new ArrayList<>();
    private Manager remoteManager = null;
    JTextArea textArea = new JTextArea();


    public void loadUsers(){

        textArea.setText("");
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setBorder(BorderFactory.createEmptyBorder());
        textArea.append("Users:\n");
        try {
            this.users = this.remoteManager.getUsers();

            for (User user : users)
                if (user.getId() == 0)
                    textArea.append("Manager: " + user.getUsername() + "\n");
                else
                    textArea.append(user.getId() + ": " + user.getUsername() + "\n");

        } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
        }

        add(textArea);
        repaint();

    }

    public void setRemoteManager(Manager manager){
        this.remoteManager = manager;
    }

}
