package com.BahKr.main.GameObject.Entity.Entities;

import com.BahKr.main.GameObject.Entity.Entity;
import com.BahKr.main.Handler;
import com.BahKr.main.Network.Packet.Packet02Move;
import com.BahKr.main.Network.Packet.Packet03Shoot;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Player extends Entity {
    private String username;
    private boolean bulletShot;

    public Player(Handler handler, float x, float y, int width, int height, String username) {
        super(handler, x, y, width, height);
        this.username = username;
        speed = 3f;
    }

    @Override
    public void tick() {
        velX = 0;
        velY = 0;
        bulletShot = false;
        if (handler != null) {
            if (handler.getKeyInput().up)
                velY = -speed;
            if (handler.getKeyInput().down)
                velY = speed;
            if (handler.getKeyInput().left)
                velX = -speed;
            if (handler.getKeyInput().right)
                velX = speed;

            if (handler.getMouseInput().leftBut)
                bulletShot = true;
        }
        move();
        if (velX != 0 || velY != 0) {
            Packet02Move packet = new Packet02Move(this.getUsername(), (int) this.x, (int) this.y);
            packet.writeData(handler.getGame().gameClient);
        }

        if (bulletShot) {
            int playerCenterX = (int) x + 16;
            int playerCenterY = (int) y + 16;

            float angle = (float) Math.atan2(handler.getMouseInput().mouseX - playerCenterX, handler.getMouseInput().mouseY - playerCenterY);
            handler.getMouseInput().buttons[MouseEvent.BUTTON1] = false;

            Packet03Shoot packet = new Packet03Shoot(playerCenterX - 8, playerCenterY - 8, 16, (float) Math.sin(angle) * 5f, (float) Math.cos(angle) * 5f);
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
