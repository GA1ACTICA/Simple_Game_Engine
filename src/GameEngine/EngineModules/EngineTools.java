package GameEngine.EngineModules;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

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
     * 
     * @param image The BufferedImage to be rotated
     * 
     * @param angle Angle in degres to rotate the BufferedImage clockwise
     * 
     * @return The rotated BufferedImage
     */
    public static BufferedImage rotateImage(BufferedImage image, double angle) {

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

        // Set up the rotation point to be the center of the image
        g2d.rotate(Math.toRadians(angle), newWidth / 2.0, newHeight / 2.0);
        g2d.translate((newWidth - width) / 2.0, (newHeight - height) / 2.0);

        // Draw the original image onto the rotated graphics context
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

    /**
     * Fixes a Point to a line between Point one and two and moves acording to a
     * refrence position elsewhere. That Point could for example be a mouse
     * position.
     * 
     * @param refrence
     * 
     * @param pointOne
     * 
     * @param pointTwo
     * 
     * @return
     */
    public static Point fixToLine(Point refrence, Point pointOne, Point pointTwo) {
        double deltaX = pointTwo.x - pointOne.x;
        double deltaY = pointTwo.y - pointOne.y;

        double mx = refrence.x - pointOne.x;
        double my = refrence.y - pointOne.y;

        double lengthSquared = deltaX * deltaX + deltaY * deltaY;

        // Start and end are the same point
        if (lengthSquared == 0)
            return new Point(pointOne.x, pointOne.y);

        double progress = (mx * deltaX + my * deltaY) / lengthSquared;

        // Fix "progress" inbetween point one and two
        progress = Math.max(0, Math.min(1, progress));

        int pointX = (int) Math.round(pointOne.x + progress * deltaX);
        int pointY = (int) Math.round(pointOne.y + progress * deltaY);

        return new Point(pointX, pointY);
    }
}