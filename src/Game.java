import java.awt.Dimension;
import javax.swing.*;

import AdvancedRendering.uiRendering.Menu.GameMenu;
import AdvancedRendering.worldRendering.AdvancedGraphics;
import Game.GameState;
import GameEngine.GameUpdate;
import GameEngine.EngineModules.*;

public class Game {

    static final GameState state = new GameState();

    static final EnginePanel panel = new EnginePanel(state);
    static final JFrame frame = new JFrame("Game_Title");
    static final EngineContext context = new EngineContext();

    static final Keys keys = new Keys(state);
    static final Mouse mouse = new Mouse(state, context);
    static final GameMenu menu = new GameMenu(state, panel);
    static final EngineTools tools = new EngineTools(state);
    static final AdvancedGraphics advanced = new AdvancedGraphics(state);

    static final GameUpdate gu = new GameUpdate(keys,
            mouse,
            state,
            panel,
            frame,
            menu,
            advanced,
            tools,
            context);

    public static void main(String[] args) {

        // PANEL setup
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(state.width, state.height));
        panel.setBackground(state.backgroundColor);
        panel.setFocusable(true);

        // Add listeners
        panel.addKeyListener(keys);
        panel.addMouseListener(mouse);
        panel.addMouseMotionListener(mouse);
        panel.addMouseWheelListener(mouse);

        // FRAME setup
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new Thread(gu).start();
    }
}