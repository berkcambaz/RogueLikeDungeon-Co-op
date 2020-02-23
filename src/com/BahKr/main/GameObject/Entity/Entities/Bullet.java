package com.BahKr.main.GameObject.Entity.Entities;

import com.BahKr.main.GameObject.Entity.Entity;
import com.BahKr.main.Handler;

import java.awt.*;

public class Bullet extends Entity {
    private float velX, velY;

    public Bullet(Handler handler, int x, int y, int radius, float velX, float velY) {
        super(handler, x, y, radius, radius);
        this.velX = velX;
        this.velY = velY;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.drawOval((int) x, (int) y, width, height);
    }
}
