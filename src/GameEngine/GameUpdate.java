package GameEngine;

import javax.swing.JFrame;

import AdvancedRendering.uiRendering.Button.RectButton;
import AdvancedRendering.uiRendering.Menu.GameMenu;
import AdvancedRendering.uiRendering.Misc.FPSCounter;
import AdvancedRendering.worldRendering.AdvancedGraphics;
import Game.*;
import GameEngine.EngineModules.*;
import GameEngine.Interfaces.Updatable;

public class GameUpdate implements Runnable {

    private boolean running = true;
    private long lastUpdateTime;
    private long currentTime;

    private final GameState state;
    private final GamePanel panel;
    private final GameContext context;

    public GameUpdate(
            Keys keys,
            Mouse mouse,
            GameState state,
            GamePanel panel,
            JFrame frame,
            GameMenu menu,
            AdvancedGraphics advanced,
            EngineTools tools) {
        this.state = state;
        this.panel = panel;

        this.context = new GameContext();
        panel.setGameContex(context);

        // constructors from engine
        GameFactory.create(mouse, context);
        FPSCounter fps = GameFactory.create(new FPSCounter(), context);

        // constructors for menu
        RectButton b = new RectButton(mouse, 50, 50, 50, 50);
        GameFactory.create(b, context);

        b.onClick(() -> {
            System.out.println("test");
        });

        b.show();

        // constructors for game
        GameFactory.create(new MainGameClass(state, menu, fps, advanced), context);
        GameFactory.create(new SecondGameClass(state, advanced), context);

    }

    @Override
    public void run() {

        lastUpdateTime = System.currentTimeMillis();

        while (running) {

            currentTime = System.currentTimeMillis();

            if (currentTime - lastUpdateTime >= state.exampleUpdateInterval) {

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
