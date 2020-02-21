package com.BahKr.main;

import com.BahKr.main.Input.KeyInput;
import com.BahKr.main.World.World;

public class Handler {
    private Game game;

    public Handler(Game game) {
        this.game = game;
    }

    public Game getGame(){
        return game;
    }

    public int getWidth() {
        return game.getWidth();
    }

    public int getHeight() {
        return game.getHeight();
    }

    public KeyInput getKeyInput(){
        return game.getKeyInput();
    }

    public World getWorld(){
        return game.getWorld();
    }
}
