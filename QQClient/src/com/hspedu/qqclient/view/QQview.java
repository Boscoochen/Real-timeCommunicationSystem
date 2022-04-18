package com.hspedu.qqclient.view;


import com.hspedu.qqclient.service.FileClientService;
import com.hspedu.qqclient.service.MessageClientService;
import com.hspedu.qqclient.service.UserClientService;
import com.hspedu.qqclient.utils.Utility;
import com.hspedu.qqcommon.User;

/**
 * Client menu
 */
public class QQview {
    private boolean loop = true; // control display menu
    private String key = "";
    private UserClientService userClientService = new UserClientService(); //object is used to login/register user
    private MessageClientService messageClientService = new MessageClientService(); //for private/group chatting
    private FileClientService fileClientService = new FileClientService(); // for sending file

    public static void main(String[] args) {
        new QQview().mainMenu();
    }

    //Display menu
    private void mainMenu() {
        while (loop) {
            System.out.println("============Welcome To Login NetWork Communication System========");
            System.out.println("\t\t\t\t\t\t\t 1 Login System");
            System.out.println("\t\t\t\t\t\t\t 9 Quit System");
            System.out.println("Please Enter Your Selection:");
            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.print("Please Enter UserName:");
                    String userId = Utility.readString(50);
                    System.out.print("Please Enter Password:");
                    String pwd = Utility.readString(50);
                    // ToDo: Need to send to Server to verify
                    if (userClientService.checkUser(userId, pwd)) {
                        System.out.println("============Welcome (User " + userId + " login succeed)=============");
                        //Enter into second menu
                        while (loop) {
                            System.out.println("\n============Secondary menu of network communication system(User " + userId + " )===========");
                            System.out.println("\t\t\t\t\t\t\t 1 Display A List Of Online Users");
                            System.out.println("\t\t\t\t\t\t\t 2 Group Sending Message");
                            System.out.println("\t\t\t\t\t\t\t 3 Send A Private Chat Message");
                            System.out.println("\t\t\t\t\t\t\t 4 Send file");
                            System.out.println("\t\t\t\t\t\t\t 9 Quit System");
                            System.out.println("Please Enter Your Selection: ");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
//                                    System.out.println("Display A List Of Online Users");
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("Please enter message for group chatting: ");
                                    String content = Utility.readString(100);
                                    //call a method, encapsulate message object, send it to server
                                    messageClientService.sendMessageToAll(content, userId);
                                    break;
                                case "3":
                                    System.out.println("Please enter userId you want to chat with(online): ");
                                    String getterId = Utility.readString(50);
                                    System.out.println("Please enter message: ");
                                    String content_to_all = Utility.readString(100);
                                    //code a method, send this object message to server
                                    messageClientService.sendMessageToOne(content_to_all, userId, getterId);
                                    break;
                                case "4":
                                    System.out.print("Please enter userId you want send file to(online): ");
                                    getterId = Utility.readString(50);
                                    System.out.print("Please enter file path(format /Applications/user1/xx.txt): ");
                                    String src = Utility.readString(100);
                                    System.out.print("Please enter destination file path(format /Applications/user2/xx.txt):  ");
                                    String dest = Utility.readString(100);
                                    fileClientService.sendFileToOne(src, dest, userId, getterId);
                                    break;
                                case "9":
                                    //call method, tell server to exit system
                                    userClientService.logOut();
                                    loop = false;
                                    break;
                            }
                        }
                    } else { //Login failed
                        System.out.println("=========LoginFail===========");
                    }
                    break;
                case "9":
                    System.out.println("Quit System");
                    loop = false;
                    break;
            }
        }
    }
}
