/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 *
 * Copyright © 2026 Galactica
 */

package advancedRendering.uiRendering.textField;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import gameEngine.engineModules.ClassFactory;
import gameEngine.engineModules.EngineContext;
import gameEngine.engineModules.EnginePanel;
import gameEngine.engineModules.Keys;
import gameEngine.engineModules.Mouse;
import gameEngine.engineModules.cursor.CursorManager;
import gameEngine.engineModules.cursor.CursorType;
import gameEngine.interfaces.Clickable;
import gameEngine.interfaces.Hoverable;
import gameEngine.interfaces.MenuInterface;
import gameEngine.interfaces.MenuInterface.*;
import gameEngine.interfaces.Updatable;
import gameEngine.interfaces.drawables.UIDrawable;
import utils.GraphicsTools;

public class TextField
        implements Clickable, Hoverable, UIDrawable, Updatable, MenuInterface, MenuSetSize, MenuSetPosition,
        MenuSetColor,
        MenuSetImage, MenuSetHoverVisual {

    private int zIndex = 0;

    private boolean show;

    private int x;
    private int y;
    private int width;
    private int height;

    private double angle;

    private RectangularShape baseShape;
    private Shape rotatedShape;

    // Colors
    private Color color = new Color(173, 169, 169);
    private Color hoverColor = Color.LIGHT_GRAY;

    // Images
    private Image image;
    private Image hoverImage;

    private Font fieldFont = new Font("SansSerif", Font.PLAIN, 25);

    private Runnable clickAction;

    private EngineContext context;
    private Keys keys;
    private Mouse mouse;

    // Behavioral variables
    private boolean isHovered;
    private boolean focused;
    private boolean enabled;
    private boolean pressed;

    /**
     * 
     * Important: TextField class is currently under development and is not to be
     * used!
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
    public TextField(EngineContext context, Mouse mouse, Keys keys, int x, int y, int width,
            int height) {
        ClassFactory.create(this, context);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mouse = mouse;
        this.keys = keys;

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();

    }

    /**
     * 
     * Important: TextField class is currently under development and is not to be
     * used!
     * 
     * @param mouse
     * 
     * @param context
     * 
     * @param topLeft
     * 
     * @param bottomRight
     */
    public TextField(EngineContext context, Mouse mouse, Keys keys, Point topLeft,
            Point bottomRight) {
        ClassFactory.create(this, context);

        x = (int) topLeft.getX();
        y = (int) topLeft.getY();
        width = (int) bottomRight.getX() - (int) topLeft.getX();
        height = (int) bottomRight.getY() - (int) topLeft.getY();
        this.mouse = mouse;
        this.keys = keys;

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();
    }

    /**
     * 
     * Important: TextField class is currently under development and is not to be
     * used!
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
    public TextField(EngineContext context, Mouse mouse, Keys keys, Point middle, int width,
            int height) {
        ClassFactory.create(this, context);

        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        this.width = width;
        this.height = height;
        this.mouse = mouse;
        this.keys = keys;

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();

    }

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
    public void setZIndex(int zIndex) {
        ClassFactory.updatePriority(this, context, zIndex);
        this.zIndex = zIndex;
    }

    @Override
    public int getZIndex() {
        return zIndex;
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
    public void translatePosition(int dx, int dy) {
        x += dx;
        y += dy;
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

    @Override
    public void translateSize(int dWidth, int dHeight) {
        width += dWidth;
        height += dHeight;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void setHoverImage(Image hoverImage) {
        this.hoverImage = hoverImage;
    }

    public void setMiddle(Point middle) {
        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    public void setRotation(double angle) {
        this.angle = angle;

        updateRotatedShape();
    }

    /**
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * @return int
     */
    public int getY() {
        return y;
    }

    /**
     * @return int
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return int
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return Point
     */
    public Point getMiddlePoint() {
        return new Point(x + width / 2, y + height / 2);
    }

    /**
     * @return double
     */
    public double getAngle() {
        return angle;
    }

    /**
     * @return Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        Graphics2D g2d = (Graphics2D) g;

        GraphicsTools.rotateGraphics(g2d, angle, getMiddlePoint(), () -> {

            if (!isHovered) {
                // Background
                if (image == null) {
                    g2d.setColor(color);
                    g2d.fillRect(x, y, width, height);
                } else
                    g2d.drawImage(image, x, y, width, height, null);
            } else {
                // Background when hovered
                if (hoverImage == null) {
                    g2d.setColor(hoverColor);
                    g2d.fillRect(x, y, width, height);
                } else
                    g2d.drawImage(hoverImage, x, y, width, height, null);
            }

            if (cursorVisible && focused) {
                g2d.setColor(Color.BLACK);
                g2d.fillRect(x + 2, y + 2, 2, height - 4);
            }

        });
    }

    private float cursorTimer = 0f;
    private boolean cursorVisible = true;

    @Override
    public void update(float deltaTime) {
        if (!show)
            return;

        cursorTimer += deltaTime;

        if (cursorTimer >= 0.5f) {
            cursorTimer -= 0.5f;
            cursorVisible = !cursorVisible;
        }

    }

    // Call updateRotatedShape every time the position, size or rotation changes
    protected void updateRotatedShape() {

        AffineTransform transform = new AffineTransform();
        Point middle = getMiddlePoint();

        transform.rotate(Math.toRadians(angle), middle.x, middle.y);
        rotatedShape = transform.createTransformedShape(baseShape);
    }

    /**
     * @param fieldFont
     */
    private void updateFont(Font font) {

    }

    @Override
    public boolean isHovered() {
        return isHovered;
    }

    @Override
    public void setHovered(boolean isHovered) {
        this.isHovered = isHovered;

        if (isHovered)
            CursorManager.setCursor(CursorType.TEXT);
        else
            CursorManager.setCursor(CursorType.DEFAULT);

    }

    @Override
    public boolean contains(int mouseX, int mouseY) {
        return rotatedShape.contains(new Point(mouseX, mouseY));
    }

    @Override
    public void onClick(Runnable action) {
        clickAction = action;
    }

    @Override
    public void executeOnClick() {
        focused = true;

        if (clickAction != null)
            clickAction.run();
    }

    @Override
    public void onPressed() {
        pressed = true;
    }

    @Override
    public void onReleased() {
        pressed = false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void notifyClick(Hoverable click) {
        if (click != this)
            focused = false;
    }
}
