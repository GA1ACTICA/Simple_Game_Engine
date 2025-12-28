package GameEngine;

import java.awt.geom.AffineTransform;
import javax.swing.*;

import GameEngine.Interfaces.Drawable;

import java.awt.*;

public class GamePanel extends JPanel {

    // logical space dimension

    public final int logicalWidth = 1000;
    public final int logicalHeight = 1000;

    private final GameState state;

    public GamePanel(GameState state) {
        this.state = state;

        setDoubleBuffered(true);
    }

    // rendering engine
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        AffineTransform old = g2d.getTransform();

        double scaleX = getWidth() / (double) logicalWidth;
        double scaleY = getHeight() / (double) logicalHeight;
        double scale = Math.min(scaleX, scaleY);

        // Center + scale
        g2d.translate(
                (getWidth() - logicalWidth * scale) / 2,
                (getHeight() - logicalHeight * scale) / 2);
        g2d.scale(scale, scale);

        g2d.setColor(state.backgroundColor);
        g2d.fillRect(0, 0, logicalWidth, logicalHeight);

        // Draw game objects in world space
        for (Drawable d : GameUpdate.worldDrawables) {
            d.draw(g2d);
        }

        // Restore transform
        g2d.setTransform(old);

        // Draw game objects in UI space
        for (Drawable d : GameUpdate.uiDrawables) {
            d.draw(g2d);
        }
    }
}