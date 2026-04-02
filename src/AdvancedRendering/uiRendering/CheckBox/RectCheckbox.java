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
        MenuSetHoverVisual, MenuSetToggleVisual, MenuSetImage, MenuSetColor, Clickable, Hoverable {

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

    // private Runnable hoverAction; // TODO: look into this
    private boolean isHovered = false;
    private boolean showHover = false;

    private Mouse mouse;
    private EngineContext context;

    /**
     * Creates and registers a rectangular checkbox with the specified dimensions.
     * 
     * @param context The engine context containing objects involved in rendering,
     *                updating, and input handling.
     * 
     * @param panel   The panel on which the checkbox is drawn to.
     * 
     * @param mouse   The mouse input handler used for interaction with the
     *                checkbox.
     * 
     * @param x       The x-coordinate of the rectangle's topLeft point.
     * 
     * @param y       The y-coordinate of the rectangle's topLeft point.
     * 
     * @param width   The width of the rectangle.
     * 
     * @param height  The height of the rectangle.
     */
    public RectCheckbox(EngineContext context, EnginePanel panel, Mouse mouse, int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this(context, mouse, panel);

    }

    /**
     * Creates and registers a rectangular checkbox with the specified points.
     * 
     * @param context     The engine context containing objects involved in
     *                    rendering, updating, and input handling.
     * 
     * @param panel       The panel on which the checkbox is drawn to.
     * 
     * @param mouse       The mouse input handler used for interaction with the
     *                    checkbox.
     * 
     * @param topLeft     The top left point of the rectangle.
     * 
     * @param bottomRight The bottom left point of the rectangle.
     */
    public RectCheckbox(EngineContext context, EnginePanel panel, Mouse mouse, Point topLeft, Point bottomRight) {

        x = (int) topLeft.getX();
        y = (int) topLeft.getY();
        width = (int) bottomRight.getX() - (int) topLeft.getX();
        height = (int) bottomRight.getY() - (int) topLeft.getY();

        this(context, mouse, panel);

    }

    /**
     * Creates and registers a rectangular checkbox with the specified dimensions
     * and
     * center point.
     *
     * @param context The engine context containing objects involved in rendering,
     *                updating, and input handling.
     * 
     * @param panel   The panel on which the checkbox is drawn to.
     * 
     * @param mouse   The mouse input handler used for interaction with the
     *                checkbox.
     * 
     * @param center  The center point of the rectangle.
     * 
     * @param width   The width of the rectangle.
     * 
     * @param height  The height of the rectangle.
     */
    public RectCheckbox(EngineContext context, EnginePanel panel, Mouse mouse, Point center, int width, int height) {

        x = (int) center.getX() - width / 2;
        y = (int) center.getY() - height / 2;
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

    // ————————— Set colors ——————————
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setToggleColor(Color toggleColorTrue) {
        this.toggleColorTrue = toggleColorTrue;
    }

    @Override
    public void setHoverColor(Color hoveColor) {
        this.hoverColor = hoveColor;
    }

    public void setDisabledColor(Color disabledColor) {
        this.disabledColor = disabledColor;
    }

    // —————————— Set images ——————————
    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void setToggleImage(Image toggleImage) {
        this.toggleImage = toggleImage;
    }

    @Override
    public void setHoverImage(Image hoverImage) {
        this.hoverImage = hoverImage;
    }

    public void setDisabledImage(Image disabledImage) {
        this.disabledImage = disabledImage;
    }
    // ————————————————————————————————

    /**
     * Sets the center position of the button. This recalculates the
     * top-left coordinates based on the current width and height,
     * updates the base shape, and refreshes the rotated shape.
     *
     * @param center the new center position
     */
    public void setCenter(Point center) {
        x = (int) Math.round(center.getX() - width / 2);
        y = (int) Math.round(center.getY() - height / 2);
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    /**
     * Sets the rotation of the button.
     * Positive angles rotate clockwise, negative angles rotate counterclockwise.
     *
     * <p>
     * <b>Note:</b> Positioning methods return values based on the unrotated
     * shape, not the visually rotated one.
     * </p>
     *
     * @param angle the rotation angle in degrees
     */
    public void setRotation(double angle) {
        this.angle = angle;

        updateRotatedShape();
    }

    /**
     * Enables or disables the visual click effect (color or image change)
     * when the button is pressed.
     *
     * @param clickEffect true to enable the click effect, false to disable it
     */
    public void setClickEffectEnabled(boolean enabled) {
        clickEffect = enabled;
    }

    /**
     * Enables or disables the visual hover effect (color or image change)
     * when the button is hovered.
     *
     * @param hoverEffect true to enable the hover effect, false to disable it
     */
    public void setHoverEffectEnabled(boolean hoverEffect) {
        showHover = hoverEffect;
    }

    /**
     * Set the ability to interact with the checkbox. This also changes the
     * appearances to the disabled state.
     * 
     * @param enabled true to disable the checkbox, false to enable it
     * 
     * @see #setDisabledColor(Color)
     * @see #setDisabledImage(Image)
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 
     * @param state
     */
    public void setCheckboxState(boolean state) {
        toggled = state;

        executeToggleAction();
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

    public double getAngle() {
        return angle;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean isHovered() {
        return isHovered;
    }

    public boolean getCheckboxState() {
        return toggled;
    }

    @Override
    public void onClick(Runnable onClickAction) {
        this.onClickAction = onClickAction;

    }

    /**
     * Sets the action to be executed whenever the checkbox is set to {@code true}.
     *
     * <p>
     * The provided {@link Runnable} is invoked whenever the checkbox is set to the
     * {@code true} state. This can be achieved either by calling
     * {@link #executeOnClick()} were the previous state was {@code false} or by
     * calling {@link #setCheckboxState(boolean)} with the parameter {@code true}.
     * </p>
     *
     * <p>
     * Example usage:
     * </p>
     * 
     * <pre>
     * checkbox.onToggleTrue(() -> {
     *     System.out.println("Checkbox is true!");
     * });
     * </pre>
     *
     * @param onToggleTrueAction the action to execute when the checkbox becomes
     *                           {@code true}
     */
    public void onToggleTrue(Runnable onToggleTrueAction) {
        this.onToggleTrueAction = onToggleTrueAction;
    }

    /**
     * Sets the action to be executed whenever the checkbox is set to {@code false}.
     *
     * <p>
     * The provided {@link Runnable} is invoked whenever the checkbox is set to the
     * {@code false} state. This can be achieved either by calling
     * {@link #executeOnClick()} were the previous state was {@code true} or by
     * calling {@link #setCheckboxState(boolean)} with the parameter {@code false}.
     * </p>
     *
     * <p>
     * Example usage:
     * </p>
     * 
     * <pre>
     * checkbox.onToggleFalse(() -> {
     *     System.out.println("Checkbox is false!");
     * });
     * </pre>
     *
     * @param onToggleFalseAction the action to execute when the checkbox becomes
     *                            {@code false}
     */
    public void onToggleFalse(Runnable onToggleFalseAction) {
        this.onToggleFalseAction = onToggleFalseAction;
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

            } else {

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

            if (isHovered && showHover) {

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
                // Draws this if checkbox is clicked
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

    /**
     * Updates the rotated version of the base shape based on the current angle.
     * The rotation is performed around the shape's center point.
     *
     * This method should be called whenever the position, size, or rotation
     * changes.
     */
    protected void updateRotatedShape() {

        AffineTransform transform = new AffineTransform();
        Point center = getCenter();

        transform.rotate(Math.toRadians(angle), center.x, center.y);
        rotatedShape = transform.createTransformedShape(baseShape);
    }

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

        executeToggleAction();
    }

    private void executeToggleAction() {
        if (toggled && onToggleTrueAction != null)
            onToggleTrueAction.run();
        else if (!toggled && onToggleFalseAction != null)
            onToggleFalseAction.run();
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
    public void onReleased() {
        clicked = false;
    }

    @Override
    public void onPressed() {
        clicked = true;
    }
}
