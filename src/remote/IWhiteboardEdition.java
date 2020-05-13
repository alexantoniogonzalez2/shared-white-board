package remote;
import server.ServerWhiteboard;
import whiteboard.Whiteboard;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IWhiteboardEdition extends Remote {

    void addLine(double x1, double y1, double x2, double y2) throws RemoteException;

    void addCircle(double x1, double y1, double x2, double y2) throws RemoteException;

    void addRectangle(double x1, double y1, double x2, double y2) throws RemoteException;

    void addText(int x1, int y1, String text) throws RemoteException;

    void setWhiteboard(Whiteboard whiteboard) throws RemoteException;

    ArrayList<Shape> getObjects() throws RemoteException;

}
