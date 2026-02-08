package AdvancedRendering.uiRendering.Button;

import java.awt.Point;
import java.awt.geom.RoundRectangle2D;

import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.EnginePanel;
import GameEngine.EngineModules.Mouse;

public class RoundRectButton extends RectButton {

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
     * 
     * @param arcWidth
     * 
     * @param arcHeight
     */
    public RoundRectButton(EngineContext context, EnginePanel panel, Mouse mouse, int x, int y, int width, int height,
            int arcWidth,
            int arcHeight) {

        super(context, panel, mouse, x, y, width, height);

        this.baseShape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
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
     * 
     * @param arcWidth
     * 
     * @param arcHeight
     */
    public RoundRectButton(EngineContext context, EnginePanel panel, Mouse mouse, Point topLeft, Point bottomRight,
            int arcWidth,
            int arcHeight) {

        int x = (int) topLeft.getX();
        int y = (int) topLeft.getY();
        int width = (int) bottomRight.getX() - (int) topLeft.getX();
        int height = (int) bottomRight.getY() - (int) topLeft.getY();

        super(context, panel, mouse, x, y, width, height);

        this.baseShape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
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
     * 
     * @param arcWidth
     * 
     * @param arcHeight
     */
    public RoundRectButton(EngineContext context, EnginePanel panel, Mouse mouse, Point middle, int width, int height,
            int arcWidth,
            int arcHeight) {

        int x = (int) middle.getX() - width / 2;
        int y = (int) middle.getY() - height / 2;

        super(context, panel, mouse, x, y, width, height);

        this.baseShape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
        this.rotatedShape = baseShape;

        updateRotatedShape();

    }

}
