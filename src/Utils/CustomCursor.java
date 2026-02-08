package Utils;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import GameEngine.EngineModules.EnginePanel;
import GameEngine.EngineModules.Mouse;

public class CustomCursor {

    private int x, y, width, height;

    private BufferedImage cursorImage;

    private EnginePanel panel;
    private Mouse mouse;

    public CustomCursor(EnginePanel panel, Mouse mouse) {
        this.panel = panel;
        this.mouse = mouse;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void hideCursor() {
        panel.setCursor(panel.getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));
    }

    public void showCursor() {
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void loadCursor(String imagePath) {
        cursorImage = FileTools.getBufferedImage(imagePath, BufferedImage.TYPE_INT_ARGB);

        // skips if a custom width or height is allready set
        // if (width == 0)
        width = cursorImage.getWidth();

        // if (height == 0)
        height = cursorImage.getHeight();

    }

    public void paint(Graphics g) {
        x = mouse.getPoint().x - width / 2;
        y = mouse.getPoint().y - height / 2;
        g.drawImage(cursorImage, x, y, width, height, null);
    }
}
