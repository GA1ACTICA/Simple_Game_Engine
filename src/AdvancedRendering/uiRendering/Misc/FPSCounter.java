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
    private int ups = 0;
    private long timer = System.nanoTime();

    private int x = 10;
    private int y = 25;
    private Font font = new Font("Arial", Font.PLAIN, 25);
    private Color color = Color.BLACK;

    private EngineContext context;

    /**
     * Creates and registers an FPS/UPS counter.
     * <p>
     * In Swing/AWT-based applications, frame rate cannot be measured directly with
     * high accuracy. Instead, this class measures the number of {@link Updatable}
     * update calls per second (UPS). If rendering occurs on every update, this
     * value can be used as an approximation of the frame rate (FPS).
     *
     * @param context the engine context containing components related to rendering,
     *                updating, and input handling
     */
    public FPSCounter(EngineContext context) {
        this.context = context;
        ClassFactory.create(this, context);
    }

    @Override
    public void setZIndex(int zIndex) {
        ClassFactory.updatePriority(this, context, zIndex);
        this.zIndex = zIndex;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    public boolean isVisible() {
        return show;
    }

    @Override
    public void show() {
        show = true;
    }

    @Override
    public void hide() {
        show = false;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setPosition(Point position) {
        this.x = position.x;
        this.y = position.y;
    }

    @Override
    public void translatePosition(int dx, int dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getUPS() {
        return ups;
    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        g.setFont(font);
        g.setColor(color);
        g.drawString("FPS/UPS: " + Integer.toString(ups), x, y);
    }

    @Override
    public void update() {

        frames++;

        long now = System.nanoTime();
        long interval = 1_000_000_000L; // 1 second in nanoseconds

        while (now - timer >= interval) {
            ups = frames;
            frames = 0;
            timer += interval;
        }
    }
}
