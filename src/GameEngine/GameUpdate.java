package GameEngine;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import AdvancedRendering.AdvancedGraphics;
import AdvancedRendering.Menu.GameButtonScaling;
import AdvancedRendering.Menu.MenuContet.GameButton;
import AdvancedRendering.Menu.MenuContet.GameMenu;
import Game.*;

public class GameUpdate implements Runnable {

    private final Mouse mouse;
    private final GameState gs;
    private final GameMenu menu;
    private final GamePanel panel;
    private final MainGameClass mgc;
    private final EngineTools tools;
    private final SecondGameClass sgc;

    public static List<Drawable> drawables = new ArrayList<>();

    boolean running = true;
    long lastUpdateTime;
    long currentTime;

    public GameUpdate(
            Keys keys,
            Mouse mouse,
            GameState gs,
            GamePanel panel,
            JFrame frame,
            GameMenu menu,
            AdvancedGraphics ag,
            GameButton button,
            EngineTools tools) {
        this.gs = gs;
        this.menu = menu;
        this.mouse = mouse;
        this.panel = panel;
        this.tools = tools;
        this.mgc = new MainGameClass(keys, gs, menu, button, tools);
        this.sgc = new SecondGameClass(keys, gs);
    }

    @Override
    public void run() {

        drawables.add(mgc);
        drawables.add(sgc);
        drawables.add(menu);

        lastUpdateTime = System.currentTimeMillis();

        while (running) {

            currentTime = System.currentTimeMillis();

            if (currentTime - lastUpdateTime >= gs.exampleUpdateInterval) {

                // here you can update the game logic

                mgc.updateGameLogic();
                sgc.updateGameLogic();

                lastUpdateTime = currentTime;

                gs.width = panel.getWidth();
                gs.height = panel.getHeight();

                gs.x1 = (gs.width - 800) / 2;
                gs.y1 = (gs.height - 600) / 2;

                mouse.deltaReset();
                tools.updateFPS();
            }

            panel.repaint();
            // firts I put tools.updateFPS(); here

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
