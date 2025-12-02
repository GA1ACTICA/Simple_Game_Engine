import java.awt.*;

public class MainGameClass implements Drawable {

    Keys keys;
    GameState gs;

    MainGameClass(Keys keys) {
        this.keys = keys;
    }

    @Override
    public void draw(Graphics g) {

        // here you can draw

        Font stringFont = new Font("SansSerif", Font.PLAIN, 50);
        g.setFont(stringFont);

        g.setColor(Color.BLACK);
        g.drawString("Hello", 200, 300);

    }
}