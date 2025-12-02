import java.awt.*;
import javax.swing.*;

public class Game extends Canvas {

    // window rules
    static int width = 800;
    static int height = 600;

    // Main Method
    public static void main(String args[]) {

        JFrame frame = new JFrame("Game_Title");
        Game canvas = new Game();
        GameState gs = new GameState();
        Keys keys = new Keys();

        canvas.addKeyListener(keys);
        canvas.setFocusable(true);
        canvas.requestFocus();

        frame.add(canvas);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setBackground(Color.WHITE);

        GameUpdate gu = new GameUpdate(canvas, keys, gs);
        gu.init();

        Thread thread = new Thread(gu);
        thread.start();
    }
}