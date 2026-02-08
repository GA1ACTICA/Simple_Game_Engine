package AdvancedRendering.uiRendering.Button;

import java.awt.Point;
import java.awt.geom.Ellipse2D;

import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.EnginePanel;
import GameEngine.EngineModules.Mouse;

public class OvalButton extends RectButton {

    /**
     * 
     * @param mouse
     * 
     * @param context
     * 
     * @param x
     * 
     * @param y
     * 
     * @param width
     * 
     * @param height
     */
    public OvalButton(EngineContext context, EnginePanel panel, Mouse mouse, int x, int y, int width, int height) {

        super(context, panel, mouse, x, y, width, height);

        this.baseShape = new Ellipse2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;

        updateRotatedShape();

    }

    /**
     * 
     * @param mouse
     * 
     * @param context
     * 
     * @param topLeft
     * 
     * @param bottomRight
     */
    public OvalButton(EngineContext context, EnginePanel panel, Mouse mouse, Point topLeft, Point bottomRight) {

        int x = (int) topLeft.getX();
        int y = (int) topLeft.getY();
        int width = (int) bottomRight.getX() - (int) topLeft.getX();
        int height = (int) bottomRight.getY() - (int) topLeft.getY();

        super(context, panel, mouse, x, y, width, height);

        this.baseShape = new Ellipse2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;

        updateRotatedShape();

    }

    /**
     * 
     * @param mouse
     * 
     * @param context
     * 
     * @param middle
     * 
     * @param width
     * 
     * @param height
     */
    public OvalButton(EngineContext context, EnginePanel panel, Mouse mouse, Point middle, int width, int height) {

        int x = (int) middle.getX() - width / 2;
        int y = (int) middle.getY() - height / 2;

        super(context, panel, mouse, x, y, width, height);

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
    public OvalButton(EngineContext context, EnginePanel panel, Mouse mouse, Point middle, int radius) {

        int x = (int) middle.getX() - radius;
        int y = (int) middle.getY() - radius;

        super(context, panel, mouse, x, y, radius * 2, radius * 2);

        this.baseShape = new Ellipse2D.Float(x, y, radius * 2, radius * 2);
        this.rotatedShape = baseShape;

        updateRotatedShape();

    }

}
