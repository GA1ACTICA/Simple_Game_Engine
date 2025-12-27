package GameEngine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class EngineTools {

    private long lastTime = System.nanoTime();
    private int frames = 0;
    private int fps = 0;
    private long timer = System.currentTimeMillis();

    private final GameState state;
    private GameUpdate gu;

    public EngineTools(GameState state) {
        this.state = state;
    }

    public void setGameUpdate(GameUpdate gu) {
        this.gu = gu;
    }

    public void updateFPS() {
        frames++;

        if (System.currentTimeMillis() - timer >= 1000) {
            fps = frames;
            frames = 0;
            timer += 1000;
        }
    }

    public void drawFPS(Graphics g) {
        Font font = new Font("Arial", Font.PLAIN, 25);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString("FPS/UPS: " + Integer.toString(fps), 10, 20);
    }

    public void drawFPS(Graphics g, int x, int y, Font font, Color color) {
        g.setFont(font);
        g.setColor(color);
        g.drawString("FPS/UPS: " + Integer.toString(fps), x, y);
    }

    public Image getImage(String filePathFromProject) {
        Image image = new ImageIcon(getClass().getResource("../" + filePathFromProject)).getImage();
        return image;
    }

}
