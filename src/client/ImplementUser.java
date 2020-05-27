package client;

import remote.User;
import whiteboard.EditorList;
import whiteboard.Whiteboard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImplementUser extends UnicastRemoteObject implements User {

    private int id;
    private String username;
    private Whiteboard whiteboard;
    private EditorList editorList;

    public ImplementUser(String username) throws RemoteException {
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
}
