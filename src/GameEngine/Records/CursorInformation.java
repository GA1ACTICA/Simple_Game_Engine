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

package GameEngine.Records;

import java.awt.Point;
import java.nio.file.Path;
import java.util.Objects;

/**
 * The {@code CursorInformation} record encapsulates the information required to
 * create a cursor.
 * 
 * The record contains:
 * <ul>
 * <li>{@code hotspot} - the reference point from which clicks originate</li>
 * <li>{@code cursorPath} - the path to the cursor resource to be loaded</li>
 * <li>{@code animated} - boolean state deciding if the cursor is animated (
 * should only be {@code true} if the provided path is a directory )</li>
 * 
 * <li>{@code millis} - the amount of millis between each frame</li>
 * </ul>
 *
 * @param hotspot    the reference point for cursor clicks
 * @param cursorPath the path to the cursor to be imported
 * @param animated   boolean state deciding if the cursor is animated
 * @param millis     the amount of millis between each frame
 * 
 * @throws NullPointerException if {@code hotspot} or {@code cursorPath} is
 *                              {@code null}
 */
public record CursorInformation(Point hotspot, Path cursorPath, boolean animated, Integer millis) {
    public CursorInformation {
        Objects.requireNonNull(hotspot, "hotspot must not be null");
        Objects.requireNonNull(cursorPath, "cursorPath must not be null");
    }

    /**
     * Creates a non-animated cursor configuration with the given hotspot and cursor
     * path.
     *
     * <p>
     * This is a convenience factory method that reduces the number of arguments
     * required for creating a standard (non-animated) cursor
     * 
     * @param hotspot    the reference point for cursor clicks
     * @param cursorPath the path to the cursor to be imported
     * @return a {@code CursorInformation} instance representing a non-animated
     *         cursor
     * 
     * @throws NullPointerException if {@code hotspot} or {@code cursorPath} is
     *                              {@code null}
     */
    public static CursorInformation defaultCursor(Point hotspot, Path cursorPath) {
        hotspot = Objects.requireNonNull(hotspot, "hotspot must not be null");
        cursorPath = Objects.requireNonNull(cursorPath, "cursorPath must not be null");

        return new CursorInformation(hotspot, cursorPath, false, null);
    }
}
