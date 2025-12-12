package GameEngine;

import java.util.ArrayList;
import java.util.List;

import Game.*;

public class GameUpdate implements Runnable {

    @SuppressWarnings("unused")
    private final Keys keys; // if you want to have logic for menus or other classes in the loop below you
                             // also have acces to key inputs eventhough they are not used in this example
    private final GameState gs;
    private final GamePanel panel;
    private final MainGameClass mgc;
    private final SecondGameClass sgc;

    public static List<Drawable> drawables = new ArrayList<>();

    boolean running = true;
    long lastUpdateTime;
    long currentTime;

    public GameUpdate(Keys keys, GameState gs, GamePanel panel) {
        this.gs = gs;
        this.keys = keys;
        this.panel = panel;
        this.mgc = new MainGameClass(keys, gs);
        this.sgc = new SecondGameClass(keys, gs);
    }

    @Override
    public void run() {

        drawables.add(mgc);
        drawables.add(sgc);

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
            }

            panel.repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
