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
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

import GameEngine.Interfaces.Drawables.CursorDrawable;
import GameEngine.Records.CursorInformation;
import Utils.FileTools;

public class CursorManager implements CursorDrawable {
    public enum CursorType {
        ALIAS,
        ALL_RESIZE,
        ALL_SCROLL,
        CELL,
        COL_RESIZE,
        CONTEXT_MENU,
        COPY,
        CROSSHAIR,
        DEFAULT,
        E_RESIZE,
        EW_RESIZE,
        GRAB,
        GRABBING,
        HELP,
        MOVE,
        NE_RESIZE,
        NESW_RESIZE,
        NO_DROP,
        NOT_ALLOWED,
        N_RESIZE,
        NS_RESIZE,
        NW_RESIZE,
        NWSE_RESIZE,
        POINTER,
        ROW_RESIZE,
        SE_RESIZE,
        S_RESIZE,
        SW_RESIZE,
        TEXT,
        VERTICAL_TEXT,
        W_RESIZE,
        X_CURSOR,
        ZOOM_IN,
        ZOOM_OUT,

        // Animated
        PROGRESS,
        WAIT
    }

    private static Map<CursorType, CursorInformation> cursors = new EnumMap<>(CursorType.class) {
        {
            put(CursorType.ALIAS, new CursorInformation(new Point(72, 20), null)); // alias
            put(CursorType.ALL_RESIZE, new CursorInformation(new Point(48, 44), null)); // all-resize
            put(CursorType.ALL_SCROLL, new CursorInformation(new Point(44, 44), null)); // all-scroll
            put(CursorType.CELL, new CursorInformation(new Point(44, 44), null)); // cell
            put(CursorType.COL_RESIZE, new CursorInformation(new Point(48, 48), null)); // col-resize
            put(CursorType.CONTEXT_MENU, new CursorInformation(new Point(12, 4), null)); // context-menu
            put(CursorType.COPY, new CursorInformation(new Point(12, 4), null)); // copy
            put(CursorType.CROSSHAIR, new CursorInformation(new Point(44, 44), null)); // crosshair
            put(CursorType.DEFAULT, new CursorInformation(new Point(12, 4), null)); // default
            put(CursorType.E_RESIZE, new CursorInformation(new Point(76, 52), null)); // e-resize
            put(CursorType.EW_RESIZE, new CursorInformation(new Point(48, 48), null)); // ew-resize
            put(CursorType.GRAB, new CursorInformation(new Point(44, 8), null)); // grab
            put(CursorType.GRABBING, new CursorInformation(new Point(36, 20), null)); // grabbing
            put(CursorType.HELP, new CursorInformation(new Point(48, 84), null)); // help
            put(CursorType.MOVE, new CursorInformation(new Point(12, 4), null)); // move
            put(CursorType.NE_RESIZE, new CursorInformation(new Point(60, 40), null)); // ne-resize
            put(CursorType.NESW_RESIZE, new CursorInformation(new Point(44, 44), null)); // nesw-resize
            put(CursorType.NO_DROP, new CursorInformation(new Point(12, 4), null)); // no-drop
            put(CursorType.NOT_ALLOWED, new CursorInformation(new Point(48, 48), null)); // not-allowed
            put(CursorType.N_RESIZE, new CursorInformation(new Point(52, 24), null)); // n-resize
            put(CursorType.NS_RESIZE, new CursorInformation(new Point(48, 52), null)); // ns-resize
            put(CursorType.NW_RESIZE, new CursorInformation(new Point(40, 40), null)); // nw-resize
            put(CursorType.NWSE_RESIZE, new CursorInformation(new Point(44, 44), null)); // nwse-resize
            put(CursorType.POINTER, new CursorInformation(new Point(28, 20), null)); // pointer
            put(CursorType.ROW_RESIZE, new CursorInformation(new Point(48, 52), null)); // row-resize
            put(CursorType.SE_RESIZE, new CursorInformation(new Point(60, 60), null)); // se-resize
            put(CursorType.S_RESIZE, new CursorInformation(new Point(52, 72), null)); // s-resize
            put(CursorType.SW_RESIZE, new CursorInformation(new Point(40, 60), null)); // sw-resize
            put(CursorType.TEXT, new CursorInformation(new Point(44, 48), null)); // text
            put(CursorType.VERTICAL_TEXT, new CursorInformation(new Point(48, 44), null)); // vertical-text
            put(CursorType.W_RESIZE, new CursorInformation(new Point(24, 52), null)); // w-resize
            put(CursorType.X_CURSOR, new CursorInformation(new Point(44, 48), null)); // X_cursor
            put(CursorType.ZOOM_IN, new CursorInformation(new Point(44, 40), null)); // zoom-in
            put(CursorType.ZOOM_OUT, new CursorInformation(new Point(44, 40), null)); // zoom-out

            // animated
            put(CursorType.PROGRESS, new CursorInformation(new Point(12, 4), null, true, 50)); // progress
            put(CursorType.WAIT, new CursorInformation(new Point(44, 44), null, true, 50));// wait

        }
    };

    private static CursorInformation currentCursor = cursors.get(CursorType.DEFAULT);

    private int x, y, width, height;

    // Dosen't need cursorPath since the image is stored instead.
    private static Point hotspot = currentCursor.hotspot();
    private static boolean animated = currentCursor.animated();
    private static int millis = currentCursor.millis();
    private static Image cursorImage = FileTools.getImage(currentCursor.cursorPath());

    private static boolean show = true;

    private Mouse mouse;
    private EngineContext context;

    public CursorManager(EngineContext context, EnginePanel panel, Mouse mouse) {
        this.mouse = mouse;
        this.context = context;
        ClassFactory.create(this, context);

        // Hide system cursor
        panel.setCursor(
                Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                        new Point(0, 0), "Transparent cursor"));
    }

    public static boolean isVisible() {
        return show;
    }

    public static void show() {
        show = true;
    }

    public static void hide() {
        show = false;
    }

    public static void setCursor(CursorManager.CursorType cursorType) {
        currentCursor = cursors.get(cursorType);
    }

    public static void updateCursor() {
        hotspot = currentCursor.hotspot();
        animated = currentCursor.animated();
        millis = currentCursor.millis();
        cursorImage = FileTools.getImage(currentCursor.cursorPath());
    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        g.drawImage(cursorImage, mouse.getPoint().x, mouse.getPoint().y, width, height, null);
    }

}
