package Game;

import java.awt.*;

import AdvancedRendering.uiRendering.Misc.FPSCounter;
import AdvancedRendering.worldRendering.AdvancedGraphics;
import GameEngine.Interfaces.*;

public class MainGameClass implements Drawable, Updatable {

    @SuppressWarnings("unused")
    private final GameState state;
    private final FPSCounter fps;
    private final AdvancedGraphics advanced;

    public MainGameClass(GameState state, FPSCounter fps, AdvancedGraphics advanced) {
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
        advanced.centerAlignedString(g, 500, 500, "Hello");

        fps.show();
        fps.setColor(Color.RED);
    }

    @Override
    public void update() {
        // here you update game logic
    }
}