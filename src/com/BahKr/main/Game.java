package com.BahKr.main;

import com.BahKr.main.GameObject.Entity.Entities.Player;
import com.BahKr.main.GameObject.Entity.Entities.PlayerMP;
import com.BahKr.main.Input.KeyInput;
import com.BahKr.main.Network.GameClient;
import com.BahKr.main.Network.GameServer;
import com.BahKr.main.Network.Packet.Packet00Login;
import com.BahKr.main.Network.Packet.Packet01Disconnect;
import com.BahKr.main.State.State;
import com.BahKr.main.State.States.GameState;
import com.BahKr.main.State.States.MenuState;
import com.BahKr.main.World.World;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    private int width, height;

    private Thread thread;
    private boolean running = false;

    private State gameState;
    private State menuState;
    private KeyInput keyInput;
    private Handler handler;
    private World world;
    private Player player;

    public GameClient gameClient;
    public GameServer gameServer;

    public Game(int width, int height, String title) {
        this.width = width;
        this.height = height;

        this.addKeyListener(keyInput = new KeyInput());

        new Window(width, height, title, this);
        handler = new Handler(this);

        world = new World();

        menuState = new MenuState(handler);
        gameState = new GameState(handler);

        State.setState(gameState);

        if (JOptionPane.showConfirmDialog(this, "Do you want to run the server") == 0) {
            gameServer = new GameServer(handler);
            gameServer.start();
        }

        gameClient = new GameClient(handler, "localhost");
        gameClient.start();

        player = new PlayerMP(handler, 100, 100, JOptionPane.showInputDialog(this, "Please enter a username"), null, -1);
        world.addObj(player);

        Packet00Login loginPacket = new Packet00Login(player.getUsername(), (int) player.x, (int) player.y);
        if (gameServer != null) {
            gameServer.addConnection((PlayerMP) player, loginPacket);
        }
        loginPacket.writeData(gameClient);
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.requestFocus();
        long initialTime = System.nanoTime();
        boolean RENDER_TIME = true;
        float UPS = 60.f;
        float FPS = 75.f;
        final double timeU = 1000000000 / UPS;
        final double timeF = 1000000000 / FPS;
        double deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if (deltaU >= 1) {
                tick();
                ticks++;
                deltaU--;
            }

            if (deltaF >= 1) {
                render();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                if (RENDER_TIME) {
                    //System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
                }
                frames = 0;
                ticks = 0;
                timer += 1000;
            }
        }
        stop();
    }

    public void tick() {
        keyInput.tick();
        if (State.getState() != null) {
            State.getState().tick();
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);

        if (State.getState() != null) {
            State.getState().render(g);
        }

        g.dispose();
        bs.show();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public KeyInput getKeyInput() {
        return keyInput;
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    public static void main(String[] args) {
        new Game(640, 480, "Title!");
    }
}
