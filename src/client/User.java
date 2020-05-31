package client;

import remote.RemoteUser;
import whiteboard.EditorList;
import whiteboard.Whiteboard;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class User extends UnicastRemoteObject implements RemoteUser {

    private int id;
    private String username;
    private Whiteboard whiteboard;
    private EditorList editorList;

    public User(String username) throws RemoteException {
        this.username = username;
    }

    @Override
    public void setId(int id){
        this.id = id;
    }

    @Override
    public void newObject(Object object) {
        this.whiteboard.newObject(object);
    }

    @Override
    public void reloadUser() {
        this.editorList.loadUsers();
    }

    public void setWhiteBoard(Whiteboard whiteboard) {
        this.whiteboard = whiteboard;
    }

    public void setEditorList(EditorList editorList) {
        this.editorList = editorList;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void notifyKickOff() {

        String errorMsg = "The manager has kicked you out of the whiteboard.";
        Thread t = new Thread(new Runnable(){
            public void run(){
                JOptionPane.showMessageDialog(new JFrame(),errorMsg ,"Kicked out", JOptionPane.INFORMATION_MESSAGE);
                System.exit(1);
            }
        });
        t.start();

    }

    @Override
    public void notifyCloseApplication()  {
        String errorMsg = "The manager has closed the whiteboard.";
        Thread t = new Thread(new Runnable(){
            public void run(){
                JOptionPane.showMessageDialog(new JFrame(),errorMsg ,"Whiteboard closed", JOptionPane.INFORMATION_MESSAGE);
                System.exit(1);
            }
        });
        t.start();

    }

    @Override
    public void reloadObject()  {
        this.whiteboard.loadObjects();
    }

    @Override
    public void setEnable(boolean enable) {
        this.whiteboard.setEnable(enable);
    }
}
