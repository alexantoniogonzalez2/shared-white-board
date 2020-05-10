package server;

public class SharedWhiteboard {

    public static void main(String[] args){

        System.out.println("Server running...");


        server.GUI clientGUI = new server.GUI();
        clientGUI.initGUI();
        clientGUI.setVisible(true);


    }
}