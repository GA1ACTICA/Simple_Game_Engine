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

package GameEngine.EngineModules;

import java.awt.Point;

import GameEngine.Interfaces.Clickable;
import GameEngine.Interfaces.Hoverable;

public class MouseManager {

    private static Hoverable lastHovered = null;
    private static Hoverable topMost = null;
    private static Clickable currentTopMost = null;

    public static void handlePriority(EngineContext context, Point mousePoint) {

        // Find the topmost hoverable under the mouse
        for (Hoverable hoverable : context.getHoverables()) {
            if (!hoverable.isVisible())
                continue;

            if (hoverable.contains(mousePoint.x, mousePoint.y)) {
                topMost = hoverable;
                return;
            } else {
                topMost = null;
            }
        }

    }

    public static void handleClick(EngineContext context, Point mousePoint, boolean mouseState) {

        // mouse DOWN
        if (mouseState) {

            if (currentTopMost == null && topMost instanceof Clickable clickable) {
                currentTopMost = clickable;
                clickable.onPressed();
            }

            // mouse UP
        } else {

            if (currentTopMost != null) {

                if (currentTopMost == topMost) {
                    currentTopMost.executeOnClick();
                }

                currentTopMost.onReleased();
                currentTopMost = null;
            }
        }
    }

    public static void handleHover(EngineContext context, Point mousePoint) {

        // If hover target changed
        if (lastHovered != topMost) {

            // Fire exit on previous
            if (lastHovered != null)
                lastHovered.setHovered(false);

            // Fire enter on new
            if (topMost != null)
                topMost.setHovered(true);

            lastHovered = topMost;
        }
    }
}
