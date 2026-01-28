package AdvancedRendering.uiRendering.Menu;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import GameEngine.EngineModules.ClassFactory;
import GameEngine.EngineModules.EngineContext;
import GameEngine.Interfaces.Drawable;
import GameEngine.Interfaces.InterfacePainter;
import GameEngine.Interfaces.MenuInterface;
import GameEngine.Interfaces.Updatable;

public class GameMenu implements Drawable, Updatable, MenuInterface {

    private boolean show;

    private List<MenuInterface> items = new ArrayList<>();

    private InterfacePainter customDrawAction = (gDraw) -> {
        gDraw.setColor(Color.GRAY);
        gDraw.fillRect(100, 100, 500, 400);
    };

    public GameMenu(EngineContext context) {
        ClassFactory.create(this, context);
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