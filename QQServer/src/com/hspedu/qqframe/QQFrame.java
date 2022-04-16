package com.hspedu.qqframe;

import com.hspedu.qqserver.service.QQServer;

/**
 * this class create QQserver, start background service
 */
public class QQFrame {
    public static void main(String[] args) {
        new QQServer();
    }
}
