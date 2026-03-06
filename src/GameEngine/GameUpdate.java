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

        RectButton b = new RectButton(context, panel, mouse, new Point(500, 500), new Point(700, 700));
        b.show();
        b.setInsideOveride(false);
        b.onClick(() -> {
            System.out.println("testing");
        });
        b.setZIndex(50);

        RectButton b2 = new RectButton(context, panel, mouse, new Point(300, 500), new Point(550, 750));
        b2.show();
        b2.setColor(Color.MAGENTA);
        b2.setInsideOveride(false);
        b2.onClick(() -> {
            System.out.println("testing2");
        });
        b2.setZIndex(51);

        RectButton b3 = new RectButton(context, panel, mouse, new Point(0, 0), new Point(250, 250));
        b3.show();
        b3.setColor(Color.MAGENTA);
        b3.setInsideOveride(false);
        b3.onClick(() -> {
            System.out.println("testing3");
        });
        b3.setZIndex(49);

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
