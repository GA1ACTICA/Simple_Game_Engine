import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameUpdate implements Runnable {

    Keys keys;
    GameState gs;
    Graphics2D g;
    GamePanel panel;
    MainGameClass mgc;
    SecondGameClass sgc;

    public static List<Drawable> drawables = new ArrayList<>();

    boolean running = true;
    long lastUpdateTime;
    long currentTime;

    public GameUpdate(Keys keys, GameState gs, GamePanel panel) {
        this.gs = gs;
        this.keys = keys;
        this.panel = panel;
        this.gs = new GameState();
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
