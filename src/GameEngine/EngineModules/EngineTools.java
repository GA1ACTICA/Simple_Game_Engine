package GameEngine.EngineModules;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.swing.ImageIcon;

import GameEngine.EngineModules.Records.FixResult;

public class EngineTools {

    /**
     * 
     * @param pointOne
     * 
     * @param pointTwo
     * 
     * @return
     */
    public static double pythagoras(Point pointOne, Point pointTwo) {

        double deltaX = pointTwo.getX() - pointOne.getX();
        double deltaY = pointTwo.getY() - pointOne.getY();

        return Math.hypot(deltaX, deltaY);
    }

    /**
     * 
     * @param x1
     * 
     * @param y1
     * 
     * @param x2
     * 
     * @param y2
     * 
     * @return
     */
    public static double pythagoras(int x1, int y1, int x2, int y2) {

        double deltaX = x2 - x1;
        double deltaY = y2 - y1;

        return Math.hypot(deltaX, deltaY);
    }

    /**
     * Get an image from the specified file path.
     * 
     * @param filePathFromProject Path to the image file, relative to the project
     *                            resources.
     * @return The Image loaded from the given file path.
     */
    public static Image getImage(String filePathFromProject) {
        Image image = new ImageIcon(EngineTools.class.getClassLoader().getResource(filePathFromProject)).getImage();
        return image;
    }

    /**
     * Get an image from the specified file path.
     * 
     * @param filePathFromProject Path to the image file, relative to the project
     *                            resources.
     * @return The BufferedImage loaded from the given file path.
     */
    public static BufferedImage getBufferedImage(String filePathFromProject) {

        BufferedImage image = convertToBufferedImage(getImage(filePathFromProject));

        return image;
    }

    /**
     * 
     * @param image Input Image to be cast to BufferedImage
     * 
     * @return The casted BufferedImage
     */
    public static BufferedImage convertToBufferedImage(Image image) {

        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // Create a BufferedImage with the same width and height as the original image
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the original image onto the BufferedImage using Graphics2D
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return bufferedImage;
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
                width * Math.abs(Math.cos(Math.toRadians(angle))) + height * Math.abs(Math.sin(Math.toRadians(angle))));
        int newHeight = (int) Math.floor(
                width * Math.abs(Math.sin(Math.toRadians(angle))) + height * Math.abs(Math.cos(Math.toRadians(angle))));

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

        // Restore original transform and clipS
        g2d.setTransform(oldTransform);
    }

    /**
     * Projects a reference point onto the line segment defined by {@code pointOne}
     * and {@code pointTwo}, returning the closest point on that segment.
     * <p>
     * The returned point is constrained to lie between {@code pointOne} and
     * {@code pointTwo}. If the perpendicular projection of the reference point
     * lies outside the segment, the nearest endpoint is returned instead.
     * <p>
     * This is commonly used for constraining input (e.g. a mouse position)
     * to move along a fixed line.
     *
     * @param refrence The point to be projected onto the line segment (e.g. mouse
     *                 position)
     * 
     * @param pointOne The start point of the line segment
     * 
     * @param pointTwo The end point of the line segment
     *
     * @return a {@link FixResult} containing:
     *         <ul>
     *         <li>the constrained point on the line segment</li>
     *         <li>a progress value in the range {@code [0.0, 1.0]}, where
     *         {@code 0.0} corresponds to {@code pointOne} and
     *         {@code 1.0} corresponds to {@code pointTwo}</li>
     *         </ul>
     * 
     * @throws NullPointerException if {@code refrence}, {@code pointOne}, or
     *                              {@code pointTwo} is {@code null}
     */
    public static FixResult fixToLine(Point refrence, Point pointOne, Point pointTwo) {

        Objects.requireNonNull(refrence, "refrence must not be null");
        Objects.requireNonNull(pointOne, "pointOne must not be null");
        Objects.requireNonNull(pointTwo, "pointTwo must not be null");

        double deltaX = pointTwo.x - pointOne.x;
        double deltaY = pointTwo.y - pointOne.y;

        double mx = refrence.x - pointOne.x;
        double my = refrence.y - pointOne.y;

        double lengthSquared = deltaX * deltaX + deltaY * deltaY;

        // Start and end are the same point
        if (lengthSquared == 0)
            return new FixResult(new Point(pointOne.x, pointOne.y), 0);

        double progress = (mx * deltaX + my * deltaY) / lengthSquared;

        // Fix "progress" inbetween point one and two
        progress = Math.max(0, Math.min(1, progress));

        int pointX = (int) Math.round(pointOne.x + progress * deltaX);
        int pointY = (int) Math.round(pointOne.y + progress * deltaY);

        return new FixResult(new Point(pointX, pointY), progress);
    }
}