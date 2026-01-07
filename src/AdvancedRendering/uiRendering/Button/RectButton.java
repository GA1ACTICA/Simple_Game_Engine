package AdvancedRendering.uiRendering.Button;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import GameEngine.EngineModules.ClassFactory;
import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.Mouse;
import GameEngine.Interfaces.UIDrawable;
import GameEngine.Interfaces.Updatable;

public class RectButton implements UIDrawable, Updatable {

    private int x, y, width, height;
    private boolean show = false;

    public RectangularShape shape;

    private Color color = Color.GREEN;
    private Color hoverColor = Color.RED;

    private Image image;
    private Image hoverImage;

    public boolean inside;
    private boolean wasPressed;

    private Runnable action;
    private Mouse mouse;

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
    public RectButton(Mouse mouse, EngineContext context, int x, int y, int width, int height) {
        ClassFactory.create(this, context);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mouse = mouse;
        this.shape = new Rectangle2D.Float(x, y, width, height);
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
    public RectButton(Mouse mouse, EngineContext context, Point topLeft, Point bottomRight) {
        ClassFactory.create(this, context);

        x = (int) topLeft.getX();
        y = (int) topLeft.getY();
        width = (int) bottomRight.getX();
        height = (int) bottomRight.getY();
        this.mouse = mouse;
        this.shape = new Rectangle2D.Float(x, y, width, height);
    }

    public RectButton(Mouse mouse, EngineContext context, Point middle, int width, int height) {
        ClassFactory.create(this, context);

        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        this.width = width;
        this.height = height;
        this.mouse = mouse;
        this.shape = new Rectangle2D.Float(x, y, width, height);
    }

    public void show() {
        show = true;
    }

    public void hide() {
        show = false;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        shape.setFrame(x, y, width, height);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        shape.setFrame(x, y, width, height);
    }

    /**
     * Gets the x and y cordinates from a middle Point
     * 
     * @param middle
     */
    public void setMiddle(Point middle) {
        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        shape.setFrame(x, y, width, height);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setHoverColor(Color hoveColor) {
        this.hoverColor = hoveColor;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setHoverImage(Image hoverImage) {
        this.hoverImage = hoverImage;
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

    public Point getMiddlePoint() {
        return new Point(x + width / 2, y + height / 2);
    }

    /**
     * 
     * @param action
     */
    public void onClick(Runnable action) {
        this.action = action;

    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        Graphics2D g2d = (Graphics2D) g;

        // Draw color background
        if (inside && hoverImage == null) {
            g2d.setColor(hoverColor);
            g2d.fill(shape);

        } else if (image == null) {
            g2d.setColor(color);
            g2d.fill(shape);
        }

        // Draw image background
        if (image != null) {
            g2d.setClip(shape); // Clip the image to the shape of the button

            // Draw the image scaled to fit the button's bounds
            if (inside && hoverImage != null)
                g2d.drawImage(hoverImage, x, y, width, height, null);
            else
                g2d.drawImage(image, x, y, width, height, null);
        }
    }

    @Override
    public void update() {
        if (!show)
            return;

        inside = shape.contains(mouse.getX(), mouse.getY());

        pressed();
    }

    private void pressed() {
        if (inside && mouse.getLeftDown() && !wasPressed) {
            wasPressed = true;
        }

        if (wasPressed && !mouse.getLeftDown()) {
            wasPressed = false;
            if (inside && action != null) {
                action.run();
            }
        }
    }
}
