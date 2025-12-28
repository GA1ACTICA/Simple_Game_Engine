package GameEngine;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import AdvancedRendering.uiRendering.Menu.GameMenu;
import AdvancedRendering.uiRendering.Misc.FPSCounter;
import AdvancedRendering.worldRendering.AdvancedGraphics;
import Game.*;
import GameEngine.Interfaces.Drawable;
import GameEngine.Interfaces.Updatable;

public class GameUpdate implements Runnable {

    private final Mouse mouse;
    private final GameState state;
    private final GameMenu menu;
    private final GamePanel panel;
    private final MainGameClass mgc;
    @SuppressWarnings("unused")
    private final EngineTools tools;
    private final SecondGameClass sgc;
    private final FPSCounter fps;

    // rendering lists
    public static List<Drawable> worldDrawables = new ArrayList<>();
    public static List<Drawable> uiDrawables = new ArrayList<>();

    // update list
    List<Updatable> updatables = new ArrayList<>();

    boolean running = true;
    long lastUpdateTime;
    long currentTime;

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
        this.menu = menu;
        this.mouse = mouse;
        this.panel = panel;
        this.tools = tools;
        this.fps = new FPSCounter();
        this.mgc = new MainGameClass(state, menu, fps, advanced);
        this.sgc = new SecondGameClass(state, advanced);
    }

    @Override
    public void run() {

        // worldDrawables list
        worldDrawables.add(mgc);
        worldDrawables.add(sgc);
        worldDrawables.add(menu);

        // uiDrawables list
        uiDrawables.add(fps);

        // updatables list
        updatables.add(mgc);
        updatables.add(sgc);
        updatables.add(fps);
        updatables.add(mouse);

        lastUpdateTime = System.currentTimeMillis();

        while (running) {

            currentTime = System.currentTimeMillis();

            if (currentTime - lastUpdateTime >= state.exampleUpdateInterval) {

                // update all updatables
                for (Updatable u : updatables) {
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
