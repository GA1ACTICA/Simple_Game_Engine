package GameEngine.EngineModules;

import java.awt.Point;

import Game.Configs.GameState.GameState;
import GameEngine.Interfaces.Clickable;

public class ClickManager {

    private static GameState state;
    private static Mouse mouse;

    public static void setObjects(GameState getState, Mouse getMouse) {
        state = getState;
        mouse = getMouse;
    }

    public static void handleClick(EngineContext context, Point mousePoint) {

        for (ListEntry entry : context.getClickables()) {
            Clickable clickable = entry.clickable;

            // Prints all buttons and zIndex
            if (state.data().debugVerbose)
                System.out.println("%s %s".formatted(clickable, clickable.getZIndex()));

            // Print only pressed button and zIndex
            if (state.data().debug && clickable.contains(mousePoint.x, mousePoint.y))
                System.out.println("%s %s".formatted(clickable, clickable.getZIndex()));

            if (clickable.contains(mousePoint.x, mousePoint.y) && !clickable.getDisabled()) {
                clickable.executeOnClick();
                return;
            }
        }

    }

    public static void handleInside(EngineContext context, Point mousePoint) {
        if (!mouse.moved)
            return;

        for (ListEntry entry : context.getClickables()) {
            Clickable clickable = entry.clickable;

            if (clickable.getVisible()) {

            }
        }

    }
}
