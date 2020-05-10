package server;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

class Whiteboard extends JPanel {
    ArrayList<Shape> objects = new ArrayList<Shape>();
    Line2D line = null;
    Ellipse2D circle = null;
    Rectangle rectangle = null;
    TextArea text = null;

    private String action = "line";


    public Whiteboard(){


        class MyListener extends MouseInputAdapter {
            public void mousePressed(MouseEvent e) {

                int x = e.getX();
                int y = e.getY();

                switch(action){
                    case "line":
                        line = new Line2D.Double(x, y, 0, 0);
                        break;
                    case "circle":
                        circle = new Ellipse2D.Double(x, y, 0, 0);
                        break;
                    case "rectangle":
                        rectangle = new Rectangle(x, y, 0, 0);
                        break;
                    case "text":
                    default:
                        break;

                }

                //System.out.println("Action"+action);
                //int x = e.getX();
                //int y = e.getY();
                //rectangle = new Rectangle(x, y, 0, 0);

                //updateDrawableRect(getWidth(), getHeight());
                //repaint();
            }

            public void mouseDragged(MouseEvent e) {
                updateSize(e);
            }

            public void mouseReleased(MouseEvent e) {
                updateSize(e);
            }

            void updateSize(MouseEvent e) {

                int x = e.getX();
                int y = e.getY();

                switch(action){
                    case "line":
                        line.setLine(line.getX1(),line.getY1(),x,y);
                        objects.add(line);
                        break;
                    case "circle":
                        circle.setFrame(circle.getX(),circle.getY(),x-circle.getX(),y-circle.getY());
                        objects.add(circle);
                        break;
                    case "rectangle":
                        //rectangle = new Rectangle(x, y, 0, 0);
                        rectangle.setSize(x - rectangle.x, y - rectangle.y);
                        objects.add(rectangle);
                        break;
                    default:
                        break;

                }

                repaint();
                //objects.add(currentRect);

            }

            void createObject(int x, int y, String action){


            }


        }

        MyListener myListener = new MyListener();
        addMouseListener(myListener);
        addMouseMotionListener(myListener);
    }
    public void setAction(String action){
        this.action = action;
    }


    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();

        super.paintComponent(g2d);
        setBackground(Color.white);

        for (Shape item : objects) {
            g2d.draw(item);
        }

    }
}
