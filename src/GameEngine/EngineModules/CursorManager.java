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
import java.nio.file.Path;
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

    private static String defaultCursorPath = "GameEngine/Assets/Cursors/Adwaita 96x96/";

    private static Map<CursorType, CursorInformation> cursors = new EnumMap<>(CursorType.class) {
        {
            put(CursorType.ALIAS,
                    new CursorInformation(new Point(72, 20),
                            Path.of(defaultCursorPath + "alias.png")));
            put(CursorType.ALL_RESIZE,
                    new CursorInformation(new Point(48, 44),
                            Path.of(defaultCursorPath + "all-resize.png")));
            put(CursorType.ALL_SCROLL,
                    new CursorInformation(new Point(44, 44),
                            Path.of(defaultCursorPath + "all-scroll.png")));
            put(CursorType.CELL,
                    new CursorInformation(new Point(44, 44),
                            Path.of(defaultCursorPath + "cell.png")));
            put(CursorType.COL_RESIZE,
                    new CursorInformation(new Point(48, 48),
                            Path.of(defaultCursorPath + "col-resize.png")));
            put(CursorType.CONTEXT_MENU,
                    new CursorInformation(new Point(12, 4),
                            Path.of(defaultCursorPath + "context-menu.png")));
            put(CursorType.COPY,
                    new CursorInformation(new Point(12, 4),
                            Path.of(defaultCursorPath + "copy.png")));
            put(CursorType.CROSSHAIR,
                    new CursorInformation(new Point(44, 44),
                            Path.of(defaultCursorPath + "crosshair.png")));
            put(CursorType.DEFAULT,
                    new CursorInformation(new Point(12, 4),
                            Path.of(defaultCursorPath + "default.png")));
            put(CursorType.E_RESIZE,
                    new CursorInformation(new Point(76, 52),
                            Path.of(defaultCursorPath + "e-resize.png")));
            put(CursorType.EW_RESIZE,
                    new CursorInformation(new Point(48, 48),
                            Path.of(defaultCursorPath + "ew-resize.png")));
            put(CursorType.GRAB,
                    new CursorInformation(new Point(44, 8),
                            Path.of(defaultCursorPath + "grab.png")));
            put(CursorType.GRABBING,
                    new CursorInformation(new Point(36, 20),
                            Path.of(defaultCursorPath + "grabbing.png")));
            put(CursorType.HELP,
                    new CursorInformation(new Point(48, 84),
                            Path.of(defaultCursorPath + "help.png")));
            put(CursorType.MOVE,
                    new CursorInformation(new Point(12, 4),
                            Path.of(defaultCursorPath + "move.png")));
            put(CursorType.NE_RESIZE,
                    new CursorInformation(new Point(60, 40),
                            Path.of(defaultCursorPath + "ne-resize.png")));
            put(CursorType.NESW_RESIZE,
                    new CursorInformation(new Point(44, 44),
                            Path.of(defaultCursorPath + "nesw-resize.png")));
            put(CursorType.NO_DROP,
                    new CursorInformation(new Point(12, 4),
                            Path.of(defaultCursorPath + "no-drop.png")));
            put(CursorType.NOT_ALLOWED,
                    new CursorInformation(new Point(48, 48),
                            Path.of(defaultCursorPath + "not-allowed.png")));
            put(CursorType.N_RESIZE,
                    new CursorInformation(new Point(52, 24),
                            Path.of(defaultCursorPath + "n-resize.png")));
            put(CursorType.NS_RESIZE,
                    new CursorInformation(new Point(48, 52),
                            Path.of(defaultCursorPath + "ns-resize.png")));
            put(CursorType.NW_RESIZE,
                    new CursorInformation(new Point(40, 40),
                            Path.of(defaultCursorPath + "nw-resize.png")));
            put(CursorType.NWSE_RESIZE,
                    new CursorInformation(new Point(44, 44),
                            Path.of(defaultCursorPath + "nwse-resize.png")));
            put(CursorType.POINTER,
                    new CursorInformation(new Point(28, 20),
                            Path.of(defaultCursorPath + "pointer.png")));
            put(CursorType.ROW_RESIZE,
                    new CursorInformation(new Point(48, 52),
                            Path.of(defaultCursorPath + "row-resize.png")));
            put(CursorType.SE_RESIZE,
                    new CursorInformation(new Point(60, 60),
                            Path.of(defaultCursorPath + "se-resize.png")));
            put(CursorType.S_RESIZE,
                    new CursorInformation(new Point(52, 72),
                            Path.of(defaultCursorPath + "s-resize.png")));
            put(CursorType.SW_RESIZE,
                    new CursorInformation(new Point(40, 60),
                            Path.of(defaultCursorPath + "sw-resize.png")));
            put(CursorType.TEXT,
                    new CursorInformation(new Point(44, 48),
                            Path.of(defaultCursorPath + "text.png")));
            put(CursorType.VERTICAL_TEXT,
                    new CursorInformation(new Point(48, 44),
                            Path.of(defaultCursorPath + "vertical-text.png")));
            put(CursorType.W_RESIZE,
                    new CursorInformation(new Point(24, 52),
                            Path.of(defaultCursorPath + "w-resize.png")));
            put(CursorType.X_CURSOR,
                    new CursorInformation(new Point(44, 48),
                            Path.of(defaultCursorPath + "X_curs.png")));
            put(CursorType.ZOOM_IN,
                    new CursorInformation(new Point(44, 40),
                            Path.of(defaultCursorPath + "zoom-in.png")));
            put(CursorType.ZOOM_OUT,
                    new CursorInformation(new Point(44, 40),
                            Path.of(defaultCursorPath + "zoom-out.png")));

            // animated
            // put(CursorType.PROGRESS, new CursorInformation(new Point(12, 4), null, true,
            // 50)); // progress
            // put(CursorType.WAIT, new CursorInformation(new Point(44, 44), null, true,
            // 50));// wait

        }
    };

    private static CursorInformation currentCursor = cursors.get(CursorType.DEFAULT);

    private int width = 24;
    private int height = 24;

    private static boolean overriding = false;

    // Does not need cursorPath since the image is stored instead.
    private static Point hotspot = currentCursor.hotspot();
    private static boolean animated = currentCursor.animated();
    private static Integer millis = currentCursor.millis();
    private static Image cursorImage = FileTools.getImage(currentCursor.cursorPath());

    private static boolean show = true;

    private Mouse mouse;

    public CursorManager(EngineContext context, EnginePanel panel, Mouse mouse) {
        this.mouse = mouse;
        ClassFactory.create(this, context);

        // Hide system cursor
        panel.setCursor(
                Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1,
                        BufferedImage.TYPE_INT_ARGB),
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

    /**
     * Set a cursor predefined in {@link CursorType} or added with
     * {@link #TEMPORARY_METHOD_NAME()}.
     * 
     * @param cursorType
     * @return {@code true} if
     */
    public static boolean setCursor(CursorManager.CursorType cursorType) {
        if (overriding)
            return false;

        currentCursor = cursors.get(cursorType);
        updateCursor();
        return true;
    }

    /**
     * Sets the current cursor and locks it, preventing it from being changed
     * via {@link #setCursor(CursorManager.CursorType) setCursor()}.
     * <p>
     * While the cursor is locked, calls to {@code setCursor(...)} will have
     * no effect and return {@code false}.
     *
     * @param cursorType the cursor type to set and lock
     */
    public static void lockCursor(CursorManager.CursorType cursorType) {
        currentCursor = cursors.get(cursorType);
        updateCursor();

        overriding = true;
    }

    /**
     * Unlocks the cursor, allowing it to be changed via
     * {@link #setCursor(CursorManager.CursorType) setCursor()}.
     * <p>
     * After calling this method, {@code setCursor(...)} will resume normal
     * behavior and return {@code true}.
     */
    public static void unlockCursor() {
        overriding = false;
    }

    @Override
    public void draw(Graphics g) {
        if (!show || !mouse.onScreen())
            return;

        double scaleX = (double) width / cursorImage.getWidth(null);
        double scaleY = (double) height / cursorImage.getHeight(null);

        int drawX = (int) (mouse.getPoint().x - hotspot.x * scaleX);
        int drawY = (int) (mouse.getPoint().y - hotspot.y * scaleY);

        g.drawImage(cursorImage, drawX, drawY, width, height, null);
    }

    private static void updateCursor() {
        hotspot = currentCursor.hotspot();
        animated = currentCursor.animated();
        millis = currentCursor.millis();
        cursorImage = FileTools.getImage(currentCursor.cursorPath());
    }

}
