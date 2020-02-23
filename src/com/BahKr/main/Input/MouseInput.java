package com.BahKr.main.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    public boolean[] buttons;
    public boolean leftBut, rightBut;
    public int mouseX, mouseY;

    public MouseInput() {
        buttons = new boolean[6];
    }

    public void tick() {
        leftBut = buttons[MouseEvent.BUTTON1];
        //rightBut = buttons[MouseEvent.BUTTON2];
    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }
}
