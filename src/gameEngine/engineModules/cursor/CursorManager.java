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
package gameEngine.engineModules.cursor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.List;

import gameEngine.engineModules.ClassFactory;
import gameEngine.engineModules.EngineContext;
import gameEngine.engineModules.EnginePanel;
import gameEngine.engineModules.Mouse;
import gameEngine.engineModules.cursor.AnimatedCursorData.Frame;
import gameEngine.interfaces.Updatable;
import gameEngine.interfaces.drawables.CursorDrawable;
import utils.FileTools;

public class CursorManager implements CursorDrawable, Updatable {
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

    private static String defaultCursorPath = "assets/cursors/Adwaita 96x96/";

    private static Map<CursorType, String> cursors = new EnumMap<>(CursorType.class) {
        {
            put(CursorType.ALIAS, "alias.png");
            put(CursorType.ALL_RESIZE, "all-resize.png");
            put(CursorType.ALL_SCROLL, "all-scroll.png");
            put(CursorType.CELL, "cell.png");
            put(CursorType.COL_RESIZE, "col-resize.png");
            put(CursorType.CONTEXT_MENU, "context-menu.png");
            put(CursorType.COPY, "copy.png");
            put(CursorType.CROSSHAIR, "crosshair.png");
            put(CursorType.DEFAULT, "default.png");
            put(CursorType.E_RESIZE, "e-resize.png");
            put(CursorType.EW_RESIZE, "ew-resize.png");
            put(CursorType.GRAB, "grab.png");
            put(CursorType.GRABBING, "grabbing.png");
            put(CursorType.HELP, "help.png");
            put(CursorType.MOVE, "move.png");
            put(CursorType.NE_RESIZE, "ne-resize.png");
            put(CursorType.NESW_RESIZE, "nesw-resize.png");
            put(CursorType.NO_DROP, "no-drop.png");
            put(CursorType.NOT_ALLOWED, "not-allowed.png");
            put(CursorType.N_RESIZE, "n-resize.png");
            put(CursorType.NS_RESIZE, "ns-resize.png");
            put(CursorType.NW_RESIZE, "nw-resize.png");
            put(CursorType.NWSE_RESIZE, "nwse-resize.png");
            put(CursorType.POINTER, "pointer.png");
            put(CursorType.ROW_RESIZE, "row-resize.png");
            put(CursorType.SE_RESIZE, "se-resize.png");
            put(CursorType.S_RESIZE, "s-resize.png");
            put(CursorType.SW_RESIZE, "sw-resize.png");
            put(CursorType.TEXT, "text.png");
            put(CursorType.VERTICAL_TEXT, "vertical-text.png");
            put(CursorType.W_RESIZE, "w-resize.png");
            put(CursorType.X_CURSOR, "x-cursor.png");
            put(CursorType.ZOOM_IN, "zoom-in.png");
            put(CursorType.ZOOM_OUT, "zoom-out.png");

            // animated
            put(CursorType.PROGRESS, "progress");
            put(CursorType.WAIT, "wait");

        }
    };

    private static AnimatedCursor rawCursorData = new AnimatedCursor(null);
    private static Frame[] frameDataArray = null;

    private static boolean show = true;

    private static boolean overriding = false;
    private int width = 48;
    private int height = 48;

    private static List<Image> cursorImageCache = new ArrayList<Image>();
    private static Image[] cursorImageArray;
    private static int cursorArrayIndex = 0;

    private static float currentCursorMillis = 0;
    private static boolean animated = false;
    private static Image cursorImage = null;
    private static Point hotspot = null;

    private Mouse mouse;

    public CursorManager(EngineContext context, EnginePanel panel, Mouse mouse) {
        this.mouse = mouse;
        ClassFactory.create(this, context);

        // Hide system cursor
        panel.setCursor(
                Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1,
                        BufferedImage.TYPE_INT_ARGB),
                        new Point(0, 0), "Transparent cursor"));

        setCursor(CursorType.DEFAULT);

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

        Path currentCursorPath = Path.of(cursors.get(cursorType));
        File resource = currentCursorPath.toFile();

        if (resource.toString().contains(".png")) {
            // Loads in a static cursor image
            Path cursor = Path.of(defaultCursorPath.toString() + resource.toString());
            System.out.println(
                    "Path to image for static cursor: " + cursor);

            cursorImageCache.add(FileTools.getImage(cursor));
            cursorImageArray = cursorImageCache.toArray(new Image[0]);
            cursorImageCache.clear();
            animated = false;

            updateCursor();
        } else {
            // Loads in a animated cursor collection with the information from meta.json

            // Populate frameDataArray from meta.json
            rawCursorData.importJSON(AnimatedCursorData.class,
                    "src/" + defaultCursorPath + resource.toString() + "/meta.json");

            frameDataArray = (Frame[]) rawCursorData.data().getFrames().toArray(new Frame[0]);

            if (frameDataArray != null) {
                for (Frame frame : frameDataArray) {
                    Path imagePath = Path.of(defaultCursorPath
                            + resource.getName() + "/"
                            + frame.getImagePath());

                    System.out.println("Image path: " + imagePath.toString());
                    System.out.println("Image hotspot: [" + frame.getHotspot()[0] + "," + frame.getHotspot()[1] + "]");
                    System.out.println("Delay duration: " + frame.getDurationMs() + '\n');

                    cursorImageCache.add(
                            FileTools.getImage(imagePath));
                }

                // Transform to a array for easier data handling.
                cursorImageArray = cursorImageCache.toArray(new Image[0]);
                cursorImageCache.clear();

                System.out.println(Arrays.toString(cursorImageArray));
                animated = true;
                updateCursor();
            }
        }

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

    private float timer;

    @Override
    public void update(float deltaTime) {
        if (!show)
            return;

        // Reset or set the iterator if it was missing
        if (cursorArrayIndex >= cursorImageArray.length - 1)
            cursorArrayIndex = 0;

        if (animated != false) {
            timer += deltaTime;

            System.out.println("Timer: " + timer);

            if (timer >= currentCursorMillis) {
                timer -= currentCursorMillis;

                cursorArrayIndex++;
                updateCursor();
            }
        }
    }

    /**
     * Updates the information about the current cursor. (Hotspot, Image and time
     * delay)
     */
    private static void updateCursor() {
        System.out.println("Updating cursor index: " + cursorArrayIndex);
        System.out.println("Updating cursor image: " + cursorImageArray[cursorArrayIndex] + '\n');

        cursorImage = cursorImageArray[cursorArrayIndex];

        if (!animated)
            return;

        currentCursorMillis = frameDataArray[cursorArrayIndex].getDurationMs() * 0.001f;
        hotspot = new Point(frameDataArray[cursorArrayIndex].getHotspotX(),
                frameDataArray[cursorArrayIndex].getHotspotY());

        System.out.println("Updating cursor millis: " + frameDataArray[cursorArrayIndex].getDurationMs());
        System.out.println("Updating cursor hotspot:  [" + frameDataArray[cursorArrayIndex].getHotspot()[0] + "," + frameDataArray[cursorArrayIndex].getHotspot()[1] + "]");

    }

}
