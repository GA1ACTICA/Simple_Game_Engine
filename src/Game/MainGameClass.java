package Game;

import java.awt.*;

import AdvancedRendering.uiRendering.Menu.GameMenu;
import AdvancedRendering.uiRendering.Misc.FPSCounter;
import AdvancedRendering.worldRendering.AdvancedGraphics;
import GameEngine.*;
import GameEngine.Interfaces.Drawable;
import GameEngine.Interfaces.Updatable;

public class MainGameClass implements Drawable, Updatable {

    @SuppressWarnings("unused")
    private final GameState state;
    private final FPSCounter fps;
    private final AdvancedGraphics advanced;

    public MainGameClass(GameState state, GameMenu menu, FPSCounter fps, AdvancedGraphics advanced) {
        this.advanced = advanced;
        this.fps = fps;
        this.state = state;
    }

    @Override
    public void draw(Graphics g) {

        // here you can draw graphics

        Font stringFont = new Font("SansSerif", Font.PLAIN, 75);
        g.setFont(stringFont);

        g.setColor(Color.BLACK);
        advanced.centerAlignedString(g, 500, 500, "Hello", stringFont);

        fps.setEnabled(true);
        fps.setColor(Color.RED);
    }

    @Override
    public void update() {
        // here you update game logic
    }
}