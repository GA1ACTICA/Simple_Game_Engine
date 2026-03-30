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

package AdvancedRendering.uiRendering.Button;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;

import GameEngine.EngineModules.*;
import GameEngine.EngineModules.CursorManager.CursorType;
import GameEngine.Interfaces.*;
import GameEngine.Interfaces.Drawables.UIDrawable;
import GameEngine.Interfaces.MenuInterface.*;
import Utils.GraphicsTools;

public class RectButton implements
        UIDrawable, MenuInterface, MenuSetPosition, MenuSetSize, MenuSetHoverVisual,
        MenuSetImage, MenuSetColor, Clickable, Hoverable {

    private int zIndex = 0; // default zIndex

    private int x, y, width, height;
    private double angle = 0;
    private boolean show = false;

    protected RectangularShape baseShape;
    protected Shape rotatedShape;

    private Color color = Color.GREEN;
    private Color hoverColor = Color.ORANGE;
    private Color clickColor = new Color(255, 255, 255, 150);
    private Color disabledColor = Color.LIGHT_GRAY;

    private Image image;
    private Image hoverImage;
    private Image clickImage;
    private Image disabledImage;

    private boolean enabled = true;
    private boolean clicked = false;
    private boolean clickEffect = true;

    private Runnable clickAction;

    // private Runnable hoverAction; // TODO: look into this
    private boolean isHovered = false;

    private Mouse mouse;
    private EngineContext context;

    /**
     * 
     * @param mouse
     * 
     * @param context
     * 
     * @param x
     * 
     * @param y
     * 
     * @param width
     * 
     * @param height
     */
    public RectButton(EngineContext context, EnginePanel panel, Mouse mouse, int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this(context, mouse, panel);

    }

    /**
     * 
     * @param mouse
     * 
     * @param context
     * 
     * @param topLeft
     * 
     * @param bottomRight
     */
    public RectButton(EngineContext context, EnginePanel panel, Mouse mouse, Point topLeft, Point bottomRight) {

        x = (int) topLeft.getX();
        y = (int) topLeft.getY();
        width = (int) bottomRight.getX() - (int) topLeft.getX();
        height = (int) bottomRight.getY() - (int) topLeft.getY();

        this(context, mouse, panel);

    }

    /**
     * 
     * @param mouse
     * 
     * @param context
     * 
     * @param middle
     * 
     * @param width
     * 
     * @param height
     */
    public RectButton(EngineContext context, EnginePanel panel, Mouse mouse, Point middle, int width, int height) {

        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        this.width = width;
        this.height = height;

        this(context, mouse, panel);

    }

    private RectButton(EngineContext context, Mouse mouse, EnginePanel panel) {
        ClassFactory.create(this, context, zIndex);

        this.context = context;
        this.mouse = mouse;

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();

    }

    @Override
    public void setZIndex(int zIndex) {
        ClassFactory.updatePriority(this, context, zIndex);
        this.zIndex = zIndex;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public boolean isVisible() {
        return show;
    }

    @Override
    public void show() {
        show = true;
    }

    @Override
    public void hide() {
        show = false;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    @Override
    public void setPosition(Point position) {
        this.x = position.x;
        this.y = position.y;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    @Override
    public void translate(int x, int y) {
        this.x += x;
        this.y += y;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    public void setClickEffect(boolean clickEffect) {
        this.clickEffect = clickEffect;
    }

    // ————————— Set colors ——————————
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setDisabledColor(Color disabledColor) {
        this.disabledColor = disabledColor;
    }

    public void setClickColor(Color clickColor) {
        this.clickColor = clickColor;
    }

    // —————————— Set images ——————————
    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void setHoverImage(Image hoverImage) {
        this.hoverImage = hoverImage;
    }

    public void setDisabledImage(Image disabledImage) {
        this.disabledImage = disabledImage;
    }

    public void setClickImage(Image clickImage) {
        this.clickImage = clickImage;
    }

    // ————————————————————————————————

    public void setCenter(Point middle) {
        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    public void setRotation(double angle) {
        this.angle = angle;

        updateRotatedShape();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point getCenter() {
        return new Point(x + width / 2, y + height / 2);
    }

    public Color getColor() {
        return color;
    }

    public double getAngle() {
        return angle;
    }

    @Override
    public boolean isHovered() {
        return isHovered;
    }

    /**
     * This lets you run code when the button is pressed with the method:
     * 
     * <p>
     * onClick(() -> {});
     * <p>
     * 
     * @param action
     */
    @Override
    public void onClick(Runnable action) {
        this.clickAction = action;

    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        Graphics2D g2d = (Graphics2D) g;

        // Rotate everything drawn inside
        GraphicsTools.rotateGraphics(g2d, angle, getCenter(), () -> {

            if (!enabled) {
                if (disabledImage == null) {
                    g2d.setColor(disabledColor);
                    g2d.fill(baseShape);
                } else {
                    g2d.drawImage(disabledImage, 0, 0, width, height, null);
                }
                return;
            }

            if (!isHovered) {

                // Draw if the button is not hovered
                if (image == null) {
                    g2d.setColor(color);
                    g2d.fill(baseShape);

                } else {

                    BufferedImage buffer = GraphicsTools.createMask(
                            baseShape,
                            width,
                            height,
                            gMask -> {
                                gMask.drawImage(image, 0, 0, width, height, null);
                            });
                    g2d.drawImage(buffer, x, y, null);
                }

            } else {

                // Draws this if button is hovered
                if (hoverImage == null) {
                    g2d.setColor(hoverColor);
                    g2d.fill(baseShape);

                } else {

                    BufferedImage buffer = GraphicsTools.createMask(
                            baseShape,
                            width,
                            height,
                            gMask -> {

                                gMask.drawImage(hoverImage, 0, 0, width, height, null);

                            });
                    g2d.drawImage(buffer, x, y, null);
                }
            }

            if (clickEffect && clicked) {
                // Draws this if button is clicked
                if (clickImage == null) {
                    g2d.setColor(clickColor);
                    g2d.fill(baseShape);

                } else {

                    BufferedImage buffer = GraphicsTools.createMask(
                            baseShape,
                            width,
                            height,
                            gMask -> {

                                gMask.drawImage(clickImage, 0, 0, width, height, null);

                            });
                    g2d.drawImage(buffer, x, y, null);
                }
            }
        });
    }

    // Call updateRotatedShape every time the position, size or rotation changes
    protected void updateRotatedShape() {

        AffineTransform transform = new AffineTransform();
        Point middle = getCenter();

        transform.rotate(Math.toRadians(angle), middle.x, middle.y);
        rotatedShape = transform.createTransformedShape(baseShape);
    }

    @Override
    public boolean contains(int mouseX, int mouseY) {
        return rotatedShape.contains(mouse.getPoint().x, mouse.getPoint().y);
    }

    @Override
    public void executeOnClick() {
        if (clickAction != null)
            clickAction.run();
    }

    @Override
    public void setHovered(boolean isHovered) {
        this.isHovered = isHovered;

        if (isHovered)
            CursorManager.setCursor(CursorType.POINTER);
        else
            CursorManager.setCursor(CursorType.DEFAULT);

    }

    @Override
    public void pressed() {
        clicked = true;
    }

    @Override
    public void released() {
        clicked = false;
    }

    public boolean isPressed() {
        return clicked;
    }

    public boolean isReleased() {
        return !clicked;
    }
}
