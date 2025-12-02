import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class GameUpdate implements Runnable {

    BufferStrategy backBuffer;
    Graphics2D g;
    Canvas c;
    MainGameClass mgc;
    SecondGameClass sgc;
    Keys keys;
    GameState gs;

    public GameUpdate(Canvas c, Keys keys, GameState gs) {
        this.c = c;
        this.keys = keys;
        this.gs = gs;
        this.mgc = new MainGameClass(keys);
        this.sgc = new SecondGameClass(keys);
        this.gs = new GameState();
    }

    private List<Drawable> drawables = new ArrayList<>();
    boolean running = true;

    public void init() {

        c.createBufferStrategy(2);
        backBuffer = c.getBufferStrategy();

        if (backBuffer == null) {
            throw new IllegalStateException("BufferStrategy falied to initialize");
        }

        drawables.add(mgc);
        drawables.add(sgc);
    }

    @Override
    public void run() {

        while (running) {

            render();

            try {
                Thread.sleep(16, 667);// around 60 FPS

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void render() {

        g = (Graphics2D) backBuffer.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, c.getWidth(), c.getHeight());

        for (Drawable d : drawables) {
            d.draw(g);
        }

        g.dispose();
        backBuffer.show();
        Toolkit.getDefaultToolkit().sync(); // prevents tearing on Linux
    }
}
