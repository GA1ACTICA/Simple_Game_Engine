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

package utils;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import gameEngine.interfaces.Painter;

public class GraphicsTools {

    public enum MaskType {
        INSIDE,
        OUTSIDE
    }

    /**
     * Returns a new {@link BufferedImage} containing the given image rotated
     * around its center by the specified angle.
     * <p>
     * The rotation angle is specified in degrees. Positive values result in a
     * clockwise visual rotation in screen coordinates.
     * <p>
     * The dimensions of the returned image may differ from the original to fully
     * contain the rotated image.
     *
     * @param image The image to rotate
     * 
     * @param angle The rotation angle in degrees
     *
     * @return a new {@code BufferedImage} containing the rotated image
     *
     * @throws NullPointerException if {@code image} is {@code null}
     */
    public static BufferedImage rotateImage(BufferedImage image, double angle) {

        Objects.requireNonNull(image, "image must not be null");

        int width = image.getWidth();
        int height = image.getHeight();

        // Calculate the new image size after rotation
        int newWidth = (int) Math.floor(
                width * Math.abs(Math.cos(Math.toRadians(angle)))
                        + height * Math.abs(Math.sin(Math.toRadians(angle))));
        int newHeight = (int) Math.floor(
                width * Math.abs(Math.sin(Math.toRadians(angle)))
                        + height * Math.abs(Math.cos(Math.toRadians(angle))));

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, image.getType());

        // Get Graphics2D from the new image
        Graphics2D g2d = rotatedImage.createGraphics();

        // Enable high-quality interpolation
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Set up the rotation point to be the center of the image
        g2d.rotate(Math.toRadians(angle), newWidth / 2.0, newHeight / 2.0);
        g2d.translate((newWidth - width) / 2.0, (newHeight - height) / 2.0);

        // Draw the original image onto the rotated graphics context
        g2d.drawImage(image, 0, 0, null);

        g2d.dispose();

        return rotatedImage;
    }

    /**
     * Rotates the {@link Graphics2D} context around a given point, executes the
     * specified drawing action, and then restores the original transform.
     * <p>
     * The rotation is applied only for the duration of {@code drawAction} and the
     * {@code AffineTransform} is restored after {@code drawAction} is complete.
     *
     * @param g2d           The {@code Graphics2D} context to rotate
     * 
     * @param angle         The rotation angle in degrees (positive values rotate
     *                      clockwise in screen coordinates)
     * 
     * @param rotationPoint The point around which the graphics context is rotated
     * 
     * @param drawAction    The drawing operation to be executed while the rotation
     *                      is applied
     * 
     * @throws NullPointerException if {@code rotationPoint} or
     *                              {@code drawAction} is {@code null}
     */
    public static void rotateGraphics(
            Graphics2D g2d,
            double angle,
            Point rotationPoint,
            Painter drawAction) {

        Objects.requireNonNull(rotationPoint, "rotationPoint must not be null");
        Objects.requireNonNull(drawAction, "drawAction must not be null");

        AffineTransform oldTransform = g2d.getTransform();

        // Rotate around "rotationPoint"
        g2d.rotate(
                Math.toRadians(angle),
                rotationPoint.getX(),
                rotationPoint.getY());

        // Draw
        drawAction.paint(g2d);

        // Restore original transform
        g2d.setTransform(oldTransform);

        // DO NOT DISPOSE GRAPHICS!!!
    }

    /**
     * work in progress
     * 
     * @param mask
     * 
     * @param width
     * 
     * @param height
     * 
     * @param painter
     * 
     * @return
     * 
     * @throws NullPointerException if {@code mask} is {@code null}
     */
    public static void createMask(
            Graphics2D g2d,
            Shape mask,
            MaskType type,
            Painter painter) {

        Objects.requireNonNull(g2d, "g2d must not be null");
        Objects.requireNonNull(mask, "mask must not be null");
        Objects.requireNonNull(painter, "painter must not be null");

        int x = (int) mask.getBounds2D().getX();
        int y = (int) mask.getBounds2D().getY();
        int width = (int) mask.getBounds2D().getWidth();
        int height = (int) mask.getBounds2D().getHeight();

        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Get Graphics2D from the new image
        Graphics2D bufferG2d = buffer.createGraphics();

        bufferG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        bufferG2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        AffineTransform tx = new AffineTransform(g2d.getTransform());
        bufferG2d.setTransform(tx);
        bufferG2d.translate(-x, -y);

        // Create mask alpha
        bufferG2d.setComposite(AlphaComposite.Src);
        bufferG2d.setColor(Color.WHITE);
        bufferG2d.fill(mask);

        // Draw content through mask
        switch (type) {
            case MaskType.INSIDE:
                bufferG2d.setComposite(AlphaComposite.SrcIn);
                break;

            case MaskType.OUTSIDE:
                bufferG2d.setComposite(AlphaComposite.SrcOut);
                break;
        }
        painter.paint(bufferG2d);

        g2d.drawImage(buffer, x, y, width, height, null);
    }

    /**
     * Scales an image to the specified target size.
     * <p>
     * The image is downscaled progressively by repeatedly halving its size
     * using {@code bicubic} interpolation via {@link RenderingHints}.
     * The scaling is preformed in multiple steps helps preserve image quality when
     * scaling across large size differences.
     *
     * @param image        the image to scale
     * @param targetWidth  the target width in pixels
     * @param targetHeight the target height in pixels
     *
     * @return the scaled image
     *
     * @throws NullPointerException if {@code image} is {@code null}
     */
    public static BufferedImage downscaleImage(
            BufferedImage image,
            int targetWidth,
            int targetHeight) {

        Objects.requireNonNull(image, "Image must not be null");

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage current = image;

        // Half the size until it is close to the target width and height.
        while (width / 2 >= targetWidth &&
                height / 2 >= targetHeight) {

            width /= 2;
            height /= 2;

            BufferedImage temp = new BufferedImage(
                    width,
                    height,
                    BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = temp.createGraphics();

            g.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            g.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g.drawImage(current, 0, 0, width, height, null);

            g.dispose();

            current = temp;
        }

        // Final render pass.
        BufferedImage result = new BufferedImage(
                targetWidth,
                targetHeight,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = result.createGraphics();

        g.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(current, 0, 0,
                targetWidth, targetHeight, null);

        g.dispose();

        return result;
    }

    /**
     * A simple red "X" centered at the x and y position.
     * <p>
     * This can be used to visualize coordinates when debugging position related
     * issues.
     * 
     * @param g The graphics context
     * 
     * @param x The x coordinate
     * 
     * @param y The y coordinate
     * 
     * @throws NullPointerException if {@code g} is {@code null}.
     * 
     */
    public static void debugShape(Graphics g, int x, int y) {
        Objects.requireNonNull(g, "g must not be null");
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(x - 5, y - 5, x + 5, y + 5);
        g2d.drawLine(x - 5, y + 5, x + 5, y - 5);

    }

    /**
     * A simple red rectangle that shows the bounding for the image as long as the x
     * and y coordinates are the same as the images'.
     * <p>
     * This can be used to visualize the images size when debugging image and
     * position related issues.
     * 
     * @param g     The graphics context
     * 
     * @param image The image
     * 
     * @param x     The x coordinate
     * 
     * @param y     The y coordinate
     * 
     * @throws NullPointerException if {@code g} or {@code image} is {@code null}.
     */
    public static void imageBoundingBox(Graphics g, Image image, int x, int y) {
        Objects.requireNonNull(g, "g must not be null");
        Objects.requireNonNull(image, "image must not be null");

        Graphics2D g2d = (Graphics2D) g;

        int width = image.getWidth(null);
        int height = image.getHeight(null);

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(x, y, width, height);
    }

    /**
     * Returns a resized version of the supplied {@link java.awt.Font Font} whose
     * rendered height is greater than or equal to the target height.
     * <p>
     * The font size is increased one point at a time starting from size {@code 1}
     * until the measured height of the font reaches or exceeds the target height.
     * <p>
     * Font height is measured using
     * {@link java.awt.font.LineMetrics LineMetrics} with the supplied dummy text.
     * <p>
     * <b>Note:</b> The returned font may exceed the target height since the first
     * font size whose height is greater than or equal to the target is returned.
     *
     * @param font         the base font used to derive resized fonts
     *
     * @param dummyText    the text used when calculating font metrics
     *
     * @param targetHeight the minimum desired rendered font height
     *
     * @return a resized font whose rendered height is greater than or equal to the
     *         target height
     */
    public static Font matchFontToHeight(Font font, String dummyText, int targetHeight) {

        int size = 1;
        FontRenderContext renderContext = new FontRenderContext(null, true, true);

        while (true) {
            LineMetrics metrics = font.getLineMetrics(dummyText, renderContext);

            if (metrics.getHeight() >= targetHeight) {
                return font;
            }

            size++;

            font = font.deriveFont((float) size);

        }
    }

    /**
     * Creates a color from red, green, blue, and alpha components. Every value
     * should fall between (0-255).
     *
     * @param r red channel value
     * 
     * @param g green channel value
     * 
     * @param b blue channel value
     * 
     * @param a alpha channel value
     * 
     * @return a new Color instance
     * 
     * @throws IllegalArgumentException if {@code r}, {@code g}, {@code b} or
     *                                  {@code a} are outside the range of 0 to
     *                                  255.
     * 
     * @see #rgb(int, int, int)
     */
    public static Color rgba(int r, int g, int b, int a) {
        return new Color(r, g, b, a);
    }

    /**
     * Creates an opaque color from red, green, and blue components. Every value
     * should fall between (0-255).
     *
     * @param r red channel value
     * 
     * @param g green channel value
     * 
     * @param b blue channel value
     * 
     * @return a new Color instance
     * 
     * @throws IllegalArgumentException if {@code r}, {@code g} or {@code b} are
     *                                  outside the range of 0 to 255.
     * 
     * @see #rgba(int, int, int, int)
     */
    public static Color rgb(int r, int g, int b) {
        return new Color(r, g, b);
    }
}
