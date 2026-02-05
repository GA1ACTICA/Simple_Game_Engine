package AdvancedRendering.worldRendering;

import java.awt.Font;
import java.awt.Graphics;

public class AdvancedGraphics {

    public static void centerAlignedString(Graphics g, int x, int y, String str) {

        Font font = g.getFont();
        g.getFontMetrics(font).getHeight();
        g.drawString(str, x - (g.getFontMetrics(font).stringWidth(str) / 2),
                y - (g.getFontMetrics(font).getHeight() / 2));
    }

    // Future 3d rendering here I hope
}
