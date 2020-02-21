package com.BahKr.main.Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private boolean[] keys;
    public boolean up, down, left, right;

    public KeyInput() {
        keys = new boolean[256];
    }

    public void tick() {
        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
    }

    public void keyPressed(KeyEvent e){
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e){
        keys[e.getKeyCode()] = false;
    }
}
