
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
import advancedRendering.uiRendering.misc.FPSCounter;
import advancedRendering.uiRendering.slider.Slider;
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
        FPSCounter fps = new FPSCounter(context);
        fps.setColor(Color.RED);
        fps.show();
        fps.setZIndex(100);

        s = new Slider(context, panel, mouse, new Point(100, 100), new Point(900, 200));
        s.setZIndex(1);
        s.show();

        RectButton b = new RectButton(context, panel, mouse, new Point(0, 0), new Point(200, 200));
        b.show();

        RectButton b2 = new RectButton(context, panel, mouse, new Point(100, 100), new Point(300, 300));
        b2.setHoverColor(Color.BLACK);
        b2.show();

        b2.onClick(() -> {
            s.setSliderPoints(s.getPointOne(), new Point(900, 700), false);
        });

        ClassFactory.create(new MainGameClass(), context, 8);
        ClassFactory.create(new SecondGameClass(), context, 8);

    }

    @Override
    public void run() {

        lastUpdateTime = System.currentTimeMillis();

        while (running) {

            currentTime = System.currentTimeMillis();

            if (currentTime - lastUpdateTime >= state.data().exampleUpdateInterval) {

                // update all updatables
                for (Updatable u : context.getUpdatables()) {
                    u.update();
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
