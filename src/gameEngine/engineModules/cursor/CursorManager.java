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
import java.util.Objects;

import game.configs.gameState.GameState;

import java.util.List;

import gameEngine.engineModules.ClassFactory;
import gameEngine.engineModules.EngineContext;
import gameEngine.engineModules.EnginePanel;
import gameEngine.engineModules.Mouse;
import gameEngine.engineModules.cursor.AnimatedCursorData.Frame;
import gameEngine.interfaces.Updatable;
import gameEngine.interfaces.drawables.CursorDrawable;
import utils.FileTools;
import utils.GraphicsTools;

public class CursorManager implements CursorDrawable, Updatable {

    private static String defaultCursorPath = "assets/cursors/Adwaita 96x96/";

    private static AnimatedCursor rawCursorData = new AnimatedCursor(null);
    private static Frame[] frameDataArray = null;

    private static boolean show = true;

    private static boolean overriding = false;
    private static int width = 96;
    private static int height = 96;

    private static List<BufferedImage> cursorImageCache = new ArrayList<BufferedImage>();
    private static BufferedImage[] cursorImageArray;
    private static int cursorArrayIndex = 0;

    private static float currentCursorMillis;
    private static boolean animated = false;
    private static BufferedImage cursorImage = null;
    private static Point hotspot = null;

    private static GameState state;
    private Mouse mouse;

    public CursorManager(EngineContext context, EnginePanel panel, Mouse mouse, GameState state) {
        CursorManager.state = state;
        this.mouse = mouse;
        ClassFactory.create(this, context);

        // Hide system cursor
        panel.setCursor(
                Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1,
                        BufferedImage.TYPE_INT_ARGB),
                        new Point(0, 0), "Transparent cursor"));

        setCursor(CursorType.WAIT);

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
    public static boolean setCursor(CursorType cursorType) {
        Objects.requireNonNull(cursorType, "The CursorType must not be null");

        if (overriding)
            return false;

        Path currentCursorPath = Path.of(cursorType.path());
        File resource = currentCursorPath.toFile();

        if (resource.toString().contains(".png")) {
            // Loads in a static cursor image
            Path cursor = Path.of(defaultCursorPath.toString() + resource.toString());
            System.out.println(
                    "Path to image for static cursor: " + cursor);

            cursorImage = GraphicsTools.downscaleImage(FileTools.getBufferedImage(cursor, BufferedImage.TYPE_INT_ARGB),
                    width, height);
            hotspot = cursorType.hotspot();
            animated = false;

        } else {
            // Loads in a animated cursor collection with the information from meta.json

            // Populate frameDataArray from meta.json
            rawCursorData.importJSON(AnimatedCursorData.class,
                    "src/" + defaultCursorPath + resource.toString() + "/meta.json");

            frameDataArray = rawCursorData.data().getFrames().toArray(new Frame[0]);

            if (frameDataArray != null) {
                for (Frame frame : frameDataArray) {
                    Path imagePath = Path.of(defaultCursorPath
                            + resource.getName() + "/"
                            + frame.getImagePath());

                    System.out.println("Image path: " + imagePath.toString());
                    System.out.println("Image hotspot: [" + frame.getHotspot()[0] + "," + frame.getHotspot()[1] + "]");
                    System.out.println("Delay duration: " + frame.getDurationMs() + '\n');

                    cursorImageCache.add(
                            GraphicsTools.downscaleImage(
                                    FileTools.getBufferedImage(imagePath, BufferedImage.TYPE_INT_ARGB),
                                    width, height));
                }

                // Transform to a array for easier data handling.
                cursorImageArray = cursorImageCache.toArray(new BufferedImage[0]);
                cursorImageCache.clear();

                animated = true;
                updateCursor();
            }
        }

        return true;
    }

    /**
     * Locks the current cursor preventing it from being changed
     * via {@link #setCursor(CursorManager.CursorType) setCursor()}.
     * <p>
     * While the cursor is locked, calls to {@code setCursor(...)} will have
     * no effect and return {@code false}.
     */
    public static void lockCursor() {
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
        if (!show || !animated)
            return;

        // Reset or set the iterator if it was missing
        if (cursorArrayIndex >= cursorImageArray.length - 1)
            cursorArrayIndex = 0;

        timer += deltaTime;

        if (timer >= currentCursorMillis) {
            timer -= currentCursorMillis;

            updateCursor();
            cursorArrayIndex++;
        }

    }

    /**
     * Updates the information about the current cursor. (Hotspot, Image and time
     * delay)
     */
    private static void updateCursor() {

        if (state.data().debugVerbose)
            System.out.println("Updating cursor index: " + cursorArrayIndex);

        cursorImage = cursorImageArray[cursorArrayIndex];
        currentCursorMillis = frameDataArray[cursorArrayIndex].getDurationMs() * 0.001f;
        hotspot = new Point(frameDataArray[cursorArrayIndex].getHotspotX(),
                frameDataArray[cursorArrayIndex].getHotspotY());
    }

}
