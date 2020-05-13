package whiteboard;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.rmi.RemoteException;
import java.util.ArrayList;


public abstract class Whiteboard extends JPanel {
    private ArrayList<Shape> objects = new ArrayList<Shape>();
    private Line2D line = null;
    private Ellipse2D circle = null;
    private Rectangle rectangle = null;
    private String action = "line";

    public Whiteboard(){

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
                            addRemoteText(x, y, "dummy");
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

    public void init(){
        try {
            objects = loadObjects();
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
                        addRemoteLine(x1, y1, x2, y2);
                    break;
                case "circle":
                    x1 = circle.getX();
                    y1 = circle.getY();
                    circle.setFrameFromDiagonal(x1, y1, x2, y2);
                    if (mouseAction.equals("released"))
                        addRemoteCircle(x1, y1, x2, y2);
                    break;
                case "rectangle":
                    x1 = rectangle.getX();
                    y1 = rectangle.getY();
                    rectangle.setFrameFromDiagonal(x1, y1, x2, y2);
                    if (mouseAction.equals("released"))
                        addRemoteRectangle(x1, y1, x2, y2);
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
        int i =0;
        for (Shape item : objects) {
            //System.out.print("i: "+i);
            g2d.draw(item);
            i++;
        }
    }

    public abstract void addRemoteLine(double x1, double y1, double x2, double y2) throws RemoteException;
    public abstract void addRemoteCircle(double x1, double y1, double x2, double y2) throws RemoteException;
    public abstract void addRemoteRectangle(double x1, double y1, double x2, double y2) throws RemoteException;
    public abstract void addRemoteText(int x1, int y1, String text) throws RemoteException;
    public abstract ArrayList<Shape> loadObjects() throws RemoteException;

    public void addLine(double x1, double y1, double x2, double y2){
        objects.add(new Line2D.Double(x1, y1, x2, y2));
        repaint();
    }

    public void addCircle(double x1, double y1, double x2, double y2){
        Ellipse2D newCircle = new Ellipse2D.Double(x1,y1,0,0);
        newCircle.setFrameFromDiagonal(x1, y1, x2, y2);
        objects.add(newCircle);
        repaint();
    }

    public void addRectangle(double x1, double y1, double x2, double y2){
        Rectangle newRectangle = new Rectangle((int)x1,(int)y1,0,0);
        newRectangle.setFrameFromDiagonal(x1, y1, x2, y2);
        objects.add(newRectangle);
        repaint();
    }

    public void addText(int x1, int y1, String string){
        JTextField text = new JTextField();
        text.setBounds(x1,y1,100,50);
        text.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        text.setOpaque(false);
        add(text);
        repaint();
        text.requestFocus();
    }

    public void setAction(String action){
        this.action = action;
    }

    public void setObjects(ArrayList<Shape> objects){
        this.objects = objects;
    }

    public ArrayList<Shape> getObjects(){
        return objects;
    }

}