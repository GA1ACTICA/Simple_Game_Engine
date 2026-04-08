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
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import Game.Configs.GameState.GameState;
import GameEngine.Interfaces.Updatable;
import Utils.ErrorManagement;

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

    /**
     * @param arg0
     */
    // MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent arg0) {
        updateMouseMovement(arg0);
    }

    /**
     * @param arg0
     */
    @Override
    public void mouseMoved(MouseEvent arg0) {
        updateMouseMovement(arg0);
    }

    private void updateMouseMovement(MouseEvent arg0) {
        x = arg0.getX();
        y = arg0.getY();

        deltaX = x - lastX;
        deltaY = y - lastY;

        lastX = x;
        lastY = y;

        MouseManager.handlePriority(context, getPoint());
        MouseManager.handleHover(context, getPoint());
    }
    // MouseListener

    /**
     * 
     * This method is strictly intended for use by the game engine.
     * It is public only because it is defined in an interface, and external
     * callers should avoid invoking it directly.
     * 
     * @param e the {@link java.awt.event.MouseEvent} received from AWT
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // unused
    }

    /**
     * 
     * This method is strictly intended for use by the game engine.
     * It is public only because it is defined in an interface, and external
     * callers should avoid invoking it directly.
     * 
     * @param e the {@link java.awt.event.MouseEvent} received from AWT
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        onScreen = true;
    }

    /**
     * 
     * This method is strictly intended for use by the game engine.
     * It is public only because it is defined in an interface, and external
     * callers should avoid invoking it directly.
     * 
     * @param e the {@link java.awt.event.MouseEvent} received from AWT
     */
    @Override
    public void mouseExited(MouseEvent e) {
        onScreen = false;
    }

    /**
     * 
     * This method is strictly intended for use by the game engine.
     * It is public only because it is defined in an interface, and external
     * callers should avoid invoking it directly.
     * 
     * @param e the {@link java.awt.event.MouseEvent} received from AWT
     */
    @Override
    public void mousePressed(MouseEvent e) {
        setButton(e.getButton(), true);
    }

    /**
     * 
     * This method is strictly intended for use by the game engine.
     * It is public only because it is defined in an interface, and external
     * callers should avoid invoking it directly.
     * 
     * @param e the {@link java.awt.event.MouseEvent} received from AWT
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        setButton(e.getButton(), false);
    }

    private void setButton(int button, boolean down) {
        switch (button) {
            case MouseEvent.BUTTON1:
                leftDown = down;
                MouseManager.handleClick(context, getPoint(), down);
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

    /**
     * 
     * This method is strictly intended for use by the game engine.
     * It is public only because it is defined in an interface, and external
     * callers should avoid invoking it directly.
     * 
     * @param e the {@link java.awt.event.MouseWheelEvent} received from AWT
     */
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

    /**
     * Returns the mouse position in screen (untransformed) coordinates.
     * <p>
     * If a transformation has been applied to the graphics context, these
     * coordinates will not align with that transformed space.
     * <p>
     * To obtain the mouse position relative to a specific
     * {@link java.awt.geom.AffineTransform}, use
     * {@link #getTransformedPoint(AffineTransform)}.
     *
     * @return the mouse position in screen coordinates
     */
    public Point getPoint() {
        return new Point(x, y);
    }

    /**
     * Returns the mouse position transformed into the coordinate space defined by
     * the given {@link AffineTransform}.
     * <p>
     * This method applies the inverse of the provided transform to the current
     * mouse position.
     * <p>
     * If the transform is not invertible, this method returns {@code null}.
     *
     * @param transform the transform defining the target coordinate space
     * 
     * @return the transformed mouse position, or {@code null} if the transform
     *         cannot be inverted
     */
    public Point getTransformedPoint(AffineTransform transform) {

        try {
            AffineTransform inverse = transform.createInverse();

            Point2D transformed = inverse.transform(
                    new Point2D.Double(x, y), null);

            return new Point(
                    (int) transformed.getX(),
                    (int) transformed.getY());

        } catch (NoninvertibleTransformException e) {

            ErrorManagement.reportError(e, "The provided transform is not invertible");
            return null;
        }
    }

    public boolean leftDown() {
        return leftDown;
    }

    public boolean middleDown() {
        return middleDown;
    }

    public boolean rightDown() {
        return rightDown;
    }

    public boolean onScreen() {
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
