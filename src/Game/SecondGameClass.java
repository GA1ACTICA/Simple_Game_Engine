package Game;

import java.awt.*;

import AdvancedRendering.worldRendering.AdvancedGraphics;
import GameEngine.Interfaces.Updatable;
import GameEngine.Interfaces.Drawables.Drawable;

public class SecondGameClass implements Drawable, Updatable {

    public SecondGameClass() {
    }

    @Override
    public void draw(Graphics g) {

        // here you can draw graphics

        Font stringFont = new Font("SansSerif", Font.PLAIN, 75);
        g.setFont(stringFont);

        g.setColor(Color.BLACK);
        AdvancedGraphics.centerAlignedString(g, 500, 575, "World!");

    }

    @Override
    public void update() {
        // here you update game logic
    }

}