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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import gameEngine.interfaces.*;
import gameEngine.engineModules.*;
import gameEngine.engineModules.cursor.*;
import gameEngine.interfaces.MenuInterface.*;
import gameEngine.interfaces.drawables.UIDrawable;
import utils.GraphicsTools;

public class TextField
        implements KeyNotifier, Clickable, Hoverable, UIDrawable, Updatable, MenuInterface, MenuSetSize,
        MenuSetPosition, MenuSetColor, MenuSetImage, MenuSetHoverVisual {

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
    private Color color = GraphicsTools.rgb(155, 155, 155);
    private Color hoverColor = GraphicsTools.rgb(180, 180, 180);
    private Color pressedColor = GraphicsTools.rgb(196, 196, 196);

    // Images
    private Image image;
    private Image hoverImage;
    private Image pressedImage;

    private Font fieldFont;
    private StringBuffer text = new StringBuffer("");

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

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mouse = mouse;
        this.keys = keys;

        this(context);
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

        x = (int) topLeft.getX();
        y = (int) topLeft.getY();
        width = (int) bottomRight.getX() - (int) topLeft.getX();
        height = (int) bottomRight.getY() - (int) topLeft.getY();
        this.mouse = mouse;
        this.keys = keys;

        this(context);
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

        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        this.width = width;
        this.height = height;
        this.mouse = mouse;
        this.keys = keys;

        this(context);

    }

    private TextField(EngineContext context) {
        ClassFactory.create(this, context);

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();

        fieldFont = GraphicsTools.createFontWithPixelHeight(Font.SANS_SERIF, Font.PLAIN, height - 2);
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

    public void setFont(Font font) {
        fieldFont = font;
        updateFont();
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

    public double getAngle() {
        return angle;
    }

    public Color getColor() {
        return color;
    }

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
            } else if (!pressed) {
                // Background when hovered
                if (hoverImage == null) {
                    g2d.setColor(hoverColor);
                    g2d.fillRect(x, y, width, height);
                } else
                    g2d.drawImage(hoverImage, x, y, width, height, null);
            } else {
                // Background when hovered and pressed
                if (pressedImage == null) {
                    g2d.setColor(pressedColor);
                    g2d.fillRect(x, y, width, height);
                } else
                    g2d.drawImage(pressedImage, x, y, width, height, null);
            }

            if (cursorVisible && focused) {
                g2d.setColor(Color.BLACK);
                g2d.fillRect(x + 10 + g2d.getFontMetrics(fieldFont).stringWidth(text.toString()), y + 2, 2, height - 4);
            }

            g2d.setFont(fieldFont);
            g2d.setColor(Color.BLACK);
            g2d.drawString(text.toString(), x + 10,
                    y + g2d.getFontMetrics(fieldFont).getHeight() - g2d.getFontMetrics(fieldFont).getDescent());
            GraphicsTools.debugShape(g2d, x,
                    y + g2d.getFontMetrics(fieldFont).getHeight() - g2d.getFontMetrics(fieldFont).getDescent());

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
    private void updateRotatedShape() {

        AffineTransform transform = new AffineTransform();
        Point middle = getMiddlePoint();

        transform.rotate(Math.toRadians(angle), middle.x, middle.y);
        rotatedShape = transform.createTransformedShape(baseShape);
    }

    private void updateFont() {

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

    @Override
    public void keyNotification() {
        if (!focused)
            return;

        Character input = keys.pollTypedCharacter();

        if (input != null) {
            text.append(input);
        }

        if (keys.getKeysPressed().contains(KeyEvent.VK_BACK_SPACE)) {
            if (text.length() == 0)
                return;

            text.deleteCharAt(text.length() - 1);
            return;
        }
    }
}
