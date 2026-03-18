/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the GPL 3.0 License.
 * See LICENSE file in the project root for full license information.
 *
 * Copyright © 2026 Galactica
 */

package GameEngine.EngineModules;

import java.awt.Point;
import java.awt.event.*;

import Game.Configs.GameState.GameState;
import GameEngine.Interfaces.Updatable;

public class Mouse implements MouseMotionListener, MouseListener, MouseWheelListener, Updatable {

    private boolean leftDown;
    private boolean rightDown;
    private boolean middleDown;

    private boolean onScreen;

    private int x, y;

    private int lastX, lastY;

    private int deltaX, deltaY;

    private float mouseWheelDelta;

    private GameState state;
    private EngineContext context;

    public boolean moved;

    public Mouse(GameState state, EngineContext context, EnginePanel panel) {
        ClassFactory.create(this, context);
        this.context = context;
        this.state = state;
    }

    // MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent arg0) {
        updateMouseMovement(arg0);
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        updateMouseMovement(arg0);
    }

    private void updateMouseMovement(MouseEvent arg0) {
        x = arg0.getX();
        y = arg0.getY();

        deltaX = x - lastX;
        deltaY = y - lastY;

        moved = true;

        if (lastX == x)
            moved = false;

        lastX = x;
        lastY = y;
    }

    // MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {
        // unused
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
            case MouseEvent.BUTTON1:
                leftDown = down;
                if (down)
                    MouseManager.handleClick(context, getPoint());

                buttonPrintout(button, down);
                break;

            case MouseEvent.BUTTON2:
                middleDown = down;
                buttonPrintout(button, down);
                break;

            case MouseEvent.BUTTON3:
                rightDown = down;
                buttonPrintout(button, down);
                break;
        }
    }

    private void buttonPrintout(int button, boolean down) {
        if (!state.data().debugVerbose)
            return;

        String buttonName = "Unknown"; // In case an unknown integer is passed
        String buttonState;

        switch (button) {
            case MouseEvent.BUTTON1:
                buttonName = "Left mouse button";
                break;

            case MouseEvent.BUTTON2:
                buttonName = "Middle mouse button";

                break;

            case MouseEvent.BUTTON3:
                buttonName = "Right mouse button";

                break;
        }

        if (down)
            buttonState = "pressed";
        else
            buttonState = "released";

        System.out.println("%s %s".formatted(buttonName, buttonState));

        System.out.println("Mouse moved to X: " + x);
        System.out.println("Mouse moved to Y: " + y + '\n');

    }

    // MouseWheelListener
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        float delta = (float) e.getPreciseWheelRotation();

        mouseWheelDelta += delta;

    }

    @Override
    public void update() {

        deltaX = 0;
        deltaY = 0;
        mouseWheelDelta = 0;
    }

    public Point getPoint() {
        return new Point(x, y);
    }

    public boolean getLeftDown() {
        return leftDown;
    }

    public boolean getMiddleDown() {
        return middleDown;
    }

    public boolean getRightDown() {
        return rightDown;
    }

    public boolean getOnScreen() {
        return onScreen;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public float getMouseWheelDelta() {
        return mouseWheelDelta;
    }
}
