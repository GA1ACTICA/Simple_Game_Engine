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

import java.awt.Point;
import java.util.Objects;

/**
 * Creates a new record of CursorType
 * 
 * This is the general constructor which can be used both for animated and non
 * animated cursors.
 * 
 * @param hotspot  The hotspot from where click originate
 * 
 * @param path     The path to the image or directory of the cursor
 * 
 * @param animated Toggle for animated cursor
 */
public record CursorType(boolean animated, String path, Point hotspot) {

    /**
     * Creates a new record of CursorType where the default values for animated is
     * false and for hotspot null.
     * 
     * This constructor is only intended to be used when adding a new <b>non
     * animated</b> cursor.
     * 
     * @param path Path to the cursor image
     */
    public CursorType(String path) {
        Objects.requireNonNull(path, "Path must not be null");
        this(false, path, null);
    }

}
