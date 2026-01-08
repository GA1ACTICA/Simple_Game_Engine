package AdvancedRendering.uiRendering.Button;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;

import GameEngine.EngineModules.ClassFactory;
import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.EngineTools;
import GameEngine.EngineModules.Mouse;
import GameEngine.Interfaces.UIDrawable;
import GameEngine.Interfaces.Updatable;

public class RectButton implements UIDrawable, Updatable {

    private int x, y, width, height;
    private double angle = 0;
    private boolean show = false;

    protected RectangularShape baseShape;
    protected Shape rotatedShape;

    private Color color = Color.GREEN;
    private Color hoverColor = Color.RED;

    private Image image;
    private Image hoverImage;

    public boolean inside;
    private boolean wasPressed;
    private boolean insideOveride = false;

    private Runnable action;
    private final Mouse mouse;

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

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();

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

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();

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
    public RectButton(Mouse mouse, EngineContext context, Point middle, int width, int height) {
        ClassFactory.create(this, context);

        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        this.width = width;
        this.height = height;
        this.mouse = mouse;

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();

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
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
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

    public void setImage(Image image) {
        this.image = image;
    }

    public void setHoverImage(Image hoverImage) {
        this.hoverImage = hoverImage;
    }

    public void setInsideOveride(boolean isInside) {
        insideOveride = isInside;

        if (isInside)
            inside = isInside;
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
    public void onClick(Runnable action) {
        this.action = action;

    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        Graphics2D g2d = (Graphics2D) g;

        // Create off-screen BufferImage for the button
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bg2d = buffer.createGraphics();

        bg2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bg2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Set baseShape to BufferedImage cordinate space
        baseShape.setFrame(0, 0, width, height);

        if (inside && hoverImage == null)
            bg2d.setColor(hoverColor);
        else if (image == null)
            bg2d.setColor(color);
        else
            bg2d.setColor(new Color(0, 0, 0, 0));

        bg2d.fill(baseShape);

        // Mask and draw image
        bg2d.setComposite(AlphaComposite.SrcIn);

        if (inside && hoverImage != null)
            bg2d.drawImage(hoverImage, 0, 0, width, height, null);
        else if (image != null)
            bg2d.drawImage(image, 0, 0, width, height, null);

        bg2d.dispose();

        EngineTools.rotateGraphics(g2d, angle, getMiddlePoint(), () -> {
            g2d.drawImage(buffer, x, y, null);
        });
    }

    @Override
    public void update() {
        if (!show)
            return;

        if (inside && mouse.getLeftDown() && !wasPressed)
            wasPressed = true;

        if (wasPressed && !mouse.getLeftDown()) {
            wasPressed = false;

            // Run action if one is set
            if (inside && action != null)
                action.run();

        }

        // Hitbox detection for the "rotatedShape"
        if (!insideOveride)
            inside = rotatedShape.contains(mouse.getX(), mouse.getY());
    }

    // Call updateRotatedShape everytime the position, size or rotation changes
    protected void updateRotatedShape() {

        AffineTransform transform = new AffineTransform();
        Point middle = getMiddlePoint();

        transform.rotate(Math.toRadians(angle), middle.x, middle.y);
        rotatedShape = transform.createTransformedShape(baseShape);
    }
}
