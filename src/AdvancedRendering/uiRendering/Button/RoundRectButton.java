package AdvancedRendering.uiRendering.Button;

import java.awt.Point;
import java.awt.geom.RoundRectangle2D;

import GameEngine.EngineModules.EngineContext;
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
    public RoundRectButton(Mouse mouse, EngineContext context, int x, int y, int width, int height, int arcWidth,
            int arcHeight) {
        super(mouse, context, x, y, width, height);

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
    public RoundRectButton(Mouse mouse, EngineContext context, Point topLeft, Point bottomRight, int arcWidth,
            int arcHeight) {

        int x = (int) topLeft.getX();
        int y = (int) topLeft.getY();
        int width = (int) bottomRight.getX();
        int height = (int) bottomRight.getY();

        super(mouse, context, x, y, width, height);
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
    public RoundRectButton(Mouse mouse, EngineContext context, Point middle, int width, int height, int arcWidth,
            int arcHeight) {

        int x = (int) middle.getX() - width / 2;
        int y = (int) middle.getY() - height / 2;

        super(mouse, context, x, y, width, height);

        this.baseShape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
        this.rotatedShape = baseShape;

        updateRotatedShape();

    }

}
