package com.BahKr.main.GameObject.Entity.Entities;

import com.BahKr.main.GameObject.Entity.Entity;
import com.BahKr.main.Handler;
import com.BahKr.main.Network.Packet.Packet02Move;

import java.awt.*;

public class Player extends Entity {
    private String username;

    public Player(Handler handler, float x, float y, int width, int height, String username) {
        super(handler, x, y, width, height);
        this.username = username;
        speed = 3f;
    }

    @Override
    public void tick() {
        velX = 0;
        velY = 0;
        if (handler != null) {
            if (handler.getKeyInput().up)
                velY = -speed;
            if (handler.getKeyInput().down)
                velY = speed;
            if (handler.getKeyInput().left)
                velX = -speed;
            if (handler.getKeyInput().right)
                velX = speed;
        }
        move();

        if (velX != 0 || velY != 0) {
            Packet02Move packet = new Packet02Move(this.getUsername(), (int) this.x, (int) this.y);
            packet.writeData(handler.getGame().gameClient);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect((int) x, (int) y, width, height);
        g.drawString(username, (int) x, (int) y - 12);
    }

    public String getUsername() {
        return username;
    }
}
