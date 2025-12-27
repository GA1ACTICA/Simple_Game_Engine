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

        // here you can draw graphics

        Font stringFont = new Font("SansSerif", Font.PLAIN, 75);
        g.setFont(stringFont);

        g.setColor(Color.BLACK);
        g.drawString("World!", 500, 575);

    }

    public void updateGameLogic() {

    }

}