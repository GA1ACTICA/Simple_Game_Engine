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

package AdvancedRendering.uiRendering.Misc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import GameEngine.EngineModules.ClassFactory;
import GameEngine.EngineModules.EngineContext;
import GameEngine.Interfaces.MenuInterface.*;
import GameEngine.Interfaces.*;
import GameEngine.Interfaces.Drawables.UIDrawable;

public class FPSCounter implements UIDrawable, Updatable, MenuInterface, MenuSetPosition, MenuSetColor {

    private boolean show = false;

    private int zIndex = 0;

    private int frames = 0;
    private int fps = 0;
    private long timer = System.currentTimeMillis();

    private int x = 10;
    private int y = 25;
    private Font font = new Font("Arial", Font.PLAIN, 25);
    private Color color = Color.BLACK;

    private EngineContext context;

    public FPSCounter(EngineContext context) {
        ClassFactory.create(this, context);
    }

    /**
     * @param zIndex
     */
    @Override
    public void setZIndex(int zIndex) {
        ClassFactory.updatePriority(this, context, zIndex);
        this.zIndex = zIndex;
    }

    /**
     * @return int
     */
    @Override
    public int getZIndex() {
        return zIndex;
    }

    /**
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * @return int
     */
    public int getY() {
        return y;
    }

    /**
     * @return Color
     */
    public Color getColor() {
        return color;
    }

    @Override
    public void show() {
        show = true;
    }

    @Override
    public void hide() {
        show = false;
    }

    /**
     * @param x
     * @param y
     */
    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param position
     */
    @Override
    public void setPosition(Point position) {
        this.x = position.x;
        this.y = position.y;
    }

    /**
     * @param dx
     * @param dy
     */
    @Override
    public void translatePosition(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * @param color
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @param font
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        g.setFont(font);
        g.setColor(color);
        g.drawString("FPS/UPS: " + Integer.toString(fps), x, y);
    }

    @Override
    public void update() {
        if (!show)
            return;

        frames++;

        if (System.currentTimeMillis() - timer >= 1000) {
            fps = frames;
            frames = 0;
            timer += 1000;
        }
    }
}
