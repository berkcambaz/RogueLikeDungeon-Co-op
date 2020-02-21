package com.BahKr.main.GameObject.Entity.Entities;

import com.BahKr.main.Handler;

import java.net.InetAddress;

public class PlayerMP extends Player {
    public InetAddress ipAddress;
    public int port;

    public PlayerMP(Handler handler, float x, float y, String username, InetAddress ipAddress, int port) {
        super(handler, x, y, 32, 32, username);

        this.ipAddress = ipAddress;
        this.port = port;
    }

    public PlayerMP(float x, float y, String username, InetAddress ipAddress, int port) {
        super(null, x, y, 32, 32, username);

        this.ipAddress = ipAddress;
        this.port = port;
    }
}
