package com.BahKr.main.GameObject.Entity;

import com.BahKr.main.GameObject.GameObject;
import com.BahKr.main.Handler;

public abstract class Entity extends GameObject {
    protected float speed, velX, velY;

    public Entity(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
    }

    public void move(){
        x += velX;
        y += velY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }
}
