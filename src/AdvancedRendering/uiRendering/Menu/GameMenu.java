package AdvancedRendering.uiRendering.Menu;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import AdvancedRendering.worldRendering.AdvancedGraphics;
import GameEngine.EngineModules.ClassFactory;
import GameEngine.EngineModules.EngineContext;

import GameEngine.Interfaces.*;
import GameEngine.Interfaces.Drawables.UIDrawable;
import GameEngine.Interfaces.MenuInterface.*;

public class GameMenu implements UIDrawable, Updatable, MenuInterface, MenuSetSize, MenuSetPosition {

    private boolean show;

    private int x = 100;
    private int y = 100;
    private int width = 200;
    private int height = 200;

    private List<MenuInterface> items = new ArrayList<>();

    private InterfacePainter customDrawAction = (gDraw) -> {
        gDraw.setColor(new Color(10, 10, 10, 125));
        gDraw.fillRect(x, y, width, height);

        gDraw.setColor(Color.BLACK);
        gDraw.setFont(new Font("SansSerif", Font.PLAIN, 25));
        AdvancedGraphics.centerAlignedString(gDraw, x + width / 2, (int) (y + height * 0.2),
                "This is a menu");
    };

    public GameMenu(EngineContext context) {
        ClassFactory.create(this, context);
    };

    public GameMenu() {
    };

    @Override
    public void show() {
        show = true;

        for (MenuInterface item : items) {
            item.show();
        }
    }

    @Override
    public void hide() {
        show = false;

        for (MenuInterface item : items) {
            item.hide();
        }
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;

        for (MenuInterface item : items) {
            if (item instanceof MenuSetSize r) {
                r.setSize(width, height);
            }
        }
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;

        for (MenuInterface item : items) {
            if (item instanceof MenuSetPosition r) {
                r.setPosition(x, y);
            }
        }
    }

    @Override
    public void setPosition(Point position) {
        x = position.x;
        y = position.y;

        for (MenuInterface item : items) {
            if (item instanceof MenuSetPosition r) {
                r.setPosition(position);
            }
        }
    }

    @Override
    public void changePosition(int x, int y) {
        this.x += x;
        this.y += y;

        for (MenuInterface item : items) {
            if (item instanceof MenuSetPosition r) {
                r.changePosition(x, y);
            }
        }
    }

    public void add(MenuInterface item) {
        items.add(item);
    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        Graphics2D g2d = (Graphics2D) g;

        customDrawAction.paint(g2d);
    }

    @Override
    public void update() {
        if (!show)
            return;

    }
}