/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the GPL 3.0 License.
 * See LICENSE file in the project root for full license information.
 *
 * Copyright © 2026 Galactica
 */

package GameEngine.EngineModules;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import GameEngine.Interfaces.Drawables.CursorDrawable;
import Utils.FileTools;

public class CustomCursor implements CursorDrawable {

    private int zIndex = 0;

    private int x, y, width, height;

    private BufferedImage cursorImage;

    private boolean show;

    private Mouse mouse;
    private EngineContext context;

    public CustomCursor(EngineContext context, EnginePanel panel, Mouse mouse) {
        this.mouse = mouse;
        this.context = context;
        ClassFactory.create(this, context);

        // Hide system cursor
        panel.setCursor(
                Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                        new Point(0, 0), "Transparent cursor"));
    }

    // TODO: animated cursor?

    // LookupTable for cursors. (Static int)

    public static int ALL_SCROLL = 0;
    public static int BD_DOUBLE_ARROW = 1;
    public static int BOTTOM_LEFT_CORNER = 2;
    public static int BOTTOM_RIGHT_CORNER = 3;
    public static int BOTTOM_SIDE = 4;
    public static int BOTTOM_TEE = 5;
    public static int CIRCLE = 6;

    // Array with hot spot

    @Override
    public void setZIndex(int zIndex) {
        ClassFactory.updatePriority(this, context, zIndex);
        this.zIndex = zIndex;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    public static void setCursor(int cursor) {

    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        g.drawImage(cursorImage, mouse.getPoint().x, mouse.getPoint().y, width, height, null);
    }

}
