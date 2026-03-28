
/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the GPL 3.0 License.
 * See LICENSE file in the project root for full license information.
 *
 * Copyright © 2026 Galactica
 */

import java.awt.Dimension;
import javax.swing.*;

import Game.Configs.GameState.GameState;
import GameEngine.GameUpdate;
import GameEngine.EngineModules.*;

public class Game {

    static final GameState state = new GameState();

    static final EngineContext context = new EngineContext();
    static final EnginePanel panel = new EnginePanel(state, context);
    static final JFrame frame = new JFrame("Game_Title");

    static final Keys keys = new Keys(state);
    static final Mouse mouse = new Mouse(state, context, panel);

    static final CursorManager cursor = new CursorManager(context, panel, mouse);

    static final GameUpdate gu = new GameUpdate(keys,
            mouse,
            state,
            panel,
            frame,
            context);

    public static void main(String[] args) {

        // PANEL setup
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(state.data().width, state.data().height));
        panel.setBackground(state.data().backgroundColor);
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

        // sets objects in classes that need them
        MouseManager.setObjects(state);

        new Thread(gu).start();
    }
}