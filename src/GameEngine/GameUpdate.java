package GameEngine;

import java.awt.Point;

import javax.swing.JFrame;

import AdvancedRendering.uiRendering.Button.RectButton;
import AdvancedRendering.uiRendering.Menu.GameMenu;
import AdvancedRendering.uiRendering.Misc.FPSCounter;
import AdvancedRendering.uiRendering.Slider.Slider;
import AdvancedRendering.worldRendering.AdvancedGraphics;
import Game.*;
import GameEngine.EngineModules.*;
import GameEngine.Interfaces.Updatable;

public class GameUpdate implements Runnable {

    private boolean running = true;
    private long lastUpdateTime;
    private long currentTime;

    private final GameState state;
    private final EnginePanel panel;
    private final EngineContext context;

    public GameUpdate(
            Keys keys,
            Mouse mouse,
            GameState state,
            EnginePanel panel,
            JFrame frame,
            GameMenu menu,
            AdvancedGraphics advanced,
            EngineTools tools,
            EngineContext context) {
        this.state = state;
        this.panel = panel;
        this.context = context;

        panel.setGameContex(context);

        // constructors from engine
        FPSCounter fps = new FPSCounter(context);

        // constructors for menu
        Slider slider = new Slider(mouse, context, new Point(100, 100), new Point(200, 300));
        slider.show();
        slider.setHandleAngle(90);
        slider.setSliderMax(1024);

        RectButton button = new RectButton(mouse, context, new Point(450, 450), new Point(500, 500));
        button.show();
        button.onClick(() -> {
            slider.setSliderPoints(new Point(100, 100), new Point(300, 300));
            System.out.println(slider.getSliderValue());
        });

        // constructors for game
        ClassFactory.create(new MainGameClass(state, menu, fps, advanced), context);
        ClassFactory.create(new SecondGameClass(state, advanced), context);

    }

    @Override
    public void run() {

        lastUpdateTime = System.currentTimeMillis();

        while (running) {

            currentTime = System.currentTimeMillis();

            if (currentTime - lastUpdateTime >= state.exampleUpdateInterval) {

                // update all updatables
                for (Updatable u : context.getUpdatables()) {
                    u.update();
                }

                lastUpdateTime = currentTime;
            }

            panel.repaint();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
