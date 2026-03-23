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

    public static void setObjects(GameState getState) {
        state = getState;
    }

    private static Clickable topMost = null;
    private static Clickable currentTopMost = null; // only gets set when left mouse button is pressed down

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
                    clickable.pressed();

                    // Print only pressed button and zIndex
                    if (state.data().debug)
                        System.out.println("%s %s".formatted(clickable, clickable.getZIndex()));

                    return;
                }

            }

        } else if (currentTopMost != null) {

            if ((topMost == currentTopMost))
                currentTopMost.executeOnClick();

            currentTopMost.released();

        }

    }

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

    public static void handleHover(EngineContext context, Point mousePoint) {

        // Update hover states
        for (Clickable clickable : context.getClickables()) {
            if (!clickable.isVisible() || !clickable.isEnabled())
                continue;

            if (!(clickable instanceof Hoverable hoverable))
                continue;

            // Check if current clickable is the top clickable on the mouse
            hoverable.setHovered((clickable == topMost));

        }
    }
}
