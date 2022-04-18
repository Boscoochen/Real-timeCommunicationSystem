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

    //expend file member
    private byte[] fileBytes;
    private int fileLen = 0;
    private String dest; //file destination
    private String src; //origin file path

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public byte[] getFileBytes() {

        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

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
