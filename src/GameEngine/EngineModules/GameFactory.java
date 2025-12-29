package GameEngine.EngineModules;

import GameEngine.Interfaces.*;

public class GameFactory {
    public static <T> T create(T obj, GameContext ctx) {
        if (obj instanceof Drawable d) {
            if (obj instanceof UIDrawable) {
                ctx.getUiDrawables().add(d);
            } else {
                ctx.getWorldDrawables().add(d);
            }
        }
        if (obj instanceof Updatable u) {
            ctx.getUpdatables().add(u);
        }
        return obj;
    }
}
