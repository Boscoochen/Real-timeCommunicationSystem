package com.hspedu.qqserver.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.hspedu.qqcommon.*;

/**
 * one of class object communicate with client
 */
public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String userId; //connected userid with Server

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() { //thread at run state, can send/receive message
        while (true) {
            try {
                System.out.println("Server and Client( " + userId + " )connected, reading message...");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message o = (Message) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
