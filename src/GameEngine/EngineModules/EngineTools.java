package GameEngine.EngineModules;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class EngineTools {

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
}