import java.awt.*;

public class SecondGameClass implements Drawable {

    Keys keys;
    GameState gs;

    SecondGameClass(Keys keys) {
        this.keys = keys;
    }

    @Override
    public void draw(Graphics g) {

        // here you can draw

        Font stringFont = new Font("SansSerif", Font.PLAIN, 50);
        g.setFont(stringFont);

        g.setColor(Color.BLACK);
        g.drawString("World!", 200, 350);

    }
}