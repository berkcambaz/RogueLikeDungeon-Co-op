package com.BahKr.main.Network.Packet;

import com.BahKr.main.Network.GameClient;
import com.BahKr.main.Network.GameServer;

public class Packet03Shoot extends Packet {
    private int x, y, radius;
    private float velX, velY;

    public Packet03Shoot(byte[] data) {
        super(03);
        String[] dataArray = readData(data).split(",");
        this.x = Integer.parseInt(dataArray[0]);
        this.y = Integer.parseInt(dataArray[1]);
        this.radius = Integer.parseInt(dataArray[2]);
        this.velX = Float.parseFloat(dataArray[3]);
        this.velY = Float.parseFloat(dataArray[4]);
    }

    public Packet03Shoot(int x, int y, int radius, float velX, float velY) {
        super(03);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.velX = velX;
        this.velY = velY;
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("03" + this.x + "," + this.y + "," + this.radius+ "," + this.velX + "," + this.velY).getBytes();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getRadius() {
        return radius;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }
}
