package Utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class FileTools {

    /**
     * Get an image from the specified file path.
     * 
     * @param path Path to the image file, relative to the project
     *             resources.
     * @return The Image loaded from the given file path.
     */
    public static Image getImage(String path) {
        Image image = new ImageIcon(FileTools.class.getClassLoader().getResource(path)).getImage();
        return image;
    }

    /**
     * Get an image from the specified file path.
     * 
     * @param filePathFromProject Path to the image file, relative to the project
     *                            resources.
     * @return The BufferedImage loaded from the given file path.
     */
    public static BufferedImage getBufferedImage(String filePathFromProject, int type) {

        BufferedImage image = convertToBufferedImage(getImage(filePathFromProject), type);

        return image;
    }

    /**
     * 
     * @param image Input Image to be cast to BufferedImage
     * 
     * @return The casted BufferedImage
     */
    public static BufferedImage convertToBufferedImage(Image image, int type) {

        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // Create a BufferedImage with the same width and height as the original image
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                type);

        // Draw the original image onto the BufferedImage using Graphics2D
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);

        g2d.dispose();

        return bufferedImage;
    }
}
