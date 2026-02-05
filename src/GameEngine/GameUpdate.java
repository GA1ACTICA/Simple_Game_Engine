package GameEngine;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;

import AdvancedRendering.uiRendering.Button.RectButton;
import AdvancedRendering.uiRendering.Menu.GameMenu;
import AdvancedRendering.uiRendering.Misc.FPSCounter;
import Game.*;
import Game.Configs.GameState.GameState;
import Game.Configs.GameState.GameStateData;
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
        ClassFactory.create(new MainGameClass(), context);
        ClassFactory.create(new SecondGameClass(), context);

        RectButton b = new RectButton(mouse, context, new Point(100, 100), new Point(200, 200));

        GameMenu menu = new GameMenu();
        menu.add(b);
        menu.show();

        b.onClick(() -> {
            state.exportJSON(new GameStateData(), "conf.json");
            menu.hide();
            // state.importJSON(GameStateData.class, "conf.json");
        });

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
