/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 *
 * Copyright © 2026 Galactica
 */

package game;

import java.awt.*;

import gameEngine.interfaces.Updatable;
import gameEngine.interfaces.drawables.Drawable;

public class SecondGameClass implements Drawable, Updatable {

    public SecondGameClass() {
    }

    private int zIndex = 0;

    /**
     * @param g
     */
    @Override
    public void draw(Graphics g) {

        // here you can draw graphics

        Font stringFont = new Font("SansSerif", Font.PLAIN, 75);
        g.setFont(stringFont);

        g.setColor(Color.BLACK);
        g.drawString("World", 500, 550);

    }

    @Override
    public void update(float deltaTime) {
        // here you update game logic
    }

    /**
     * @param ZIndex
     */
    @Override
    public void setZIndex(int ZIndex) {

    }

    /**
     * @return int
     */
    @Override
    public int getZIndex() {
        return zIndex;
    }

}