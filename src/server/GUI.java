package server;

import server.Whiteboard;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

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


    public void GUI(){



    }

    void initGUI(){
        setTitle("White Board");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(40,70);
        this.whiteboardPanel.setLayout(new GridLayout());
        Whiteboard whiteboard = new Whiteboard();
        this.whiteboardPanel.add(whiteboard);
        this.add(mainPanel);

        lineButton.setActionCommand("line");
        circleButton.setActionCommand("circle");
        rectangleButton.setActionCommand("rectangle");
        textButton.setActionCommand("text");

        this.pack();

        // Event listener for the 'query' feature.
        class queryActionListener implements ActionListener {
            // The actions performed include checking that inputs are correct.
            // A message will be send if everything is correct.
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.print(e.getActionCommand());
                whiteboard.setAction(e.getActionCommand());

                /*String inputText = queryField.getText();
                String selectedLng = dictionaries.getSelection().getActionCommand();
                boolean check = inputText.matches(allowed);*/



            }
        }
        // The listener is added to the corresponding elements.
        queryActionListener queryListener = new queryActionListener();
        lineButton.addActionListener(queryListener);
        circleButton.addActionListener(queryListener);
        rectangleButton.addActionListener(queryListener);
        textButton.addActionListener(queryListener);





    }


}
