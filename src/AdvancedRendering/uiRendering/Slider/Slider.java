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

package AdvancedRendering.uiRendering.Slider;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

import AdvancedRendering.uiRendering.Button.RectButton;
import GameEngine.EngineModules.ClassFactory;
import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.EnginePanel;
import GameEngine.EngineModules.Mouse;
import GameEngine.Interfaces.MenuInterface;
import GameEngine.Interfaces.Updatable;
import GameEngine.Interfaces.Drawables.UIDrawable;
import GameEngine.Records.FixResult;
import GameEngine.Records.SliderInformation;
import Utils.MathTools;

public class Slider implements UIDrawable, Updatable, MenuInterface {

    private boolean show = false;

    private int zIndex = 0;

    // Slider looks
    private Point pointOne, pointTwo;
    private Color sliderColor = new Color(60, 60, 60, 153); // ~60% transparency
    private int sliderWidth = 5;

    // Slider values
    private double sliderPercentage = 0.50;
    private int sliderMin = 0;
    private int sliderMax = 100;

    // handle looks
    private int handleWidth = sliderWidth + 8;
    private int handleHeight = 25;
    private double handleAngle;

    private RectButton handle;
    private final Mouse mouse;

    private EngineContext context;

    public Slider(EngineContext context, EnginePanel panel, Mouse mouse, Point pointOne, Point pointTwo) {
        ClassFactory.create(this, context);
        this.mouse = mouse;
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;
        this.context = context;

        Point middle = new Point(((pointOne.x + pointTwo.x) / 2), ((pointOne.y + pointTwo.y) / 2));

        handle = new RectButton(context, panel, mouse, middle,
                handleWidth, handleHeight);

        handleAngle = Math.toDegrees(Math.atan2(
                pointTwo.y - pointOne.y,
                pointTwo.x - pointOne.x)) - 90;

        handle.setRotation(handleAngle);

    }

    /**
     * @param zIndex
     */
    @Override
    public void setZIndex(int zIndex) {
        ClassFactory.updatePriority(this, context, zIndex);
        handle.setZIndex(zIndex);
        this.zIndex = zIndex;
    }

    /**
     * @return int
     */
    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public void show() {
        show = true;
        handle.show();
    }

    @Override
    public void hide() {
        show = false;
        handle.hide();
    }

    /**
     * @return double
     */
    public double getLength() {

        double length = MathTools.pythagoras(pointOne, pointTwo);

        return length;
    }

    /**
     * @return double
     */
    public double getSliderValue() {
        return sliderPercentage * sliderMax;
    }

    // TODO: fix better javadoc
    /**
     * Returns information about the values of the current "handle" position on the
     * slider.
     * 
     * @return SliderInformation
     */
    public SliderInformation getSliderInformation() {
        return new SliderInformation(sliderPercentage * sliderMax, sliderPercentage);
    }

    /**
     * @return int
     */
    public int getSliderMin() {
        return sliderMin;
    }

    /**
     * @return int
     */
    public int getSliderMax() {
        return sliderMax;
    }

    /**
     * @return double
     */
    public double getSliderPercentage() {
        return sliderPercentage;
    }

    /**
     * @return Color
     */
    public Color getSliderColor() {
        return sliderColor;
    }

    /**
     * @return RectButton
     */
    public RectButton getHandle() {
        return handle;
    }

    /**
     * @return double
     */
    public double getHandleAngle() {
        return handleAngle;
    }

    /**
     * @return Point
     */
    public Point getPointOne() {
        return pointOne;
    }

    /**
     * @return Point
     */
    public Point getPointTwo() {
        return pointTwo;
    }

    /**
     * @param sliderColor
     */
    public void setColor(Color sliderColor) {
        this.sliderColor = sliderColor;
    }

    /**
     * @param handle
     */
    public void setHandle(RectButton handle) {
        this.handle = handle;

        // Sets the handle at the correct x and y even if the new handles constructor
        // was wrong

        updateSlider();
        // Update handle angle if a custom angle is not set
        if (handleAngle == Math.toDegrees(Math.atan2(
                pointTwo.y - pointOne.y,
                pointTwo.x - pointOne.x)) - 90)
            handle.setRotation(handleAngle);

    }

    /**
     * @param angle
     */
    public void setHandleAngle(double angle) {
        handleAngle = angle;
        handle.setRotation(handleAngle);

    }

    /**
     * @param value
     */
    public void setSliderValue(int value) {
        setPercentage((double) value / sliderMax * 100);
    }

    /**
     * @param sliderMax
     */
    public void setSliderMax(int sliderMax) {
        this.sliderMax = sliderMax;
    }

    /**
     * @param sliderMin
     */
    public void setSliderMin(int sliderMin) {
        this.sliderMin = sliderMin;
    }

    /**
     * @param sliderWidth
     */
    public void setSliderWidth(int sliderWidth) {
        this.sliderWidth = sliderWidth;
    }

    /**
     * @param pointOne
     * @param pointTwo
     */
    public void setSliderPoints(Point pointOne, Point pointTwo) {

        // Update handle angle if a custom angle is not set
        if (handleAngle == Math.toDegrees(Math.atan2(
                this.pointTwo.y - this.pointOne.y,
                this.pointTwo.x - this.pointOne.x)) - 90) {

            handleAngle = Math.toDegrees(Math.atan2(
                    pointTwo.y - pointOne.y,
                    pointTwo.x - pointOne.x)) - 90;

            handle.setRotation(handleAngle);

        }

        this.pointOne = pointOne;
        this.pointTwo = pointTwo;

        updateSlider();
    }

    /**
     * @param percentage
     */
    public void setPercentage(double percentage) {
        this.sliderPercentage = percentage;

        updateSlider();
    }

    public void updateSlider() {
        double deltaX = pointTwo.x - pointOne.x;
        double deltaY = pointTwo.y - pointOne.y;

        int px = (int) Math.round(pointOne.x + sliderPercentage * deltaX);
        int py = (int) Math.round(pointOne.y + sliderPercentage * deltaY);

        handle.setCenter(new Point(px, py));
    }

    /**
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(sliderColor);

        // Draws a line with thickness "sliderWidth"
        g2d.setStroke(new BasicStroke(sliderWidth));
        g2d.draw(new Line2D.Float(pointOne, pointTwo));

    }

    @Override
    public void update() {
        if (!show)
            return;

        // Only run when holding / dragging
        if (handle.isPressed()) {

            FixResult sliderResult = MathTools.fixToLine(mouse.getPoint(), pointOne, pointTwo);

            sliderPercentage = sliderResult.progress();
            handle.setCenter(sliderResult.point());

        }
    }
}
