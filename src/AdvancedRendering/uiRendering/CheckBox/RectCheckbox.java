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

package AdvancedRendering.uiRendering.CheckBox;

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

import GameEngine.EngineModules.ClassFactory;
import GameEngine.EngineModules.CursorManager;
import GameEngine.EngineModules.CursorManager.CursorType;
import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.EnginePanel;
import GameEngine.EngineModules.Mouse;
import GameEngine.Interfaces.MenuInterface.*;
import GameEngine.Interfaces.*;
import GameEngine.Interfaces.Drawables.UIDrawable;
import Utils.GraphicsTools;

public class RectCheckbox implements UIDrawable, MenuInterface, MenuSetPosition, MenuSetSize,
        MenuSetHoverVisual, MenuSetImage, MenuSetColor, Clickable, Hoverable {

    private int zIndex = 0;

    private int x, y, width, height;
    private double angle = 0;
    private boolean show = false;

    protected RectangularShape baseShape;
    protected Shape rotatedShape;

    private Color color = Color.GREEN;
    private Color hoverColor = Color.ORANGE;
    private Color toggleColorTrue = Color.RED;
    private Color disabledColor = Color.LIGHT_GRAY;
    private Color clickColor = new Color(255, 255, 255, 150);

    private Image image;
    private Image hoverImage;
    private Image toggleImage;
    private Image disabledImage;
    private Image clickImage;

    private Runnable onClickAction;
    private Runnable onToggleTrueAction;
    private Runnable onToggleFalseAction;

    private boolean enabled = true;
    private boolean clickEffect = true;
    private boolean clicked = false;

    private boolean toggled = false;
    private boolean showHover = false;

    // private Runnable hoverAction; // TODO: look into this
    private boolean isHovered;

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
    public RectCheckbox(EngineContext context, EnginePanel panel, Mouse mouse, int x, int y, int width, int height) {

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
    public RectCheckbox(EngineContext context, EnginePanel panel, Mouse mouse, Point topLeft, Point bottomRight) {

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
    public RectCheckbox(EngineContext context, EnginePanel panel, Mouse mouse, Point middle, int width, int height) {

        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        this.width = width;
        this.height = height;

        this(context, mouse, panel);

    }

    private RectCheckbox(EngineContext context, Mouse mouse, EnginePanel panel) {
        ClassFactory.create(this, context, zIndex);

        this.mouse = mouse;
        this.context = context;

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();

    }

    /**
     * @param zIndex
     */
    @Override
    public void setZIndex(int zIndex) {
        ClassFactory.updatePriority(this, context, zIndex);
        this.zIndex = zIndex;
    }

    /**
     * @return int
     */
    @Override
    public int getZIndex() {
        return zIndex;
    }

    /**
     * @return boolean
     */
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

    /**
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    /**
     * @param x
     * @param y
     */
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
     * @param middle
     */
    public void setCenter(Point middle) {
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
     * @param color
     */
    // ————————— Set colors ——————————
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @param toggleColorTrue
     */
    public void setToggleColorTrue(Color toggleColorTrue) {
        this.toggleColorTrue = toggleColorTrue;
    }

    /**
     * @param hoveColor
     */
    public void setHoverColor(Color hoveColor) {
        this.hoverColor = hoveColor;
    }

    /**
     * @param disabledColor
     */
    public void setDisabledColor(Color disabledColor) {
        this.disabledColor = disabledColor;
    }

    /**
     * @param image
     */
    // —————————— Set images ——————————
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * @param toggleImage
     */
    public void setToggleImageTrue(Image toggleImage) {
        this.toggleImage = toggleImage;
    }

    /**
     * @param hoverImage
     */
    public void setHoverImage(Image hoverImage) {
        this.hoverImage = hoverImage;
    }

    /**
     * @param disabledImage
     */
    public void setDisabledImage(Image disabledImage) {
        this.disabledImage = disabledImage;
    }

    /**
     * @param enabled
     */
    // ————————————————————————————————

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @param showHover
     */
    public void setShowHover(boolean showHover) {
        this.showHover = showHover;
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
    public Point getCenter() {
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
     * @return boolean
     */
    @Override
    public boolean isHovered() {
        return isHovered;
    }

    /**
     * This lets you run code when the button is pressed with the method:
     * 
     * <p>
     * {@code onClick(() -> {});}
     * <p>
     * 
     * @param action
     */
    @Override
    public void onClick(Runnable onClickAction) {
        this.onClickAction = onClickAction;

    }

    /**
     * @param onToggleTrueAction
     */
    public void onToggleTrue(Runnable onToggleTrueAction) {
        this.onToggleTrueAction = onToggleTrueAction;
    }

    /**
     * @param onToggleFalseAction
     */
    public void onToggleFalse(Runnable onToggleFalseAction) {
        this.onToggleFalseAction = onToggleFalseAction;
    }

    /**
     * @param g
     */
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

            if (!toggled) {
                // Draw if the image is not set
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

            } else if (toggled) {

                // Draw if the toggleImage is not set
                if (image == null) {
                    g2d.setColor(toggleColorTrue);
                    g2d.fill(baseShape);

                } else {

                    BufferedImage buffer = GraphicsTools.createMask(
                            baseShape,
                            width,
                            height,
                            gMask -> {

                                gMask.drawImage(toggleImage, 0, 0, width, height, null);
                            });
                    g2d.drawImage(buffer, x, y, null);

                }
            }

            if (showHover) {

                // Draw if the hoverImage is not set and inside is true
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

    /**
     * @param mouseX
     * @param mouseY
     * @return boolean
     */
    @Override
    public boolean contains(int mouseX, int mouseY) {
        return rotatedShape.contains(mouse.getPoint().x, mouse.getPoint().y);
    }

    @Override
    public void executeOnClick() {
        toggled = !toggled;

        // Run action if one is set
        if (onClickAction != null)
            onClickAction.run();

        if (toggled && onToggleTrueAction != null)
            onToggleTrueAction.run();
        else if (!toggled && onToggleFalseAction != null)
            onToggleFalseAction.run();
    }

    /**
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param isHovered
     */
    @Override
    public void setHovered(boolean isHovered) {
        this.isHovered = isHovered;

        if (isHovered)
            CursorManager.setCursor(CursorType.POINTER);
        else
            CursorManager.setCursor(CursorType.DEFAULT);
    }

    @Override
    public void onReleased() {
        clicked = false;
    }

    @Override
    public void onPressed() {
        clicked = true;
    }

}
