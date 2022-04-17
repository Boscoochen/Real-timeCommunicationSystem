package com.hspedu.qqserver.service;

import java.util.HashMap;
import java.util.Iterator;

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

    //return online users list
    public static String getOnlineUser() {
        //collection iteration,
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUserList = "";
        while (iterator.hasNext()) {
            onlineUserList += iterator.next().toString() + " ";
        }
        return onlineUserList;
    }

    //remove thread object from concurrentmap
    public static void removeServerConnectClientThread(String userId) {
        hm.remove(userId);
    }

}
