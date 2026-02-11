package GameEngine;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;

import AdvancedRendering.uiRendering.Button.RectButton;
import AdvancedRendering.uiRendering.Misc.FPSCounter;
import AdvancedRendering.uiRendering.Slider.Slider;
import AdvancedRendering.uiRendering.TextField.TextField;
import Game.*;
import Game.Configs.GameState.GameState;
import GameEngine.EngineModules.*;
import GameEngine.Interfaces.Updatable;

public class GameUpdate implements Runnable {

    private boolean running = true;
    private long lastUpdateTime;
    private long currentTime;

    private GameState state;
    private final EnginePanel panel;
    private final EngineContext context;

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

        // constructors for menu
        // constructors for game
        ClassFactory.create(new MainGameClass(), context, 8);
        ClassFactory.create(new SecondGameClass(), context, 8);

        Slider s = new Slider(context, panel, mouse, new Point(100, 100), new Point(900, 900));
        s.show();

        TextField text = new TextField(context, panel, mouse, keys, new Point(100, 100), new Point(900, 150));
        text.setColor(Color.GRAY);
        text.show();

        RectButton b = new RectButton(context, panel, mouse, new Point(500, 500), new Point(550, 550));
        b.show();
        b.setInsideOveride(true);
        // ClassFactory.updatePriority(b, context, 0);

        RectButton b2 = new RectButton(context, panel, mouse, new Point(525, 500), new Point(600, 550));
        b2.show();
        b2.setHoverColor(Color.ORANGE);
        b2.setInsideOveride(true);
        // ClassFactory.updatePriority(b2, context, 1);

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
