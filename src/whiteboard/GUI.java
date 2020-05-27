package whiteboard;

import remote.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame {
    private JButton lineButton;
    private JButton circleButton;
    private JButton rectangleButton;
    private JButton textButton;
    private JPanel actions;
    private JPanel descriptionPanel;
    private JPanel mainPanel;
    private JPanel whiteboardPanel;
    private JPanel footPanel;
    private JPanel userListPanel;
    private Whiteboard whiteboard;
    private EditorList editorList;

    private String title;

    public GUI(String type){
        if (type.equals("User"))
            this.setLocation(680,70);
        else
            this.setLocation(10,70);

        this.title = type;
        setTitle(this.title);
    }

   public void initGUI(Whiteboard whiteboard, EditorList ul){

       this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

       this.whiteboard = whiteboard;
       this.whiteboardPanel.setLayout(new GridLayout());
       this.whiteboardPanel.add(this.whiteboard);

       this.editorList = ul;
       this.userListPanel.setLayout(new GridLayout());
       this.userListPanel.add(ul);

       this.add(mainPanel);
       repaint();


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