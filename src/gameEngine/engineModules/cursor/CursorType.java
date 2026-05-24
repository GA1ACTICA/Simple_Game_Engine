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
public record CursorType(String path, Point hotspot) {

    public static CursorType ALIAS = new CursorType("alias.png", new Point(0, 0));
    // TODO: add the rest of the cursor types
    public static CursorType WAIT = new CursorType("wait", null);
}
