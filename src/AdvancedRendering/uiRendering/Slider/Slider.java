package AdvancedRendering.uiRendering.Slider;

import java.awt.Graphics;

import AdvancedRendering.uiRendering.Button.RectButton;
import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.Mouse;
import GameEngine.Interfaces.UIDrawable;
import GameEngine.Interfaces.Updatable;

public class Slider implements UIDrawable, Updatable {

    public Slider(Mouse mouse, EngineContext context, int x, int y, int width, int height) {

        // TEMP: x, y ,width, height should be diffrent from constructor

        RectButton handle = new RectButton(mouse, context, x, y, width, height);
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void update() {

    }

}
