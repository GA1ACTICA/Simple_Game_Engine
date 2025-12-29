package Game;

import java.awt.*;

import AdvancedRendering.worldRendering.AdvancedGraphics;
import GameEngine.Interfaces.Drawable;
import GameEngine.Interfaces.Updatable;

public class SecondGameClass implements Drawable, Updatable {

    @SuppressWarnings("unused")
    private final GameState state;
    private final AdvancedGraphics advanced;

    public SecondGameClass(GameState state, AdvancedGraphics advanced) {
        this.advanced = advanced;
        this.state = state;

    }

    @Override
    public void draw(Graphics g) {

        // here you can draw graphics

        Font stringFont = new Font("SansSerif", Font.PLAIN, 75);
        g.setFont(stringFont);

        g.setColor(Color.BLACK);
        advanced.centerAlignedString(g, 500, 575, "World!");

    }

    @Override
    public void update() {
        // here you update game logic
    }

}