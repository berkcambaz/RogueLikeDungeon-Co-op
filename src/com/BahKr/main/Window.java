package com.BahKr.main;

import com.BahKr.main.Network.Packet.Packet01Disconnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Window extends Canvas implements WindowListener {
    private Game game;

    public Window(int width, int height, String title, Game game) {
        this.game = game;

        JFrame frame = new JFrame(title);

        frame.getContentPane().setPreferredSize(new Dimension(width, height));
        frame.getContentPane().setMinimumSize(new Dimension(width, height));
        frame.getContentPane().setMaximumSize(new Dimension(width, height));
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(game);
        frame.setVisible(true);
        frame.addWindowListener(this);
        game.start();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        Packet01Disconnect packet = new Packet01Disconnect(game.getPlayer().getUsername());
        packet.writeData(game.gameClient);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

