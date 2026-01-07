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
import GameEngine.EngineModules.EngineTools;
import GameEngine.EngineModules.Mouse;
import GameEngine.Interfaces.UIDrawable;
import GameEngine.Interfaces.Updatable;

public class Slider implements UIDrawable, Updatable {

    boolean show = false;

    // Slider looks
    Point pointOne, pointTwo;
    Color sliderColor = new Color(60, 60, 60, 153); // ~60% transparency
    int sliderWidth = 5;

    // Slider values
    float sliderProcentage = 50.0f;
    int sliderMin = 0;
    int sliderMax = 100;

    // handle looks
    int handleWidth = sliderWidth + 8;
    int handleHeight = 25;

    boolean holding;

    RectButton handle;
    Mouse mouse;

    public Slider(Mouse mouse, EngineContext context, Point pointOne, Point pointTwo) {
        ClassFactory.create(this, context);
        this.mouse = mouse;
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;
        // TEMP: x, y ,width, height should be diffrent from constructor

        handle = new RectButton(mouse, context, (pointTwo.x + pointOne.x) / 2, (pointTwo.y + pointOne.y) / 2,
                handleWidth, handleHeight);
    }

    public void show() {
        show = true;
        handle.show();
    }

    public void hide() {
        show = false;
        handle.hide();
    }

    public double getLength() {

        double length = EngineTools.pythagoras(pointOne, pointTwo);

        return length;
    }

    public float getPrecentage() {
        return sliderProcentage;
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

        // Set position of "handle"
        if (holding) {
        }

    }

    @Override
    public void update() {
        if (!show)
            return;

        if (handle.inside && mouse.getLeftDown() && !holding)
            holding = true;

        if (!mouse.getLeftDown() && holding)
            holding = false;

        // Only run when holding / draging
        if (holding) {
            // Update "sliderProcentage"
            sliderProcentage = (float) ((EngineTools.pythagoras(pointOne, handle.getMiddlePoint()) / getLength())
                    * 100);

            // Update "handle" position
            handle.setMiddle(EngineTools.fixToLine(mouse.getPoint(), pointOne, pointTwo));

        }
    }
}
