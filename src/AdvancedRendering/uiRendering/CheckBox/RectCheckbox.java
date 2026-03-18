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
import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.EnginePanel;
import GameEngine.EngineModules.Mouse;
import GameEngine.Interfaces.MenuInterface.*;
import GameEngine.Interfaces.*;
import GameEngine.Interfaces.Drawables.UIDrawable;
import Utils.CustomCursor;
import Utils.GraphicsTools;

public class RectCheckbox implements UIDrawable, Updatable, MenuInterface, MenuSetPosition, MenuSetSize,
        MenuSetHoverVisual, MenuSetImage, MenuSetColor, Clickable {

    private int zIndex = 0;

    private int x, y, width, height;
    private double angle = 0;
    private boolean show = false;

    protected RectangularShape baseShape;
    protected Shape rotatedShape;

    private Color color = Color.GREEN;
    private Color hoverColor = Color.RED;
    private Color toggleColor = Color.ORANGE;

    private Image image;
    private Image hoverImage;
    private Image toggleImage;

    private boolean inside;
    private boolean disabled = false;
    private boolean overide;

    private Runnable onClickAction;
    private Runnable onToggleTrueAction;
    private Runnable onToggleFalseAction;

    private Mouse mouse;
    private EngineContext context;

    private boolean toggled;
    private boolean showHover = false;

    public CustomCursor notAllowed;

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
        ClassFactory.create(this, context);

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
    public boolean getVisible() {
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

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

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
    public void changePosition(int x, int y) {
        this.x += x;
        this.y += y;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
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

    public void setColor(Color color) {
        this.color = color;
    }

    public void setHoverColor(Color hoveColor) {
        this.hoverColor = hoveColor;
    }

    public void setToggleColor(Color toggleColor) {
        this.toggleColor = toggleColor;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setHoverImage(Image hoverImage) {
        this.hoverImage = hoverImage;
    }

    public void setToggleImage(Image toggleImage) {
        this.toggleImage = toggleImage;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;

        if (disabled)
            inside = !disabled;
    }

    public void setShowHover(boolean showHover) {
        this.showHover = showHover;
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

    public void onToggleTrue(Runnable onToggleTrueAction) {
        this.onToggleTrueAction = onToggleTrueAction;
    }

    public void onToggleFalse(Runnable onToggleFalseAction) {
        this.onToggleFalseAction = onToggleFalseAction;
    }

    // TODO: fix seperate color for disabeled
    // TODO: change name for insideOvertide to disable

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        Graphics2D g2d = (Graphics2D) g;

        // Rotate everything drawn inside
        GraphicsTools.rotateGraphics(g2d, angle, getMiddlePoint(), () -> {

            // Draw if the image is not set
            if ((image == null && !(inside || disabled)) ||
                    (image == null && !showHover && !toggled)) {
                g2d.setColor(color);
                g2d.fill(baseShape);
            }

            // Draw if image is set
            if ((image != null && !(inside || disabled)) ||
                    (image != null && !showHover && !toggled)) {
                BufferedImage buffer = GraphicsTools.createMask(
                        baseShape,
                        width,
                        height,
                        gMask -> {

                            gMask.drawImage(image, 0, 0, width, height, null);
                        });
                g2d.drawImage(buffer, x, y, null);

            }

            // Draw if the toggleImage is not set
            if ((image == null && !(inside || disabled) && toggled) ||
                    (image == null && toggled && !showHover)) {
                g2d.setColor(toggleColor);
                g2d.fill(baseShape);
            }

            // Draw if toggleImage is set
            if ((image != null && !(inside || disabled) && toggled) ||
                    (image != null && toggled && !showHover)) {
                BufferedImage buffer = GraphicsTools.createMask(
                        baseShape,
                        width,
                        height,
                        gMask -> {

                            gMask.drawImage(toggleImage, 0, 0, width, height, null);
                        });
                g2d.drawImage(buffer, x, y, null);

            }

            // Draw if the hoverImage is not set and inside is true
            if (showHover && (inside || disabled) && hoverImage == null) {
                g2d.setColor(hoverColor);
                g2d.fill(baseShape);
            }

            // Draw if hoverImage is set
            if (showHover && (inside || disabled) && hoverImage != null) {

                BufferedImage buffer = GraphicsTools.createMask(
                        baseShape,
                        width,
                        height,
                        gMask -> {

                            gMask.drawImage(hoverImage, 0, 0, width, height, null);

                        });
                g2d.drawImage(buffer, x, y, null);

            }

        });

    }

    @Override
    public void update() {
        if (!show)
            return;

        // Hit box detection for the "rotatedShape"
        if (!disabled)
            inside = rotatedShape.contains(mouse.getPoint().x, mouse.getPoint().y);
    }

    // Call updateRotatedShape every time the position, size or rotation changes
    protected void updateRotatedShape() {

        AffineTransform transform = new AffineTransform();
        Point middle = getMiddlePoint();

        transform.rotate(Math.toRadians(angle), middle.x, middle.y);
        rotatedShape = transform.createTransformedShape(baseShape);
    }

    private void pressed() {
        toggled = !toggled;

        // Run action if one is set
        if (inside && onClickAction != null)
            onClickAction.run();

        if (toggled && onToggleTrueAction != null)
            onToggleTrueAction.run();
        else if (!toggled && onToggleFalseAction != null)
            onToggleFalseAction.run();
    }

    @Override
    public boolean contains(int mouseX, int mouseY) {
        return rotatedShape.contains(mouse.getPoint().x, mouse.getPoint().y);
    }

    @Override
    public void executeOnClick() {
        pressed();
    }

    @Override
    public boolean getDisabled() {
        return disabled;
    }

}
