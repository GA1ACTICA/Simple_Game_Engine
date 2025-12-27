package AdvancedRendering.Menu;

import java.util.List;

import javax.swing.JButton;

import GameEngine.GamePanel;

public class GameButtonScaling {

    private int logicalX, logicalY, logicalWidth, logicalHeight;
    private float logicalFontSize;

    JButton button;
    GamePanel panel;

    public GameButtonScaling(int x, int y, int width, int height, int fontSize, JButton button, GamePanel panel) {
        this.logicalX = x;
        this.logicalY = y;
        this.logicalWidth = width;
        this.logicalHeight = height;
        this.logicalFontSize = fontSize;

        this.button = button;
        this.panel = panel;
    }

    // Update the button's bounds based on scaling
    public void updateBounds(double scaleX, double scaleY, int panelWidth, int panelHeight) {
        double scale = Math.min(scaleX, scaleY);

        // Convert from logical coordinates to pixel coordinates
        int x = (int) (logicalX * scale + (panelWidth - panel.logicalWidth * scale) / 2);
        int y = (int) (logicalY * scale + (panelHeight - panel.logicalHeight * scale) / 2);
        int width = (int) (logicalWidth * scale);
        int height = (int) (logicalHeight * scale);

        button.setBounds(x, y, width, height);

        button.setFont(
                button.getFont().deriveFont(logicalFontSize * (float) scale));
    }
}
