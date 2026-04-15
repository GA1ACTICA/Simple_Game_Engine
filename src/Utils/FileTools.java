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

package Utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

import javax.swing.ImageIcon;

public class FileTools {

    /**
     * Get an image from the specified file path.
     * 
     * @param path Path to the image file, relative to the project
     *             resources.
     * @return The Image loaded from the given file path.
     * 
     * @see Image
     */
    public static Image getImage(Path path) {
        Image image = new ImageIcon(FileTools.class.getClassLoader().getResource(path.toString())).getImage();
        return image;
    }

    /**
     * Get an image from the specified file path.
     * 
     * @param path Path to the image file, relative to the project
     *             resources.
     * @return The BufferedImage loaded from the given file path.
     * 
     * @see BufferedImage
     */
    public static BufferedImage getBufferedImage(Path path, int type) {

        BufferedImage image = convertToBufferedImage(getImage(path), type);

        return image;
    }

    /**
     * 
     * @param image Input Image to be cast to BufferedImage
     * 
     * @return The casted BufferedImage
     * 
     * @see BufferedImage
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
