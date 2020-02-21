package com.BahKr.main.State.States;

import com.BahKr.main.GameObject.Entity.Entities.Player;
import com.BahKr.main.Handler;
import com.BahKr.main.State.State;

import java.awt.*;

public class GameState extends State {

    public GameState(Handler handler) {
        super(handler);

        //handler.getWorld().addObj(new Player(handler, 10, 10, 32, 32));
    }

    @Override
    public void tick() {
        handler.getWorld().tick();
    }

    @Override
    public void render(Graphics g) {
        handler.getWorld().render(g);
    }
}
