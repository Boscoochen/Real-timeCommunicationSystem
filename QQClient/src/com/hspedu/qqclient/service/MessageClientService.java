package com.hspedu.qqclient.service;

import com.hspedu.qqcommon.Message;
import com.hspedu.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * this class/object, provide method of message service
 */
public class MessageClientService {
    /**
     *
     * @param content content
     * @param senderId sender id
     * @param getterId receiver id
     */
    public void sendMessageToOne(String content, String senderId, String getterId) {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMM_MES); //normal message
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString()); //set current time to message object
        System.out.println(senderId + " to " + getterId + " message: " + content);
        //send to server
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}