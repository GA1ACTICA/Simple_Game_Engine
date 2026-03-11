/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the GPL 3.0 License.
 * See LICENSE file in the project root for full license information.

 Coppyright © 2026 Galactica
 */

package Utils;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import GameEngine.EngineModules.ClassFactory;
import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.EnginePanel;
import GameEngine.EngineModules.Mouse;
import GameEngine.Interfaces.Drawables.CursorDrawable;

public class CustomCursor implements CursorDrawable {

    private int zIndex = 0;

    private int x, y, width, height;

    private BufferedImage cursorImage;

    private boolean show;

    private EnginePanel panel;
    private Mouse mouse;
    private EngineContext context;

    public CustomCursor(EngineContext context, EnginePanel panel, Mouse mouse) {
        this.panel = panel;
        this.mouse = mouse;
        this.context = context;
        ClassFactory.create(this, context);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void hideCursor() {
        show = false;
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void showCursor() {
        show = true;
        panel.setCursor(panel.getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));
    }

    public void loadCursor(String imagePath) {
        cursorImage = FileTools.getBufferedImage(imagePath, BufferedImage.TYPE_INT_ARGB);

        // skips if a custom width or height is allready set
        if (width == 0)
            width = cursorImage.getWidth();

        if (height == 0)
            height = cursorImage.getHeight();

    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        x = mouse.getPoint().x - width / 2;
        y = mouse.getPoint().y - height / 2;
        g.drawImage(cursorImage, x, y, width, height, null);
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
}
