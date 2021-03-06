// Author: Alex Gonzalez Login ID: aagonzalez
// Purpose: Assignment 2 - COMP90015: Distributed Systems

package whiteboard;
// Classes and interfaces
import remote.RemoteManager;
// Libraries
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Whiteboard extends JPanel {
    private ArrayList<Object> objects = new ArrayList<>();
    private Line2D line = null;
    private Ellipse2D circle = null;
    private Rectangle rectangle = null;
    private String action = "line";
    private RemoteManager remoteManager = null;
    private boolean enable = true;

    public Whiteboard() {

        class TextListener implements FocusListener {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                processText((JTextField)e.getSource());
            }
        }

        class CustomKeyListener implements KeyListener{
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    processText((JTextField)e.getSource());
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        }

        class MouseListener extends MouseInputAdapter {
            public void mousePressed(MouseEvent e) {

                int x = e.getX();
                int y = e.getY();
                if (enable) {
                    switch (action) {
                        case "line":
                            line = new Line2D.Double(x, y, 0, 0);
                            objects.add(line);
                            break;
                        case "circle":
                            circle = new Ellipse2D.Double(x, y, 0, 0);
                            objects.add(circle);
                            break;
                        case "rectangle":
                            rectangle = new Rectangle(x, y, 0, 0);
                            objects.add(rectangle);
                            break;
                        case "text":
                            JTextField text = new JTextField();
                            text.setBounds(x, y-20, 100, 50);
                            text.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                            text.setOpaque(false);
                            add(text);
                            repaint();
                            text.requestFocus();

                            // The listener is added to the corresponding elements.
                            text.addFocusListener(new TextListener());
                            text.addKeyListener(new CustomKeyListener());

                            break;
                        default:
                            break;
                    }
                }
            }

            public void mouseDragged(MouseEvent e) {
                if (enable)
                    updateSize(e,"dragged");
            }

            public void mouseReleased(MouseEvent e) {
                if (enable)
                    updateSize(e,"released");
            }
        }

        MouseListener mouseListener = new MouseListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

    }

    public void setRemoteManager(RemoteManager remoteManager){
        this.remoteManager = remoteManager;
    }
    public void setAction(String action){
        this.action = action;
    }

    public void loadObjects(){
        try {
            this.objects = this.remoteManager.getObjects();
        } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
        }
        repaint();
    }

    public void updateSize(MouseEvent e, String mouseAction) {

        double x2 = e.getX();
        double y2 = e.getY();
        double x1, y1;

        try {
            switch (action) {
                case "line":
                    x1 = line.getX1();
                    y1 = line.getY1();
                    line.setLine(x1, y1, x2, y2);
                    if (mouseAction.equals("released"))
                        this.remoteManager.addObject(line);
                    break;
                case "circle":
                    x1 = circle.getX();
                    y1 = circle.getY();
                    circle.setFrameFromDiagonal(x1, y1, x2, y2);
                    if (mouseAction.equals("released"))
                        this.remoteManager.addObject(circle);
                    break;
                case "rectangle":
                    x1 = rectangle.getX();
                    y1 = rectangle.getY();
                    rectangle.setFrameFromDiagonal(x1, y1, x2, y2);
                    if (mouseAction.equals("released"))
                        this.remoteManager.addObject(rectangle);
                    break;
                default:
                    break;
            }
        } catch (RemoteException exception) {
            exception.printStackTrace();
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();

        super.paintComponent(g2d);

        for (Object item : objects) {
            if (item instanceof Shape)
                g2d.draw((Shape)item);
            else{
                JTextField text = ((JTextField)item);
                g2d.drawString(text.getText(),text.getX(),text.getY()+30);
            }
        }
    }

    public void processText(JTextField text){
        if (!text.getText().equals("")){
            try {
                this.remoteManager.addObject(text);
                this.remove(text);
            } catch (RemoteException exception) {
                exception.printStackTrace();
            }
        }

    }

    // Receive shapes
    public void newObject(Object object) {
        objects.add(object);
        repaint();
    }

    public void setEnable(boolean enable) {
        if (enable){
            setBackground(Color.white);
            this.enable = true;
        }
        else {
            setBackground(Color.lightGray);
            this.enable = false;
        }
        repaint();
    }
}