package com.hspedu.qqcommon;

import java.io.Serializable;

/**
 * represent message object of client and server
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender; //sender
    private String getter;// receive
    private String content; //message
    private String sendTime; //send message time
    private String mesType; //message type, can define type at interface

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
