package com.BahKr.main.State;

import com.BahKr.main.Handler;

import java.awt.*;

public abstract class State {
    private static State currentState = null;
    protected Handler handler;

    public State(Handler handler) {
        this.handler = handler;
    }

    public static State getState() {
        return currentState;
    }

    public static void setState(State state) {
        currentState = state;
    }

    public abstract void tick();

    public abstract void render(Graphics g);
}
