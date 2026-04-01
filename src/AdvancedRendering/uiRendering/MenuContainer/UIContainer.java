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

package AdvancedRendering.uiRendering.MenuContainer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import GameEngine.Interfaces.MenuInterface;
import GameEngine.Interfaces.MenuInterface.MenuSetPosition;
import GameEngine.Interfaces.MenuInterface.MenuSetSize;

public class UIContainer implements MenuInterface, MenuSetSize, MenuSetPosition {

    private List<MenuInterface> items = new ArrayList<>();

    /**
     * @param item
     */
    public void add(MenuInterface item) {
        items.add(item);
    }

    public void show() {
        for (MenuInterface item : items) {
            item.show();
        }
    }

    public void hide() {
        for (MenuInterface item : items) {
            item.hide();
        }
    }

    /**
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        for (MenuInterface item : items) {
            if (item instanceof MenuSetSize r) {
                r.setSize(width, height);
            }
        }
    }

    /**
     * @param x
     * @param y
     */
    @Override
    public void setPosition(int x, int y) {
        for (MenuInterface item : items) {
            if (item instanceof MenuSetPosition r) {
                r.setPosition(x, y);
            }
        }
    }

    /**
     * @param position
     */
    @Override
    public void setPosition(Point position) {
        for (MenuInterface item : items) {
            if (item instanceof MenuSetPosition r) {
                r.setPosition(position);
            }
        }
    }

    /**
     * @param dx
     * @param dy
     */
    @Override
    public void translate(int dx, int dy) {
        for (MenuInterface item : items) {
            if (item instanceof MenuSetPosition r) {
                r.translate(dx, dy);
            }
        }
    }

}