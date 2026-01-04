package GameEngine.EngineModules;

import GameEngine.Interfaces.*;

public class ClassFactory {
    public static <T> T create(T obj, EngineContext ctx) {
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
