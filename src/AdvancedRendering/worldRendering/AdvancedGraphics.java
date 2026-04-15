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

package AdvancedRendering.worldRendering;

import java.awt.Font;
import java.awt.Graphics;

public class AdvancedGraphics {

    // I do not even know if I'm gonna keep this tbh

    /**
     * @param g
     * @param x
     * @param y
     * @param str
     */
    public static void centerAlignedString(Graphics g, int x, int y, String str) {

        Font font = g.getFont();
        g.getFontMetrics(font).getHeight();
        g.drawString(str, x - (g.getFontMetrics(font).stringWidth(str) / 2),
                y - (g.getFontMetrics(font).getHeight() / 2));
    }

    // Future 3d rendering here I hope
}
