package Utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import GameEngine.Interfaces.InterfacePainter;

public class GraphicsTools {

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
                        Runnable drawAction) {

                Objects.requireNonNull(rotationPoint, "rotationPoint must not be null");
                Objects.requireNonNull(drawAction, "drawAction must not be null");

                AffineTransform oldTransform = g2d.getTransform();

                // Rotate around "rotationPoint"
                g2d.rotate(
                                Math.toRadians(angle),
                                rotationPoint.getX(),
                                rotationPoint.getY());

                // Draw
                drawAction.run();

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
        public static BufferedImage createMask(
                        Shape mask,
                        int width,
                        int height,
                        InterfacePainter painter) {

                Objects.requireNonNull(mask, "mask must not be null");

                BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                // Get Graphics2D from the new image
                Graphics2D g2d = buffer.createGraphics();

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                Shape localMask = mask;

                // Transform mask from "world space" to "local space"
                if (mask.getBounds2D().getX() != 0 || mask.getBounds().getY() != 0) {

                        Rectangle2D boundingBox = mask.getBounds2D();

                        // Translate to "local space" x: 0 -> getX() y: 0 -> getY()
                        AffineTransform transform = AffineTransform.getTranslateInstance(-boundingBox.getX(),
                                        -boundingBox.getY());

                        localMask = transform.createTransformedShape(mask);
                }

                // Paint mask alpha
                g2d.setComposite(AlphaComposite.Src);
                g2d.setColor(Color.WHITE);
                g2d.fill(localMask);

                // Mask content
                g2d.setComposite(AlphaComposite.SrcIn);
                painter.paint(g2d);

                g2d.dispose();

                return buffer;
        }
}
