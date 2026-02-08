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
import GameEngine.Interfaces.UIDrawable;
import GameEngine.Interfaces.Updatable;
import GameEngine.Records.SliderInformation;
import Utils.MathTools;

public class Slider implements UIDrawable, Updatable, MenuInterface {

    boolean show = false;

    // Slider looks
    private Point pointOne, pointTwo;
    private Color sliderColor = new Color(60, 60, 60, 153); // ~60% transparency
    private int sliderWidth = 5;

    // Slider values
    private double sliderPercentage = 50.0;
    private int sliderMin = 0;
    private int sliderMax = 100;

    // handle looks
    private int handleWidth = sliderWidth + 8;
    private int handleHeight = 25;
    private double handleAngle;

    private boolean holding;

    private RectButton handle;
    private final Mouse mouse;

    public Slider(EngineContext context, EnginePanel panel, Mouse mouse, Point pointOne, Point pointTwo) {
        ClassFactory.create(this, context);
        this.mouse = mouse;
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;

        Point middle = new Point(((pointOne.x + pointTwo.x) / 2), ((pointOne.y + pointTwo.y) / 2));

        handle = new RectButton(context, panel, mouse, middle,
                handleWidth, handleHeight);

        handleAngle = Math.toDegrees(Math.atan2(
                pointTwo.y - pointOne.y,
                pointTwo.x - pointOne.x)) - 90;

        handle.setRotation(handleAngle);
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

    public double getLength() {

        double length = MathTools.pythagoras(pointOne, pointTwo);

        return length;
    }

    public double getPercentage() {
        return sliderPercentage;
    }

    public double getSliderValue() {
        return sliderPercentage / 100 * sliderMax;
    }

    // fix better java doc
    /**
     * Returns information about the values of the current "handle" position on the
     * slider.
     * 
     * @return SliderInformation
     */
    public SliderInformation getSliderInformation() {
        return new SliderInformation(sliderPercentage / 100 * sliderMax, sliderPercentage);
    }

    public int getSliderMin() {
        return sliderMin;
    }

    public int getSliderMax() {
        return sliderMax;
    }

    public Color getSliderColor() {
        return sliderColor;
    }

    public RectButton getHandle() {
        return handle;
    }

    public double getHandleAngle() {
        return handleAngle;
    }

    public void setHandle(RectButton handle) {
        this.handle = handle;

        // Sets the handle at the correct x and y even if the new handles constructor
        // was wrong
        setPercentage(getPercentage());

        // Update handle angle if a custom angle is not set
        if (handleAngle == Math.toDegrees(Math.atan2(
                pointTwo.y - pointOne.y,
                pointTwo.x - pointOne.x)) - 90)
            handle.setRotation(handleAngle);

    }

    public void setHandleAngle(double angle) {
        handleAngle = angle;
        handle.setRotation(handleAngle);

    }

    public void setSliderValue(int value) {
        setPercentage((double) value / sliderMax * 100);
    }

    public void setSliderMax(int sliderMax) {
        this.sliderMax = sliderMax;
    }

    public void setSliderMin(int sliderMin) {
        this.sliderMin = sliderMin;
    }

    public void setSliderWidth(int sliderWidth) {
        this.sliderWidth = sliderWidth;
    }

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

        setPercentage(getPercentage());

    }

    public void setSliderColor(Color sliderColor) {
        this.sliderColor = sliderColor;
    }

    public void setPercentage(double percentage) {

        double deltaX = pointTwo.x - pointOne.x;
        double deltaY = pointTwo.y - pointOne.y;

        int px = (int) Math.round(pointOne.x + percentage / 100 * deltaX);
        int py = (int) Math.round(pointOne.y + percentage / 100 * deltaY);

        handle.setMiddle(new Point(px, py));

        this.sliderPercentage = percentage;
    }

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

        if (handle.inside && mouse.getLeftDown() && !holding) {
            holding = true;
            handle.setInsideOveride(true);
        }

        if (!mouse.getLeftDown() && holding) {
            holding = false;
            handle.setInsideOveride(false);
        }

        // Only run when holding / draging
        if (holding) {

            // Update "sliderProcentage"
            sliderPercentage = MathTools.fixToLine(mouse.getPoint(), pointOne, pointTwo).progress() * 100;

            // Update "handle" position
            handle.setMiddle(MathTools.fixToLine(mouse.getPoint(), pointOne, pointTwo).point());

        }
    }
}
