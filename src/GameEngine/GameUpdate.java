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

package GameEngine;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;

import AdvancedRendering.uiRendering.Button.RectButton;
import AdvancedRendering.uiRendering.CheckBox.RectCheckbox;
import AdvancedRendering.uiRendering.Misc.FPSCounter;
import AdvancedRendering.uiRendering.Slider.Slider;
import Game.*;
import Game.Configs.GameState.GameState;
import GameEngine.EngineModules.*;
import GameEngine.EngineModules.CursorManager.CursorType;
import GameEngine.Interfaces.Updatable;

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

        s = new Slider(context, panel, mouse, new Point(0, 0), new Point(1000, 500));
        s.show();
        s.setSliderMax(104563);

        RectButton b1 = new RectButton(context, panel, mouse, new Point(475, 525), new Point(600, 600));
        b1.show();

        RectCheckbox b = new RectCheckbox(context, panel, mouse, new Point(450, 500), new Point(550, 550));
        b.setColor(Color.BLACK);
        b.show();

        b.onClick(() -> {
            System.out.println("black");
        });

        b1.onClick(() -> {
            System.out.println("green");

        });

        // constructors for menu
        // constructors for game
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

            panel.repaint();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
