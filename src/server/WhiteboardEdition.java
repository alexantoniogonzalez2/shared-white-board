package server;

import whiteboard.Whiteboard;


import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import remote.IWhiteboardEdition;

public class WhiteboardEdition extends UnicastRemoteObject implements IWhiteboardEdition {

    Whiteboard serverWhiteboard;

    public WhiteboardEdition() throws RemoteException {
    }

    public void addLine(double x1, double y1, double x2, double y2){
        serverWhiteboard.addLine(x1, y1, x2, y2);
    }

    public void addCircle(double x1, double y1, double x2, double y2){
        serverWhiteboard.addCircle(x1, y1, x2, y2);
    }

    public void addRectangle(double x1, double y1, double x2, double y2){
        serverWhiteboard.addRectangle(x1, y1, x2, y2);
    }

    public void addText(int x1, int y1, String text){
        serverWhiteboard.addText(x1, y1, text);
    }

    public void setWhiteboard(Whiteboard w){
        this.serverWhiteboard = w;
    }

    public ArrayList<Shape> getObjects(){
        return this.serverWhiteboard.getObjects();
    }

}
