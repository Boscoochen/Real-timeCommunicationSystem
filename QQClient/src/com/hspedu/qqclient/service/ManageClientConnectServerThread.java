package com.hspedu.qqclient.service;

import java.util.HashMap;

public class ManageClientConnectServerThread {
    //we put mutiple threads into a hashmap, key is the userid, value is a thread
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    //put one of the thread into hashmap
    public static void addClientConnectServerThread(String usrId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(usrId, clientConnectServerThread);
    }

    //according a userid, we can get related thread
    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }
}
