// Author: Alex Gonzalez Login ID: aagonzalez
// Purpose: Assignment 2 - COMP90015: Distributed Systems

package whiteboard;

import remote.RemoteManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JButton lineButton;
    private JButton circleButton;
    private JButton rectangleButton;
    private JButton textButton;
    private JPanel actions;
    private JPanel headerPanel;
    private JPanel mainPanel;
    private JPanel whiteboardPanel;
    private JPanel footPanel;
    private JPanel userListPanel;
    private JPanel kickOutPanel;
    private JLabel kickOutLabel;
    private JButton kickOutButton;
    private JTextField kickOutField;
    private JPanel filePanel;
    private JButton closeButton;
    private JButton saveAsButton;
    private JButton saveButton;
    private JButton openButton;
    private JButton newButton;
    private JLabel fileLabel;
    private Whiteboard whiteboard;
    private ListEditor listEditor;
    private String type;
    private String title;
    private RemoteManager remoteManager;
    private FileManager fileManager;

    public GUI(String type, String username){

        this.fileManager = new FileManager();
        this.type = type;
        if (type.equals("User"))
            this.setLocation(680,70);
        else
            this.setLocation(10,70);

        this.title = type + ": " + username;
        setTitle(this.title);
    }

   public void initGUI(Whiteboard whiteboard, ListEditor listEditor, RemoteManager remoteManager){

       this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

       this.whiteboard = whiteboard;
       this.whiteboardPanel.setLayout(new GridLayout());
       this.whiteboardPanel.add(this.whiteboard);

       this.listEditor = listEditor;
       this.userListPanel.setLayout(new GridLayout());
       this.userListPanel.add(listEditor);

       this.remoteManager = remoteManager;
       try {
           this.remoteManager.setEnable(true);
       } catch (RemoteException remoteException) {
           remoteException.printStackTrace();
       }

       if (type.equals("User")) {
           this.footPanel.remove(kickOutPanel);
           this.headerPanel.remove(filePanel);
           this.headerPanel.remove(fileLabel);
       }

       this.add(mainPanel);
       this.repaint();
       this.pack();

       // Actions and listeners for drawing buttons
       lineButton.setActionCommand("line");
       circleButton.setActionCommand("circle");
       rectangleButton.setActionCommand("rectangle");
       textButton.setActionCommand("text");

       class drawActionListener implements ActionListener {
           @Override
           public void actionPerformed(ActionEvent e) {
               //if (open)
                   whiteboard.setAction(e.getActionCommand());
           }
       }
       drawActionListener drawAction = new drawActionListener();
       lineButton.addActionListener(drawAction);
       circleButton.addActionListener(drawAction);
       rectangleButton.addActionListener(drawAction);
       textButton.addActionListener(drawAction);

       kickOutButton.addActionListener( new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent e) {

               String input = kickOutField.getText();
               int userId = 0;
               try {
                   userId = Integer.parseInt(input);
               } catch (NumberFormatException exception){
                   errorMessage("enter_integer");
               }

               if (userId == 1) {
                   errorMessage("no_kickoff_yourself");
               }
               else if (userId != 0) {
                   boolean result = false;
                   try {
                       result = remoteManager.kickOffUser(userId);
                   } catch (RemoteException remoteException) {
                       remoteException.printStackTrace();
                   }
                   if (!result)
                       errorMessage("user_no_exists");
               }
           }
       });

       newButton.addActionListener(e -> newWhiteboard());
       openButton.addActionListener(e -> openWhiteboard());
       saveButton.addActionListener(e -> saveWhiteboard());
       saveAsButton.addActionListener(e -> saveAsWhiteboard());
       closeButton.addActionListener(e -> closeWhiteboard());

   }

    private void newWhiteboard() {
        closeWhiteboard();
        try {
            this.remoteManager.setEnable(true);
        } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
        }
        //this.open = true;
        this.fileLabel.setText("File: New Whiteboard");

    }

    private void openWhiteboard() {

        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                this.remoteManager.setEnable(true);
                this.remoteManager.setObjects(this.fileManager.open(file.getParent(),file.getName()));
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        }
        if (this.fileManager.getFileName() != null)
            this.fileLabel.setText("File: " + this.fileManager.getFileName());

    }

    private void saveWhiteboard() {
        try {
            if (this.fileManager.hasFilePath())
                this.fileManager.save(this.remoteManager.getObjects());
            else
                saveAsWhiteboard();
        } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
        }
    }

    private void saveAsWhiteboard() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                this.fileManager.saveAs(this.remoteManager.getObjects(),file.getParent(),file.getName());
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        }
        if (this.fileManager.getFileName() != null)
            this.fileLabel.setText("File: " + this.fileManager.getFileName());

    }

    private void closeWhiteboard() {
        this.fileManager.deleteFilePath();

        this.fileLabel.setText("File:");
        try {
            this.remoteManager.setObjects(new ArrayList<>());
        } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
        }
        try {
            this.remoteManager.setEnable(false);
        } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
        }
        //this.open = false;
    }

    // Method used for showing error information to the manager.
    protected static void errorMessage (String type) {
        String errorMsg = "";
        switch (type) {
            case "enter_integer":
                errorMsg = "Please provide an integer number.";
                break;
            case "no_kickoff_yourself":
                errorMsg = "You entered your own id (1). It is not allowed to kick out yourself.";
                break;
            case "user_no_exists":
                errorMsg = "The id provided does not match any user.";
                break;

            default:
                System.out.println("No tracked error.");

        }
        JOptionPane.showMessageDialog(new JFrame(),errorMsg ,"Error", JOptionPane.ERROR_MESSAGE);
    }

}