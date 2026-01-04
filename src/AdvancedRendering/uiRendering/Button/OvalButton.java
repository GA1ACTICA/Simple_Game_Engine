package AdvancedRendering.uiRendering.Button;

import java.awt.geom.Ellipse2D;

import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.Mouse;

public class OvalButton extends RectButton {

    public OvalButton(Mouse mouse, EngineContext context, int x, int y, int width, int height) {
        super(mouse, context, x, y, width, height);
        this.shape = new Ellipse2D.Float(x, y, width, height);

    }

}
