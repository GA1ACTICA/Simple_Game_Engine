package AdvancedRendering.uiRendering.Misc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import GameEngine.EngineModules.ClassFactory;
import GameEngine.EngineModules.EngineContext;
import GameEngine.Interfaces.MenuInterface.*;
import GameEngine.Interfaces.*;

public class FPSCounter implements UIDrawable, Updatable, MenuInterface, MenuSetPosition, MenuSetColor {

    private boolean show = false;

    private int frames = 0;
    private int fps = 0;
    private long timer = System.currentTimeMillis();

    private int x = 10;
    private int y = 25;
    private Font font = new Font("Arial", Font.PLAIN, 25);
    private Color color = Color.BLACK;

    public FPSCounter(EngineContext context) {
        ClassFactory.create(this, context);
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
    public void changePosition(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public void setFont(Font font) {
        this.font = font;
    }

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
