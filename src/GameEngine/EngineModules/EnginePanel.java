package GameEngine.EngineModules;

import java.awt.geom.AffineTransform;

import javax.swing.*;

import Game.Configs.GameState.GameState;
import GameEngine.Interfaces.Drawable;

import java.awt.*;

public class EnginePanel extends JPanel {

    // logical space dimension
    public final int logicalWidth = 1000;
    public final int logicalHeight = 1000;

    private GameState state;
    private EngineContext context;

    private boolean exceptionReported;

    public EnginePanel(GameState state, EngineContext context) {
        this.state = state;
        this.context = context;

        setDoubleBuffered(true);
    }

    // rendering engine
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {

            Graphics2D g2d = (Graphics2D) g.create();

            AffineTransform old = g2d.getTransform();

            double scaleX = getWidth() / (double) logicalWidth;
            double scaleY = getHeight() / (double) logicalHeight;
            double scale = Math.min(scaleX, scaleY);

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            // Center + scale
            g2d.translate(
                    (getWidth() - logicalWidth * scale) / 2,
                    (getHeight() - logicalHeight * scale) / 2);
            g2d.scale(scale, scale);

            g2d.setColor(state.data().backgroundColor);
            g2d.fillRect(0, 0, logicalWidth, logicalHeight);

            // Draw game objects in world space
            for (Drawable d : context.getWorldDrawables()) {
                d.draw(g2d);
            }

            // Restore transform
            g2d.setTransform(old);

            // Draw game objects in UI space
            for (Drawable d : context.getUiDrawables()) {
                d.draw(g2d);
            }

            g2d.dispose();

        } catch (Throwable t) {

            if (!exceptionReported) {
                exceptionReported = true;
                t.printStackTrace();
            }
            // Skip rendering this frame
        }

    }
}