package AdvancedRendering.uiRendering.CheckBox;

import java.awt.Point;

import AdvancedRendering.uiRendering.Button.RectButton;
import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.Mouse;

public class RectCheckBox extends RectButton {

    private boolean toggled = false;

    public RectCheckBox(Mouse mouse, EngineContext context, Point topLeft, Point bottomRight) {
        super(mouse, context, topLeft, bottomRight);
    }

    public RectCheckBox(Mouse mouse, EngineContext context, Point middle, int width, int height) {
        super(mouse, context, middle, width, height);
    }

    public RectCheckBox(Mouse mouse, EngineContext context, int x, int y, int width, int height) {
        super(mouse, context, x, y, width, height);
    }

    @Override
    public void update() {
        if (!show)
            return;

        if (inside && mouse.getLeftDown() && !wasPressed)
            wasPressed = true;

        if (wasPressed && !mouse.getLeftDown()) {
            wasPressed = false;

            toggled = !toggled;

            if (toggled)
                clickAction.run();

        }
    }

}
