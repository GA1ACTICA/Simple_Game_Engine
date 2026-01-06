package AdvancedRendering.uiRendering.Button;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;

import GameEngine.EngineModules.ClassFactory;
import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.Mouse;
import GameEngine.Interfaces.UIDrawable;
import GameEngine.Interfaces.Updatable;

public class RectButton implements UIDrawable, Updatable {

    private int x, y, width, height;
    private boolean show = false;

    public Shape shape;

    private Color color = Color.GREEN;
    private Color hoverColor = Color.RED;

    private Image image;
    private Image hoverImage;

    private boolean inside;
    private boolean wasPressed;

    private Runnable action;
    private Mouse mouse;

    public RectButton(Mouse mouse, EngineContext context, int x, int y, int width, int height) {
        ClassFactory.create(this, context);

        this.x = x;
        this.y = y;
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

    /**
     * 
     * @param action
     */
    public void onClick(Runnable action) {
        this.action = action;

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
