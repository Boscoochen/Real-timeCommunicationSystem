package com.hspedu.qqserver.service;

import com.hspedu.qqcommon.Message;
import com.hspedu.qqcommon.MessageType;
import com.hspedu.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * this is server, listening at 9999 port, waiting client connect, keep connecting
 */
public class QQServer {
    private ServerSocket ss = null;
    //create a collections store multiple users, if those users login, consider valid
    //ConcurrentHashMap is
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<String, User>();


    static  { //static block initialize validUsers
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
        validUsers.put("smith", new User("smith", "123456"));
        validUsers.put("scott", new User("scott", "123456"));
        validUsers.put("jack", new User("jack", "123456"));
    }

    //verify user whether valid
    private boolean checkUser(String userId, String passwd) {
        User user = validUsers.get(userId);
        //过关验证
        if(user == null) { //this mean userid is not store in key of validUsers
            return false;
        }

        if(!user.getPasswd().equals(passwd)) { //userid correct, password not
            return false;
        }

        return true;

    }

    public QQServer() {
        //port can be written in the configuration file
        try {
            System.out.println("Server listening at 9999 port...");
            ss = new ServerSocket(9999);
            while(true) { //when connected with one of the client, still keep listening, so use while loop
                Socket socket = ss.accept(); //if there has no client connect, block here
                //get related socket object input stream
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                //get related socket object output stream
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());


                User u = (User) ois.readObject(); //read user object sent from Client
                //Create object of message, prepare to reply to client
                Message message = new Message();
                //verify
                if(checkUser(u.getUserId(), u.getPasswd())) { //legal, verify passed
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(message);
                    //create a thread, keep communicating with client, this thread need to contain socket
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, u.getUserId());
                    //turn on thread
                    serverConnectClientThread.start();
                    //put userid and thread into hashmap to manage
                    ManageClientThreads.addClientThread(u.getUserId(), serverConnectClientThread);
                }else { //login failed
                    System.out.println("User id=" + u.getUserId() + " pwd=" + u.getPasswd() + " verify fail");
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    socket.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //if server quick while, that means server is not listening, so close server socket
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
