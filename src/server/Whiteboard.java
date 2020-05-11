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

    private String action = "";

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
                        JTextField text = new JTextField();
                        text.setBounds(x,y,100,50);
                        text.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                        text.setOpaque(false);
                        add(text);
                        repaint();
                        text.requestFocus();
                        // TODO
                        //- Listener for lose focus after an enter
                        //- Listener for limit the characters: https://stackoverflow.com/questions/3519151/how-to-limit-the-number-of-characters-in-jtextfield
                        break;
                    default:
                        break;

                }

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
                        rectangle.setSize(x - rectangle.x, y - rectangle.y);
                        objects.add(rectangle);
                        break;
                    default:
                        break;

                }

                repaint();

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
