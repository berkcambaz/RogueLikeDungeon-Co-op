package com.BahKr.main.World;

import com.BahKr.main.GameObject.Entity.Entities.PlayerMP;
import com.BahKr.main.GameObject.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class World {
    private LinkedList<GameObject> obj = new LinkedList<>();
    public LinkedList<GameObject> bullets = new LinkedList<>();

    public World() {

    }

    public void tick() {
        for (int i = 0; i < obj.size(); i++) {
            obj.get(i).tick();
        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < obj.size(); i++) {
            obj.get(i).render(g);
        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(g);
        }
    }

    public synchronized void addObj(GameObject obj) {
        this.obj.add(obj);
    }

    public void removeObj(GameObject obj) {
        this.obj.remove(obj);
    }

    public GameObject getObj(int i) {
        return obj.get(i);
    }

    public synchronized LinkedList<GameObject> getObjList() {
        return obj;
    }

    public synchronized void removePlayerMP(String username) {
        int index = 0;
        for (GameObject tempObj : getObjList()) {
            if (tempObj instanceof PlayerMP && ((PlayerMP) tempObj).getUsername().equals(username)) {
                break;
            }
            index++;
        }
        removeObj(getObj(index));
    }

    private int getPlayerMPIndex(String username) {
        int index = 0;
        for (GameObject tempObj : getObjList()) {
            if (tempObj instanceof PlayerMP && ((PlayerMP) tempObj).getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public synchronized void movePlayer(String username, int x, int y) {
        int index = getPlayerMPIndex(username);
        PlayerMP player = (PlayerMP) getObj(index);
        player.x = x;
        player.y = y;
    }
}
