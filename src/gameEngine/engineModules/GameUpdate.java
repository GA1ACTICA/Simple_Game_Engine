
/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 *
 * Copyright © 2026 Galactica
 */

package gameEngine.engineModules;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;

import advancedRendering.uiRendering.button.RectButton;
import advancedRendering.uiRendering.misc.UPSCounter;
import advancedRendering.uiRendering.slider.Slider;
import advancedRendering.uiRendering.textField.TextField;
import game.*;
import game.configs.gameState.GameState;
import gameEngine.interfaces.Updatable;

public class GameUpdate implements Runnable {

    private boolean running = true;
    private long lastUpdateTime;
    private long currentTime;

    private GameState state;
    private final EnginePanel panel;
    private final EngineContext context;

    Slider s;

    public GameUpdate(
            Keys keys,
            Mouse mouse,
            GameState state,
            EnginePanel panel,
            JFrame frame,
            EngineContext context) {
        this.state = state;
        this.panel = panel;
        this.context = context;
        state.setGameStateData(state);

        // constructors from engine
        UPSCounter fps = new UPSCounter(context);
        fps.setColor(Color.RED);
        fps.show();
        fps.setZIndex(100);

        TextField t = new TextField(context, panel, mouse, keys, 10, 10, 400, 10);
        t.show();

        RectButton b = new RectButton(context, panel, mouse, new Point(500, 500), new Point(600, 600));
        b.show();

        ClassFactory.create(new MainGameClass(), context, 8);
        ClassFactory.create(new SecondGameClass(), context, 8);

    }

    @Override
    public void run() {

        lastUpdateTime = System.nanoTime();

        while (running) {

            currentTime = System.nanoTime();

            float deltaTime = (currentTime - lastUpdateTime) / 1000000000.0f;

            if (currentTime - lastUpdateTime >= state.data().exampleUpdateInterval) {

                // update all updatables
                for (Updatable u : context.getUpdatables()) {
                    u.update(deltaTime);
                }

                lastUpdateTime = currentTime;
            }

            context.endFrame();
            panel.repaint();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
