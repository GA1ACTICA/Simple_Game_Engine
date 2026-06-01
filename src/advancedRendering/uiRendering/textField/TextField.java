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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
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
import java.awt.image.BufferedImage;

import gameEngine.interfaces.*;
import gameEngine.engineModules.*;
import gameEngine.engineModules.cursor.*;
import gameEngine.interfaces.MenuInterface.*;
import gameEngine.interfaces.drawables.UIDrawable;
import utils.GraphicsTools;
import utils.GraphicsTools.MaskType;

public class TextField
        implements MouseNotifier, KeyNotifier, Clickable, Hoverable, UIDrawable, Updatable, MenuInterface, MenuSetSize,
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

    private Font fieldFont = new Font(Font.SANS_SERIF, Font.PLAIN, 0);
    private StringBuffer text = new StringBuffer("");
    private FontMetrics fontMetrics;

    private Runnable clickAction;

    private EngineContext context;
    private Keys keys;
    private Mouse mouse;

    // Behavioral variables
    private boolean isHovered;
    private boolean focused;
    private boolean enabled;
    private boolean pressed;

    private int caretOffset;
    private int highlightStartX = 0;
    private int highlightWidth = 0;

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

        fieldFont = GraphicsTools.matchFontToHeight(fieldFont, "ÅÄÖgjpqÉÁÂÂÂÂ",
                height - 2);
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
        fieldFont = GraphicsTools.matchFontToHeight(fieldFont, "ÅÄÖgjpqÉÁÂÂÂÂ",
                height - 2);
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

        fontMetrics = g.getFontMetrics(fieldFont);

        Graphics2D g2d = (Graphics2D) g;

        GraphicsTools.rotateGraphics(g2d, angle, getMiddlePoint(), (gRotate) -> {

            if (!isHovered) {
                // Background
                if (image == null) {
                    gRotate.setColor(color);
                    gRotate.fillRect(x, y, width, height);
                } else
                    gRotate.drawImage(image, x, y, width, height, null);
            } else if (!pressed) {
                // Background when hovered
                if (hoverImage == null) {
                    gRotate.setColor(hoverColor);
                    gRotate.fillRect(x, y, width, height);
                } else
                    gRotate.drawImage(hoverImage, x, y, width, height, null);
            } else {
                // Background when hovered and pressed
                if (pressedImage == null) {
                    gRotate.setColor(pressedColor);
                    gRotate.fillRect(x, y, width, height);
                } else
                    gRotate.drawImage(pressedImage, x, y, width, height, null);
            }

            GraphicsTools.createMask(gRotate, baseShape, MaskType.INSIDE, (gMask) -> {
                Composite old = gMask.getComposite();
                gMask.setComposite(AlphaComposite.SrcOver);

                gMask.setColor(GraphicsTools.rgba(0, 174, 255, 0.16));
                gMask.fillRect(highlightStartX, y, highlightWidth, height);

                if (cursorVisible && focused) {
                    gMask.setColor(Color.BLACK);
                    gMask.fillRect(
                            x + 10 + fontMetrics.stringWidth(text.substring(0, text.length() - caretOffset)),
                            y + 2, 2,
                            height - 4);
                }

                gMask.setFont(fontMetrics.getFont());
                gMask.setColor(Color.BLACK);
                gMask.drawString(text.toString(), x + 10, y + fontMetrics.getHeight() - fontMetrics.getDescent());

                gMask.setComposite(old);
            });
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
    public void keyTypedNotification() {
        if (!focused)
            return;

        cursorTimer = 0;
        cursorVisible = true;

        Character input = keys.pollTypedCharacter();

        if (input != null) {
            text.insert(text.length() - caretOffset, input);
        }

        if (keys.getKeysPressed().contains(KeyEvent.VK_BACK_SPACE)) {
            if (text.length() == 0)
                return;

            if (caretOffset != text.length()) {
                text.deleteCharAt(text.length() - 1 - caretOffset);
                return;
            }
        }

        if (keys.getKeysPressed().contains(KeyEvent.VK_DELETE)) {
            if (text.length() == 0)
                return;

            if (caretOffset != 0) {
                text.deleteCharAt(text.length() - caretOffset);
                caretOffset--;
                return;
            }
        }

    }

    @Override
    public void keyPressedNotification() {
        cursorTimer = 0;
        cursorVisible = true;

        if (keys.getKeysPressed().contains(KeyEvent.VK_LEFT) && caretOffset < text.length()) {
            caretOffset++;
        }

        if (keys.getKeysPressed().contains(KeyEvent.VK_RIGHT) && caretOffset > 0) {
            caretOffset--;
        }

    }

    @Override
    public void clickNotification(int x, int y) {
        if (!contains(x, y))
            return;

        cursorTimer = 0;
        cursorVisible = true;

        int caretPosition = 0;
        int closestDistance = Integer.MAX_VALUE;

        int caretX = this.x + 10;

        for (int i = 0; i <= text.length(); i++) {

            int distance = Math.abs(x - caretX);

            if (distance < closestDistance) {
                closestDistance = distance;
                caretPosition = i;
            }

            if (i < text.length()) {
                caretX += fontMetrics.charWidth(text.charAt(i));
            }
        }

        if (keys.getKeysPressed().contains(KeyEvent.VK_SHIFT)) {
            setMarkedCharacters(caretPosition, text.length() - caretOffset);
            return;
        } else
            clearMarkedCharacters();

        caretOffset = text.length() - caretPosition;
    }

    private void setMarkedCharacters(int start, int end) {

        if (start > end) {
            int tmp = start;
            start = end;
            end = tmp;
        }

        highlightStartX = x + 10
                + fontMetrics.stringWidth(text.substring(0, start));

        highlightWidth = fontMetrics.stringWidth(
                text.substring(start, end));
    }

    private void clearMarkedCharacters() {
        highlightStartX = 0;
        highlightWidth = 0;
    }
}
