package GameEngine;

import java.awt.geom.AffineTransform;
import javax.swing.*;

import AdvancedRendering.Menu.GameButtonScaling;
import AdvancedRendering.Menu.MenuContet.GameButton;
import AdvancedRendering.Menu.MenuContet.GameMenu;

import java.awt.*;

public class GamePanel extends JPanel {

    private final GameState gs;
    private GameButton button;

    // LOGICAL space
    public final int logicalWidth = 1000;
    public final int logicalHeight = 1000;

    public GamePanel(GameState gs) {
        this.gs = gs;

        setDoubleBuffered(true);
    }

    public void setButton(GameButton button) {
        this.button = button;
    }

    // rendering engine
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Save transform
        AffineTransform old = g2d.getTransform();

        double scaleX = getWidth() / (double) logicalWidth;
        double scaleY = getHeight() / (double) logicalHeight;
        double scale = Math.min(scaleX, scaleY);

        // Draw buttons in LOGICAL space
        for (GameButtonScaling button : button.getButtons()) {
            button.updateBounds(scaleX, scaleY, getWidth(), getHeight());
        }

        // Center + scale
        g2d.translate(
                (getWidth() - logicalWidth * scale) / 2,
                (getHeight() - logicalHeight * scale) / 2);
        g2d.scale(scale, scale);

        // Draw background in LOGICAL space
        g2d.setColor(gs.backgroundColor);
        g2d.fillRect(0, 0, logicalWidth, logicalHeight);

        // Draw game objects
        for (Drawable d : GameUpdate.drawables) {
            d.draw(g2d);
        }

        // Restore transform
        g2d.setTransform(old);
    }
}