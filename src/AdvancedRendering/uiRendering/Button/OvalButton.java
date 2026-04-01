/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the GPL 3.0 License.
 * See LICENSE file in the project root for full license information.
 *
 * Copyright © 2026 Galactica
 */

package AdvancedRendering.uiRendering.Button;

import java.awt.Point;
import java.awt.geom.Ellipse2D;

import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.EnginePanel;
import GameEngine.EngineModules.Mouse;

public class OvalButton extends RectButton {

    /**
     * Creates and registers an oval button with the specified dimensions.
     * 
     * @param mouse   The mouse input handler used for interaction with the button.
     * 
     * @param context The engine context containing objects involved in rendering,
     *                updating, and input handling.
     * 
     * @param x       The x-coordinate of the oval's topLeft point.
     * 
     * @param y       The y-coordinate of the oval's topLeft point.
     * 
     * @param width   The width of the oval.
     * 
     * @param height  The height of the oval.
     */
    public OvalButton(EngineContext context, EnginePanel panel, Mouse mouse, int x, int y, int width, int height) {

        super(context, panel, mouse, x, y, width, height);

        this.baseShape = new Ellipse2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;

        updateRotatedShape();

    }

    /**
     * Creates and registers an oval button with the specified points.
     * 
     * @param mouse       The mouse input handler used for interaction with the
     *                    button.
     * 
     * @param context     The engine context containing objects involved in
     *                    rendering,
     *                    updating, and input handling.
     * 
     * @param topLeft     The top left point of the oval.
     * 
     * @param bottomRight The bottom left point of the oval.
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
     * Creates and registers an oval button with the specified dimensions and center
     * point.
     *
     * @param mouse   The mouse input handler used for interaction with the button.
     * 
     * @param context The engine context containing objects involved in rendering,
     *                updating, and input handling.
     * 
     * @param middle  The center point of the oval.
     * 
     * @param width   The width of the oval.
     * 
     * @param height  The height of the oval.
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
     * Creates and registers a circular button with the specified dimensions and
     * center point.
     *
     * @param mouse   The mouse input handler used for interaction with the button.
     * 
     * @param context The engine context containing objects involved in rendering,
     *                updating, and input handling.
     * 
     * @param middle  The circle's center point.
     * 
     * @param radius  The circle's radius.
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
