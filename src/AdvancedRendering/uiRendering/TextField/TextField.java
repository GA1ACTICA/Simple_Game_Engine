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

package AdvancedRendering.uiRendering.TextField;

import GameEngine.Interfaces.MenuInterface.*;
import Utils.GraphicsTools;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import GameEngine.EngineModules.ClassFactory;
import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.EnginePanel;
import GameEngine.EngineModules.Keys;
import GameEngine.EngineModules.Mouse;
import GameEngine.Interfaces.*;
import GameEngine.Interfaces.Drawables.UIDrawable;

public class TextField implements UIDrawable, Updatable, MenuInterface, MenuSetSize, MenuSetPosition, MenuSetColor,
        MenuSetImage, MenuSetHoverVisual {

    private boolean show;

    private int x;
    private int y;
    private int width;
    private int height;

    private double angle;

    private Mouse mouse;
    private Keys keys;
    private EnginePanel panel;

    private RectangularShape baseShape;
    private Shape rotatedShape;

    private Color color;
    private Color hoverColor;

    private Image image;
    private Image hoverImage;

    private Font fieldFont = new Font("SansSerif", Font.PLAIN, 25);

    private EngineContext context;

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
    public TextField(EngineContext context, EnginePanel panel, Mouse mouse, Keys keys, int x, int y, int width,
            int height) {
        ClassFactory.create(this, context);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mouse = mouse;
        this.panel = panel;

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
    public TextField(EngineContext context, EnginePanel panel, Mouse mouse, Keys keys, Point topLeft,
            Point bottomRight) {
        ClassFactory.create(this, context);

        x = (int) topLeft.getX();
        y = (int) topLeft.getY();
        width = (int) bottomRight.getX() - (int) topLeft.getX();
        height = (int) bottomRight.getY() - (int) topLeft.getY();
        this.mouse = mouse;
        this.panel = panel;

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();

        // panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
    public TextField(EngineContext context, EnginePanel panel, Mouse mouse, Keys keys, Point middle, int width,
            int height) {
        ClassFactory.create(this, context);

        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        this.width = width;
        this.height = height;
        this.mouse = mouse;
        this.panel = panel;

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();

    }

    @Override
    public void show() {
        show = true;
    }

    @Override
    public void hide() {
        show = false;
    }

    /**
     * @param x
     * @param y
     */
    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    /**
     * @param position
     */
    @Override
    public void setPosition(Point position) {
        this.x = position.x;
        this.y = position.y;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    /**
     * @param dx
     * @param dy
     */
    @Override
    public void translate(int dx, int dy) {
        x += dx;
        y += dy;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    /**
     * @param width
     * @param height
     */
    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    /**
     * @param color
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @param hoverColor
     */
    @Override
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    /**
     * @param image
     */
    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * @param hoverImage
     */
    @Override
    public void setHoverImage(Image hoverImage) {
        this.hoverImage = hoverImage;
    }

    /**
     * @param middle
     */
    public void setMiddle(Point middle) {
        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    /**
     * @param angle
     */
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

            if (image == null) {
                g2d.setColor(color);
                g2d.fillRect(x, y, width, height);
            } else
                g2d.drawImage(image, x, y, width, height, null);

            if (hoverImage == null) {
                g2d.setColor(hoverColor);
                g2d.fillRect(x, y, width, height);
            } else
                g2d.drawImage(hoverImage, x, y, width, height, null);

        });
    }

    @Override
    public void update() {
        if (!show)
            return;

    }

    // Call updateRotatedShape everytime the position, size or rotation changes
    protected void updateRotatedShape() {

        AffineTransform transform = new AffineTransform();
        Point middle = getMiddlePoint();

        transform.rotate(Math.toRadians(angle), middle.x, middle.y);
        rotatedShape = transform.createTransformedShape(baseShape);
    }

    /**
     * @param fieldFont
     */
    private void updateFontMetrics(Font fieldFont) {

    }

    /**
     * @param zIndex
     */
    @Override
    public void setZIndex(int zIndex) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setZIndex'");
    }

    /**
     * @return int
     */
    @Override
    public int getZIndex() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getZIndex'");
    }

}
