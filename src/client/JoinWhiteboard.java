package client;


import whiteboard.GUI;

public class JoinWhiteboard {

    public static void main(String[] args){

        System.out.println("Server running...");

        GUI clientGUI = new GUI("Client", "client");
        clientGUI.initGUI();
        clientGUI.setVisible(true);


    }
}