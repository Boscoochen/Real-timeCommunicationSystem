package com.hspedu.qqserver.service;

import com.hspedu.qqcommon.Message;
import com.hspedu.qqcommon.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

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

    //get current objcet socket
    public Socket getSocket() {
        return socket;
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
                }else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    //according to message, request getter id, then get its related thread
                    ServerConnectClientThread serverConnectClientThread = ManageClientThreads.getServerConnectClientThread(message.getGetter());
                    //create objectoutputstream related to socket object, send message to infer client
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    oos.writeObject(message); //forward message, if client offline, store message to database, implement offline function
                }else if(message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {

                    //need to iterate concurrenthashmap, get all socket from threads, then forward message to all users
                    HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();

                    while (iterator.hasNext()) {
                        //get online users
                        String onLineUserId = iterator.next();
                        if(!onLineUserId.equals(message.getSender())) { //forward message except sender himself
                            //forward messages to online users
                            ObjectOutputStream oos = new ObjectOutputStream(hm.get(onLineUserId).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }
                } else if(message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {
                    //according to getterid, request related thread, forward message to users
                    ServerConnectClientThread serverConnectClientThread = ManageClientThreads.getServerConnectClientThread(message.getGetter());
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    //forward message
                    oos.writeObject(message);
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
