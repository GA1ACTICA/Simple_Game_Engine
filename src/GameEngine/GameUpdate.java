package GameEngine;

import javax.swing.JFrame;

import AdvancedRendering.uiRendering.Button.OvalButton;
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
    private final EnginePanel panel;
    private final EngineContext context;

    public GameUpdate(
            Keys keys,
            Mouse mouse,
            GameState state,
            EnginePanel panel,
            JFrame frame,
            GameMenu menu,
            AdvancedGraphics advanced,
            EngineTools tools,
            EngineContext context) {
        this.state = state;
        this.panel = panel;
        this.context = context;

        panel.setGameContex(context);

        // constructors from engine
        FPSCounter fps = new FPSCounter(context);

        // constructors for menu
        RectButton b = new OvalButton(mouse, context, 50, 50, 200, 200);

        b.setImage(tools.getImage("test.png"));
        b.setHoverImage(tools.getImage("test2.png"));

        b.onClick(() -> {
            System.out.println("test");
        });

        b.show();

        // constructors for game
        ClassFactory.create(new MainGameClass(state, menu, fps, advanced), context);
        ClassFactory.create(new SecondGameClass(state, advanced), context);

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
