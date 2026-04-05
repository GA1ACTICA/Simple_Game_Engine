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

import java.awt.Point;

import Game.Configs.GameState.GameState;
import GameEngine.Interfaces.Clickable;
import GameEngine.Interfaces.Hoverable;

public class MouseManager {

    private static GameState state;

    /**
     * @param getState
     */
    public static void setObjects(GameState getState) {
        state = getState;
    }

    private static Clickable topMost = null;
    private static Clickable currentTopMost = null; // only gets set when left mouse button is pressed down

    private static Hoverable lastHovered = null;

    /**
     * @param context
     * @param mousePoint
     */
    public static void handlePriority(EngineContext context, Point mousePoint) {

        // Find the topmost clickable under the mouse
        for (Clickable clickable : context.getClickables()) {
            if (!clickable.isVisible() || !clickable.isEnabled())
                continue;

            if (clickable.contains(mousePoint.x, mousePoint.y)) {
                topMost = clickable;
                return;
            } else {
                topMost = null;
            }
        }

    }

    /**
     * @param context
     * @param mousePoint
     * @param mouseState
     */
    public static void handleClick(EngineContext context, Point mousePoint, boolean mouseState) {

        if (mouseState) {
            currentTopMost = topMost;

            for (Clickable clickable : context.getClickables()) {
                if (!clickable.isVisible() || !clickable.isEnabled())
                    continue;

                // Prints all buttons and zIndex
                if (state.data().debugVerbose)
                    System.out.println("%s %s".formatted(clickable, clickable.getZIndex()));

                if ((clickable == currentTopMost) && clickable.contains(mousePoint.x, mousePoint.y)) {
                    clickable.onPressed();

                    // Print only pressed button and zIndex
                    if (state.data().debug && !state.data().debugVerbose)
                        System.out.println("%s %s".formatted(clickable, clickable.getZIndex()));

                    return;
                }

            }

        } else if (currentTopMost != null) {

            if ((topMost == currentTopMost))
                currentTopMost.executeOnClick();

            currentTopMost.onReleased();

        }

    }

    /**
     * @param context
     * @param mousePoint
     */
    public static void handleHover(EngineContext context, Point mousePoint) {
        Hoverable currentHovered = null;

        if (topMost != null &&
                topMost.isVisible() &&
                topMost.isEnabled() &&
                topMost instanceof Hoverable hoverable &&
                topMost.contains(mousePoint.x, mousePoint.y)) {
            currentHovered = hoverable;
        }

        // If hover target changed
        if (lastHovered != currentHovered) {

            // Fire exit on previous
            if (lastHovered != null)
                lastHovered.setHovered(false);

            // Fire enter on new
            if (currentHovered != null)
                currentHovered.setHovered(true);

            lastHovered = currentHovered;
        }
    }
}
