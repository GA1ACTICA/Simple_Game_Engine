/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the GPL 3.0 License.
 * See LICENSE file in the project root for full license information.

 Coppyright © 2026 Galactica
 */

package AdvancedRendering.uiRendering.Menu;

import java.awt.*;

import AdvancedRendering.worldRendering.AdvancedGraphics;
import GameEngine.EngineModules.ClassFactory;
import GameEngine.EngineModules.EngineContext;
import GameEngine.Interfaces.*;
import GameEngine.Interfaces.Drawables.UIDrawable;

public class GameMenu extends UIContainer
        implements UIDrawable, Updatable {

    private boolean show;

    private int zIndex = 0;

    private int x = 100;
    private int y = 100;
    private int width = 200;
    private int height = 200;

    private InterfacePainter customDrawAction = (gDraw) -> {
        gDraw.setColor(new Color(10, 10, 10, 125));
        gDraw.fillRect(x, y, width, height);

        gDraw.setColor(Color.BLACK);
        gDraw.setFont(new Font("SansSerif", Font.PLAIN, 25));
        AdvancedGraphics.centerAlignedString(gDraw, x + width / 2, (int) (y + height * 0.2),
                "This is a menu");
    };

    private EngineContext context;

    public GameMenu(EngineContext context) {
        ClassFactory.create(this, context, zIndex);
    };

    @Override
    public void show() {
        show = true;
        super.show();
    }

    @Override
    public void hide() {
        show = false;
        super.hide();
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        super.setSize(width, height);
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        super.setPosition(x, y);
    }

    @Override
    public void setPosition(Point position) {
        x = position.x;
        y = position.y;
        super.setPosition(position);
    }

    @Override
    public void changePosition(int x, int y) {
        this.x += x;
        this.y += y;
        super.changePosition(x, y);

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

    @Override
    public void setZIndex(int zIndex) {
        ClassFactory.updatePriority(this, context, zIndex);
        this.zIndex = zIndex;
        return;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }
}