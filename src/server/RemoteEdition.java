package server;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import remote.IRemoteEdition;
import remote.RemoteListener;

import javax.swing.*;

public class RemoteEdition extends UnicastRemoteObject implements IRemoteEdition {

    private ArrayList<Object> objects = new ArrayList<Object>();
    private List<RemoteListener> listeners = new ArrayList<>();


    public RemoteEdition() throws RemoteException {}

    public void addShape(Shape shape){
        this.objects.add(shape);
        //System.out.println("1");
        notifyTemperatureListeners(shape);

    }

    public void addText(JTextField text){

        /*JTextField text = new JTextField();
        text.setBounds(x1,y1,100,50);
        text.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        text.setOpaque(false);
        text.setText(string);*/
        objects.add(text);
    }

    public ArrayList<Object> getObjects(){
        return this.objects;
    }

    private void notifyTemperatureListeners(Shape shape) {
        //System.out.println("2");

        for (RemoteListener lListener : listeners)
        {
            //System.out.println("3");

            try {
                //System.out.println("4");

                lListener.temperatureChanged(shape);
            }
            catch (RemoteException aInE) {
                //System.out.println("e");

                listeners.remove(lListener);
            }
        }
    }

    @Override
    public void addTemperatureListener(RemoteListener temperatureListener) throws RemoteException
    {
        System.out.println("temperatureListener");
        System.out.println(temperatureListener);

        listeners.add(temperatureListener);
    }

    @Override
    public void removeTemperatureListener(RemoteListener temperatureListener) throws RemoteException
    {
        listeners.remove(temperatureListener);
    }


}