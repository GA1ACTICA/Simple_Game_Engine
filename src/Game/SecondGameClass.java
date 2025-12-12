package Game;

import java.awt.*;

import GameEngine.Drawable;
import GameEngine.GameState;
import GameEngine.Keys;

public class SecondGameClass implements Drawable {

    Keys keys;
    GameState gs;

    public SecondGameClass(Keys keys, GameState gs) {
        this.keys = keys;
        this.gs = gs;

    }

    @Override
    public void draw(Graphics g) {

        // here you can draw

        Font stringFont = new Font("SansSerif", Font.PLAIN, 50);
        g.setFont(stringFont);

        g.setColor(Color.BLACK);
        g.drawString("World!", gs.x1 + 300, gs.y1 + 325);

    }

    public void updateGameLogic() {

    }

}