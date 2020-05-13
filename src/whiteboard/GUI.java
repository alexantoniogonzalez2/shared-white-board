package whiteboard;

import client.ClientWhiteboard;
import remote.IWhiteboardEdition;
import server.ServerWhiteboard;
import server.WhiteboardEdition;
import whiteboard.Whiteboard;

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

    public GUI(String title, String type){
        setTitle(title);


        if (type.equals("client")) {
            ClientWhiteboard clientWhiteboard = new ClientWhiteboard();
            clientWhiteboard.init();
            this.whiteboard = clientWhiteboard;
            this.setLocation(660,70);
        }
        else {
            this.setLocation(40,70);
            this.whiteboard = new ServerWhiteboard();
            try {
                IWhiteboardEdition remoteEdition = null;

                remoteEdition = new WhiteboardEdition(); // throws RemoteException
                remoteEdition.setWhiteboard(this.whiteboard);

                Registry registry = LocateRegistry.getRegistry();
                registry.bind("WhiteboardEdition", remoteEdition);
                System.out.println("Remote edition ready");

            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (AlreadyBoundException e) {
                e.printStackTrace();
            }

        }


    }


   public void initGUI(){

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