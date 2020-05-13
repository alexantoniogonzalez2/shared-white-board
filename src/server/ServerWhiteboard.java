package server;
import whiteboard.Whiteboard;

import java.awt.*;

import java.rmi.RemoteException;

import java.util.ArrayList;



public class ServerWhiteboard extends Whiteboard {

    @Override
    public void addRemoteLine(double x1, double y1, double x2, double y2) { }

    @Override
    public void addRemoteCircle(double x1, double y1, double x2, double y2) { }

    @Override
    public void addRemoteRectangle(double x1, double y1, double x2, double y2) { }

    @Override
    public void addRemoteText(int x1, int y1, String text) { }

    @Override
    public ArrayList<Shape> loadObjects() throws RemoteException {
        return new ArrayList<Shape>();
    }


}
