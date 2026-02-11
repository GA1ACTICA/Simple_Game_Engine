package GameEngine.EngineModules;

import java.util.List;

import GameEngine.Interfaces.*;
import GameEngine.Interfaces.Drawables.CursorDrawable;
import GameEngine.Interfaces.Drawables.Drawable;
import GameEngine.Interfaces.Drawables.UIDrawable;

public class ClassFactory {

    public static <T> T create(T object, EngineContext context) {
        return create(object, context, -1);
    }

    public static <T> T create(T object, EngineContext context, int renderPriority) {

        if (object instanceof Drawable d) {

            List<RenderEntry> list;

            if (object instanceof UIDrawable) {
                list = context.getUiDrawables();
            } else {
                list = context.getWorldDrawables();
            }

            RenderEntry entry = new RenderEntry(d, renderPriority);

            int index = 0;
            while (index < list.size() &&
                    list.get(index).priority <= renderPriority) {
                index++;
            }

            // Add sorted entry
            list.add(index, entry);
        }

        if (object instanceof Updatable u) {
            context.getUpdatables().add(u);
        }

        if (object instanceof CursorDrawable u) {
            context.getCursorDrawables().add(u);
        }

        return object;
    }

    public static <T> T updatePriority(T object, EngineContext context, int renderPriority) {

        if (object instanceof Drawable d) {

            List<RenderEntry> list;

            if (object instanceof UIDrawable) {
                list = context.getUiDrawables();
            } else {
                list = context.getWorldDrawables();
            }

            // Remove old entry
            list.removeIf(entry -> entry.drawable == d);

            RenderEntry newEntry = new RenderEntry(d, renderPriority);

            int index = 0;
            while (index < list.size() &&
                    list.get(index).priority <= renderPriority) {
                index++;
            }

            // Add sorted entry
            list.add(index, newEntry);
        }

        return object;
    }
}
