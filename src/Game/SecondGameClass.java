/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Description: This is an example game class and is only use as an example. I strongly recomend to delete this and create your own
 * 
 * Licensed under the GPL 3.0 License.
 * See LICENSE file in the project root for full license information.
 *
 * Coppyright © 2026 Galactica
 */

package Game;

import java.awt.*;

import AdvancedRendering.worldRendering.AdvancedGraphics;
import GameEngine.Interfaces.Updatable;
import GameEngine.Interfaces.Drawables.Drawable;

public class SecondGameClass implements Drawable, Updatable {

    public SecondGameClass() {
    }

    private int zIndex = 0;

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

    @Override
    public void setZIndex(int ZIndex) {

    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

}