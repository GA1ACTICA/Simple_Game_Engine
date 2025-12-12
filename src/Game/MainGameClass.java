package Game;

import java.awt.*;
import GameEngine.*;

public class MainGameClass implements Drawable {

    Keys keys;
    GameState gs;

    public MainGameClass(Keys keys, GameState gs) {
        this.keys = keys;
        this.gs = gs;
    }

    @Override
    public void draw(Graphics g) {

        // here you can draw

        Font stringFont = new Font("SansSerif", Font.PLAIN, 50);
        g.setFont(stringFont);

        g.setColor(Color.BLACK);
        g.drawString("Hello", gs.x1 + 300, gs.y1 + 275);

    }

    public void updateGameLogic() {

    }
}