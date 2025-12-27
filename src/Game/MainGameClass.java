package Game;

import java.awt.*;

import javax.swing.JButton;

import AdvancedRendering.Menu.MenuContet.GameButton;
import AdvancedRendering.Menu.MenuContet.GameMenu;
import GameEngine.*;

public class MainGameClass implements Drawable {

    Keys keys;
    GameState gs;
    GameButton button;
    EngineTools tools;

    public MainGameClass(Keys keys, GameState gs, GameMenu menu, GameButton button, EngineTools tools) {
        this.keys = keys;
        this.button = button;
        this.tools = tools;
        this.gs = gs;

        // this is an example on how you can create a menu and button
        JButton test = button.addButton(500, 500, 30, 30, 30, "test");
        button.setButtonColor(test, new Color(255, 0, 0), null);
        menu.show();

        JButton test2 = button.addButton(1500, 300, 100, 30, 30, "test");
        button.setButtonColor(test2, new Color(255, 255, 0), null);
        menu.show();
    }

    @Override
    public void draw(Graphics g) {

        // here you can draw graphics

        Font stringFont = new Font("SansSerif", Font.PLAIN, 75);
        g.setFont(stringFont);

        g.setColor(Color.BLACK);
        g.drawString("Hello", 500, 500);

        tools.drawFPS(g);

    }

    public void updateGameLogic() {

    }
}