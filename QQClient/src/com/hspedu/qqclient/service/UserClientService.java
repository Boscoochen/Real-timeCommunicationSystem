package com.hspedu.qqclient.service;

import com.hspedu.qqcommon.Message;
import com.hspedu.qqcommon.MessageType;
import com.hspedu.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * This class complete user login verification and registration function
 */
public class UserClientService {
    //we might need this user info somewhere later, so make it to member property
    private User u = new User();
    //we might need this socket somewhere later, so make it to member property
    private Socket socket;

    //According to userId and pwd, send to Server to verify user
    public boolean checkUser(String userId, String pwd) {
        boolean b = false;
        //register a user
        u.setUserId(userId);
        u.setPasswd(pwd);

        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            //create a object of ObjectOutputStream
            ObjectOutputStream OOS = new ObjectOutputStream(socket.getOutputStream());
            OOS.writeObject(u);

            //Read message from server side
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if (ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) { //login ok
                //create a thread to maintain communication with server -> Create class ClientConnectServerThread
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                clientConnectServerThread.start();
                //for client to expend the thread, put thread to collection for managing
                ManageClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);
                b = true;

            } else {
                //if login fail, we can't turn on thread to communicate with server, close socket
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return b;
    }
}
