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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import GameEngine.Interfaces.Drawables.CursorDrawable;
import GameEngine.Records.CursorInformation;
import Utils.FileTools;

public class CustomCursor implements CursorDrawable {

    private int zIndex = 0;

    private int x, y, width, height;

    private BufferedImage cursorImage;

    private boolean show;

    private Mouse mouse;
    private EngineContext context;
    // TODO: animated cursor?

    // LookupTable for cursors.

    public static final int ALIAS = 0;
    public static final int ALL_RESIZE = 1;
    public static final int ALL_SCROLL = 2;
    public static final int CELL = 3;
    public static final int COL_RESIZE = 4;
    public static final int CONTEXT_MENU = 5;
    public static final int COPY = 6;
    public static final int CROSSHAIR = 7;
    public static final int DEFAULT = 8;
    public static final int E_RESIZE = 9;
    public static final int EW_RESIZE = 10;
    public static final int GRAB = 11;
    public static final int GRABBING = 12;
    public static final int HELP = 13;
    public static final int MOVE = 14;
    public static final int NE_RESIZE = 15;
    public static final int NESW_RESIZE = 16;
    public static final int NO_DROP = 17;
    public static final int NOT_ALLOWED = 18;
    public static final int N_RESIZE = 19;
    public static final int NS_RESIZE = 20;
    public static final int NW_RESIZE = 21;
    public static final int NWSE_RE = 22;
    public static final int POINTER = 23;
    public static final int ROW_RESIZE = 24;
    public static final int SE_RESIZE = 25;
    public static final int S_RESIZE = 26;
    public static final int SW_RESIZE = 27;
    public static final int TEXT = 28;
    public static final int VERTICAL_TEXT = 29;
    public static final int W_RESIZE = 30;
    public static final int X_CURSOR = 31;
    public static final int ZOOM_IN = 32;
    public static final int ZOOM_OUT = 33;

    // Animated
    public static final int PROGRESS = 34;
    public static final int WAIT = 35;

    // Hotspot
    private List<CursorInformation> cursorDefinitions = new ArrayList<>(List.of(
            CursorInformation.defaultCursor(new Point(72, 20), null), // alias
            CursorInformation.defaultCursor(new Point(48, 44), null), // all-resize
            CursorInformation.defaultCursor(new Point(44, 44), null), // all-scroll
            CursorInformation.defaultCursor(new Point(44, 44), null), // cell
            CursorInformation.defaultCursor(new Point(48, 48), null), // col-resize
            CursorInformation.defaultCursor(new Point(12, 4), null), // context-menu
            CursorInformation.defaultCursor(new Point(12, 4), null), // copy
            CursorInformation.defaultCursor(new Point(44, 44), null), // crosshair
            CursorInformation.defaultCursor(new Point(12, 4), null), // default
            CursorInformation.defaultCursor(new Point(76, 52), null), // e-resize
            CursorInformation.defaultCursor(new Point(48, 48), null), // ew-resize
            CursorInformation.defaultCursor(new Point(44, 8), null), // grab
            CursorInformation.defaultCursor(new Point(36, 20), null), // grabbing
            CursorInformation.defaultCursor(new Point(48, 84), null), // help
            CursorInformation.defaultCursor(new Point(12, 4), null), // move
            CursorInformation.defaultCursor(new Point(60, 40), null), // ne-resize
            CursorInformation.defaultCursor(new Point(44, 44), null), // nesw-resize
            CursorInformation.defaultCursor(new Point(12, 4), null), // no-drop
            CursorInformation.defaultCursor(new Point(48, 48), null), // not-allowed
            CursorInformation.defaultCursor(new Point(52, 24), null), // n-resize
            CursorInformation.defaultCursor(new Point(48, 52), null), // ns-resize
            CursorInformation.defaultCursor(new Point(40, 40), null), // nw-resize
            CursorInformation.defaultCursor(new Point(44, 44), null), // nwse-resize
            CursorInformation.defaultCursor(new Point(28, 20), null), // pointer
            CursorInformation.defaultCursor(new Point(48, 52), null), // row-resize
            CursorInformation.defaultCursor(new Point(60, 60), null), // se-resize
            CursorInformation.defaultCursor(new Point(52, 72), null), // s-resize
            CursorInformation.defaultCursor(new Point(40, 60), null), // sw-resize
            CursorInformation.defaultCursor(new Point(44, 48), null), // text
            CursorInformation.defaultCursor(new Point(48, 44), null), // vertical-text
            CursorInformation.defaultCursor(new Point(24, 52), null), // w-resize
            CursorInformation.defaultCursor(new Point(44, 48), null), // X_cursor
            CursorInformation.defaultCursor(new Point(44, 40), null), // zoom-in
            CursorInformation.defaultCursor(new Point(44, 40), null), // zoom-out

            // animated
            new CursorInformation(new Point(12, 4), null, true, 50), // progress
            new CursorInformation(new Point(44, 44), null, true, 50)// wait
    ));

    public CustomCursor(EngineContext context, EnginePanel panel, Mouse mouse) {
        this.mouse = mouse;
        this.context = context;
        ClassFactory.create(this, context);

        // Hide system cursor
        panel.setCursor(
                Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                        new Point(0, 0), "Transparent cursor"));
    }

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
