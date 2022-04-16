package com.hspedu.qqserver.service;

import java.util.HashMap;

/**
 * this class manage connecting with client threads
 */
public class ManageClientThreads {
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    //put thread object into hashmap
    public static void addClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {
        hm.put(userId, serverConnectClientThread);
    }

    //according userid, return ServerConnectClientThread thread
    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return hm.get(userId);
    }
}
