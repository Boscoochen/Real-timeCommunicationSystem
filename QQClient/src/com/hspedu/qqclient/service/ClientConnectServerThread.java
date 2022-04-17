package com.hspedu.qqclient.service;

import com.hspedu.qqcommon.Message;
import com.hspedu.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread{
    //this thread need to have Socket
    private Socket socket;

    //constructor accept a Socket object
    public ClientConnectServerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        //because Thread need to communicate with server, so we need while loop to control
        while (true) {

            System.out.println("Client Thread Is Waiting To Read Message From Server...");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject(); //if server not sending message, thread blocks here
                //determine message type to do corresponding processing
                //if the return is the list of online user
                if(message.getMesType().equals(MessageType.MESSAGE_RETURN_ONLINE_FRIEND)) {
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n========Current Online Users list========");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("User: " + onlineUsers[i]);
                    }

                } else {
                    System.out.println("Other types of message, do nothing temporary...");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //somewhere need to get socket
    public Socket getSocket() {
        return socket;
    }
}
