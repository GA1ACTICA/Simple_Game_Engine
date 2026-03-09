package GameEngine.Interfaces.Drawables;

import java.awt.Graphics;

import GameEngine.Interfaces.ZIndexable;

public interface Drawable extends ZIndexable {
    void draw(Graphics g);
}