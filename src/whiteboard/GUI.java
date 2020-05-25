package whiteboard;

import remote.IRemoteEdition;
import server.RemoteEdition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GUI extends JFrame {
    private JButton lineButton;
    private JButton circleButton;
    private JButton rectangleButton;
    private JButton textButton;
    private JPanel notificationPanel;
    private JPanel actions;
    private JPanel descriptionPanel;
    private JPanel mainPanel;
    private JPanel whiteboardPanel;
    private JPanel footPanel;
    private Whiteboard whiteboard;
    private String title;

    public GUI(String type){
        if (type.equals("client")) {
            this.setLocation(660,70);
            this.title = "Client";
        }
        else {
            this.setLocation(40,70);
            this.title = "Manager";
        }
        setTitle(this.title);
    }

   public void initGUI(Whiteboard whiteboard){
       this.whiteboard = whiteboard;

       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.whiteboardPanel.setLayout(new GridLayout());

       this.whiteboardPanel.add(whiteboard);
       this.add(mainPanel);

       lineButton.setActionCommand("line");
       circleButton.setActionCommand("circle");
       rectangleButton.setActionCommand("rectangle");
       textButton.setActionCommand("text");

       this.pack();

       class drawActionListener implements ActionListener {

           @Override
           public void actionPerformed(ActionEvent e) {
               whiteboard.setAction(e.getActionCommand());
           }
       }

       drawActionListener drawAction = new drawActionListener();
       lineButton.addActionListener(drawAction);
       circleButton.addActionListener(drawAction);
       rectangleButton.addActionListener(drawAction);
       textButton.addActionListener(drawAction);

    }

}