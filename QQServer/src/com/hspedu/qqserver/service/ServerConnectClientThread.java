package com.hspedu.qqserver.service;

import com.hspedu.qqcommon.Message;
import com.hspedu.qqcommon.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
                Message message = (Message) ois.readObject();
                //determine message type to do corresponding processing
                if(message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    //client request online users list
                    System.out.println(message.getSender() + " request online users list");
                    String onlineUser = ManageClientThreads.getOnlineUser();
                    //return
                    //Create message object, send to client
                    Message message1 = new Message();
                    message1.setMesType(MessageType.MESSAGE_RETURN_ONLINE_FRIEND);
                    message1.setContent(onlineUser);
                    message1.setGetter(message.getSender());
                    //return to client
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message1);
                }else if(message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)){ //if client send exit message
                    System.out.println(message.getSender() + " exit");
                    Thread.sleep(1);
                    ManageClientThreads.removeServerConnectClientThread(message.getSender());
                    socket.close(); //close connect
                    break; //break thread loop
                }
                else {
                    System.out.println("Other types of message, not process temporary");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
