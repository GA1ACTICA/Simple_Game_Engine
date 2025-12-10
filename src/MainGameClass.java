import java.awt.*;

public class MainGameClass implements Drawable {

    Keys keys;
    GameState gs;

    MainGameClass(Keys keys, GameState gs) {
        this.keys = keys;
        this.gs = gs;
    }

    @Override
    public void draw(Graphics g) {

        // here you can draw

        Font stringFont = new Font("SansSerif", Font.PLAIN, 50);
        g.setFont(stringFont);

        g.setColor(Color.BLACK);
        g.drawString("Hello", 200, 300);

    }

    public void updateGameLogic() {
        int i = 0;
        i++;
        System.out.println(i);
    }
}