package whiteboard;

import remote.IRemoteEdition;
import remote.RemoteListener;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.rmi.RemoteException;
import java.util.ArrayList;


public class Whiteboard extends JPanel implements RemoteListener {
    private ArrayList<Object> objects = new ArrayList<Object>();
    private Line2D line = null;
    private Ellipse2D circle = null;
    private Rectangle rectangle = null;
    private String action = "line";
    private IRemoteEdition remoteEdition = null;

    public Whiteboard() throws RemoteException {

        class MyListener extends MouseInputAdapter {
            public void mousePressed(MouseEvent e) {

                int x = e.getX();
                int y = e.getY();

                switch(action){
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
                        text.setBounds(x,y,100,50);
                        text.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                        text.setOpaque(false);
                        add(text);
                        repaint();
                        text.requestFocus();
                        try {
                            text.setText("dummy");
                            addRemoteText(text);
                        } catch (RemoteException remoteException) {
                            remoteException.printStackTrace();
                        }
                        // TODO
                        //- Listener for lose focus after an enter and sent text
                        //- Listener for limit the characters: https://stackoverflow.com/questions/3519151/how-to-limit-the-number-of-characters-in-jtextfield
                        //- Insert text: correct Y position
                        break;
                    default:
                        break;
                }
            }

            public void mouseDragged(MouseEvent e) {
                updateSize(e,"dragged");
            }

            public void mouseReleased(MouseEvent e) {
                updateSize(e,"released");
            }
        }

        MyListener myListener = new MyListener();
        addMouseListener(myListener);
        addMouseMotionListener(myListener);
    }

    public void setRemoteEdition(IRemoteEdition remote){
        this.remoteEdition = remote;
    }

    public void loadObjects(){
        try {
            this.objects = this.remoteEdition.getObjects();
        } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
        }
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
                        addRemoteShape(line);
                    break;
                case "circle":
                    x1 = circle.getX();
                    y1 = circle.getY();
                    circle.setFrameFromDiagonal(x1, y1, x2, y2);
                    if (mouseAction.equals("released"))
                        addRemoteShape(circle);
                    break;
                case "rectangle":
                    x1 = rectangle.getX();
                    y1 = rectangle.getY();
                    rectangle.setFrameFromDiagonal(x1, y1, x2, y2);
                    if (mouseAction.equals("released"))
                        addRemoteShape(rectangle);
                    break;
                default:
                    break;
            }
        } catch (RemoteException remoteException) {

        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();

        super.paintComponent(g2d);
        setBackground(Color.white);

        for (Object item : objects) {
            if (item instanceof Shape)
                g2d.draw((Shape)item);
            else
                add((JTextField)item);
        }
    }

    @Override
    public void temperatureChanged(Shape shape) throws RemoteException {
        //System.out.println("5");

        //System.out.println(shape.toString());
        //System.out.println(shape);

        objects.add(shape);
        repaint();

    }

    public void addRemoteShape(Shape shape) throws RemoteException {
        this.remoteEdition.addShape(shape);
    }

    public void addRemoteText(JTextField text) throws RemoteException {
        this.remoteEdition.addText(text);
    }

    public void setAction(String action){
        this.action = action;
    }

}