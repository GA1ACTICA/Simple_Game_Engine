package GameEngine;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Mouse implements MouseMotionListener, MouseListener, MouseWheelListener {

    public boolean leftDown;
    public boolean rightDown;
    public boolean middleDown;

    public boolean onScreen;

    public int x, y;

    private int lastX, lastY;

    public int deltaX, deltaY;

    public float mouseWheelDelta;

    GameState gs;

    public Mouse(GameState gs) {
        this.gs = gs;
    }

    // MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent arg0) {
        updateMouseMovment(arg0);
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        updateMouseMovment(arg0);
    }

    private void updateMouseMovment(MouseEvent arg0) {
        x = arg0.getX();
        y = arg0.getY();

        deltaX = x - lastX;
        deltaY = y - lastY;

        lastX = x;
        lastY = y;

        if (gs.debugVerbose) {
            System.out.println("MouseMovedToX: " + x);
            System.out.println("MouseMovedToY: " + y + '\n');

            System.out.println("DeltaX: " + deltaX);
            System.out.println("DeltaY: " + deltaY + '\n');
        }
    }

    // MouseListener
    // unused
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        onScreen = true;

    }

    @Override
    public void mouseExited(MouseEvent e) {
        onScreen = false;

    }

    @Override
    public void mousePressed(MouseEvent e) {
        setButton(e.getButton(), true);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setButton(e.getButton(), false);

    }

    private void setButton(int button, boolean down) {
        switch (button) {
            case MouseEvent.BUTTON1 -> leftDown = down;
            case MouseEvent.BUTTON2 -> middleDown = down;
            case MouseEvent.BUTTON3 -> rightDown = down;
        }

        if (gs.debugVerbose) {
            System.out.println("IsLeftPreesed: " + leftDown);
            System.out.println("IsRightPreesed: " + rightDown);
            System.out.println("IsMiddlePreesed: " + middleDown + '\n');
        }
    }

    // MouseWheelListener
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        float delta = (float) e.getPreciseWheelRotation();

        mouseWheelDelta += delta;

    }

    public void deltaReset() {

        if (gs.debugVerbose) {
            System.out.println("MouseWheelDelta: " + mouseWheelDelta + '\n');
        }

        deltaX = 0;
        deltaY = 0;
        mouseWheelDelta = 0;

    }

}
