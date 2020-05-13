package client;

import remote.IWhiteboardEdition;
import whiteboard.Whiteboard;

import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class ClientWhiteboard extends Whiteboard {

    private IWhiteboardEdition remoteEdition ;

    public ClientWhiteboard(){

        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            this.remoteEdition = (IWhiteboardEdition)registry.lookup("WhiteboardEdition");
            System.out.println("Client edition ready");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addRemoteLine(double x1, double y1, double x2, double y2) throws RemoteException {
        this.remoteEdition.addLine(x1,y1,x2,y2);
    }

    @Override
    public void addRemoteCircle(double x1, double y1, double x2, double y2) throws RemoteException {
        this.remoteEdition.addCircle(x1,y1,x2,y2);
    }

    @Override
    public void addRemoteRectangle(double x1, double y1, double x2, double y2) throws RemoteException {
        this.remoteEdition.addRectangle(x1,y1,x2,y2);
    }

    @Override
    public void addRemoteText(int x1, int y1, String text) throws RemoteException {
        this.remoteEdition.addText(x1,y1,text);

    }

    @Override
    public ArrayList<Shape> loadObjects() throws RemoteException {

        return this.remoteEdition.getObjects();
    }

}