package Game;

import java.awt.*;

import AdvancedRendering.worldRendering.AdvancedGraphics;
import GameEngine.Interfaces.*;

public class MainGameClass implements Drawable, Updatable {

    public MainGameClass() {
    }

    @Override
    public void draw(Graphics g) {

        // here you can draw graphics

        Font stringFont = new Font("SansSerif", Font.PLAIN, 75);
        g.setFont(stringFont);

        g.setColor(Color.BLACK);
        AdvancedGraphics.centerAlignedString(g, 500, 500, "Hello");

    }

    @Override
    public void update() {
        // here you update game logic
    }
}