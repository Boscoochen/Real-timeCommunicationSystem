package com.hspedu.qqcommon;

public interface MessageType {
    //different constants represent different message types
    String MESSAGE_LOGIN_SUCCEED = "1"; //login success
    String MESSAGE_LOGIN_FAIL = "2";  //login failed
    String MESSAGE_COMM_MES = "3"; //common message
    String MESSAGE_GET_ONLINE_FRIEND = "4"; //request return online user list
    String MESSAGE_RETURN_ONLINE_FRIEND = "5"; //return online user list
    String MESSAGE_CLIENT_EXIT = "6"; // user request exit
    String MESSAGE_TO_ALL_MES = "7"; //group message
    String MESSAGE_FILE_MES = "8"; //send file
}
