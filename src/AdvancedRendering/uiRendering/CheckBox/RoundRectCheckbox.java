package AdvancedRendering.uiRendering.CheckBox;

import java.awt.Point;
import java.awt.geom.RoundRectangle2D;

import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.EnginePanel;
import GameEngine.EngineModules.Mouse;

public class RoundRectCheckbox extends RectCheckbox {

    public RoundRectCheckbox(EngineContext context, EnginePanel panel, Mouse mouse, Point topLeft, Point bottomRight,
            int arcWidth,
            int arcHeight) {

        int x = (int) topLeft.getX();
        int y = (int) topLeft.getY();
        int width = (int) bottomRight.getX();
        int height = (int) bottomRight.getY();

        super(context, panel, mouse, x, y, width, height);
        this.baseShape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
        this.rotatedShape = baseShape;

        updateRotatedShape();
    }

    public RoundRectCheckbox(EngineContext context, EnginePanel panel, Mouse mouse, Point middle, int width, int height,
            int arcWidth,
            int arcHeight) {

        int x = (int) middle.getX() - width / 2;
        int y = (int) middle.getY() - height / 2;

        super(context, panel, mouse, x, y, width, height);

        this.baseShape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
        this.rotatedShape = baseShape;

        updateRotatedShape();
    }

    public RoundRectCheckbox(EngineContext context, EnginePanel panel, Mouse mouse, int x, int y, int width, int height,
            int arcWidth,
            int arcHeight) {
        super(context, panel, mouse, x, y, width, height);

        this.baseShape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
        this.rotatedShape = baseShape;

        updateRotatedShape();
    }

}
