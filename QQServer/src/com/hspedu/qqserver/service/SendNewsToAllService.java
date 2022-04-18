package com.hspedu.qqserver.service;

import com.hspedu.qqcommon.Message;
import com.hspedu.qqcommon.MessageType;
import com.hspedu.qqserver.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class SendNewsToAllService implements Runnable{

    @Override
    public void run() {
        while (true){
            System.out.println("Please enter News the server want to send: [enter exit to quit send news service]");
            String news = Utility.readString(100);
            if("exit".equals(news)){
                break;
            }
            //create message, send group message
            Message message = new Message();
            message.setSender("Server");
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            message.setContent(news);
            message.setSendTime(new Date().toString());
            System.out.println("Server send message to everyone: " + news);

            //iterate current online thread, get related socket
            HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String onLineUserId =  iterator.next();
                ServerConnectClientThread serverConnectClientThread = hm.get(onLineUserId);
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    objectOutputStream.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
