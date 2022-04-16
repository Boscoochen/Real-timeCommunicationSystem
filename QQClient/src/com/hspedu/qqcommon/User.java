package com.hspedu.qqcommon;

import java.io.Serializable;

/**
 * Represent one client message
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId; //user name or id
    private String passwd; //user password

    public User(String userId, String passwd) {
        this.userId = userId;
        this.passwd = passwd;
    }

    public User() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
