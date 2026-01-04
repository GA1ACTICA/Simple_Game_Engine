package AdvancedRendering.uiRendering.Button;

import java.awt.geom.RoundRectangle2D;

import GameEngine.EngineModules.EngineContext;
import GameEngine.EngineModules.Mouse;

public class RoundRectButton extends RectButton {

    public RoundRectButton(Mouse mouse, EngineContext context, int x, int y, int width, int height, int arcWidth,
            int arcHeight) {
        super(mouse, context, x, y, width, height);
        this.shape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
    }

}
