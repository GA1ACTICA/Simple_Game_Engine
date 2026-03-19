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

    public static void handleClick(EngineContext context, Point mousePoint) {

        for (Clickable clickable : context.getClickables()) {
            if (!clickable.getVisible() || clickable.getDisabled())
                continue;

            // Prints all buttons and zIndex
            if (state.data().debugVerbose)
                System.out.println("%s %s".formatted(clickable, clickable.getZIndex()));

            if ((clickable == topMost)) {
                clickable.executeOnClick();

                // Print only pressed button and zIndex
                if (state.data().debug)
                    System.out.println("%s %s".formatted(clickable, clickable.getZIndex()));

                return;
            }

        }

    }

    public static void handlePriority(EngineContext context, Point mousePoint) {

        // Find the topmost clickable under the mouse
        for (Clickable clickable : context.getClickables()) {
            if (!clickable.getVisible() || clickable.getDisabled())
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
            if (!clickable.getVisible() || clickable.getDisabled())
                continue;

            if (!(clickable instanceof Hoverable hoverable))
                continue;

            // Check if current clickable is the top clickable on the mouse
            hoverable.setHovered((clickable == topMost));

        }
    }
}
