package server;

import whiteboard.GUI;

public class CreateWhiteboard {

    public static void main(String[] args){

        System.out.println("Server running...");

        GUI serverGUI = new GUI("Manager","server");
        serverGUI.initGUI();
        serverGUI.setVisible(true);


    }
}