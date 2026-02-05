package AdvancedRendering.uiRendering.CheckBox;

import java.awt.Point;
import java.awt.geom.Ellipse2D;

import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.Mouse;

public class OvalCheckbox extends RectCheckbox {

    public OvalCheckbox(Mouse mouse, EngineContext context, Point topLeft, Point bottomRight) {

        int x = (int) topLeft.getX();
        int y = (int) topLeft.getY();
        int width = (int) bottomRight.getX();
        int height = (int) bottomRight.getY();

        super(mouse, context, x, y, width, height);

        this.baseShape = new Ellipse2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;

        updateRotatedShape();
    }

    public OvalCheckbox(Mouse mouse, EngineContext context, Point middle, int width, int height) {

        int x = (int) middle.getX() - width / 2;
        int y = (int) middle.getY() - height / 2;

        super(mouse, context, x, y, width, height);

        this.baseShape = new Ellipse2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;

        updateRotatedShape();
    }

    public OvalCheckbox(Mouse mouse, EngineContext context, int x, int y, int width, int height) {
        super(mouse, context, x, y, width, height);

        this.baseShape = new Ellipse2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;

        updateRotatedShape();
    }

    /**
     * Creates a circle
     * 
     * @param mouse
     * 
     * @param context
     * 
     * @param middle
     * 
     * @param radius
     */
    public OvalCheckbox(Mouse mouse, EngineContext context, Point middle, int radius) {

        int x = (int) middle.getX() - radius;
        int y = (int) middle.getY() - radius;

        super(mouse, context, x, y, radius * 2, radius * 2);

        this.baseShape = new Ellipse2D.Float(x, y, radius * 2, radius * 2);
        this.rotatedShape = baseShape;

        updateRotatedShape();

    }

}
