package GameEngine.EngineModules;

import java.util.List;

import GameEngine.Interfaces.*;
import GameEngine.Interfaces.Drawables.CursorDrawable;
import GameEngine.Interfaces.Drawables.Drawable;
import GameEngine.Interfaces.Drawables.UIDrawable;

public class ClassFactory {

    public static Object create(Object object, EngineContext context) {
        return create(object, context, -1);
    }

    // TODO: seperate adding to list into seperate method for better docstring

    public static Object create(Object object, EngineContext context, int renderPriority) {

        if (object instanceof Drawable d) {

            List<ListEntry> list;

            if (object instanceof UIDrawable) {
                list = context.getUiDrawables();
            } else {
                list = context.getWorldDrawables();
            }

            ListEntry entry = new ListEntry(d, renderPriority);

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

        if (object instanceof Clickable c) {
            List<ListEntry> list = context.getClickables();

            ListEntry entry = new ListEntry(c, renderPriority);

            // Find the appropriate index for insertion (Descending order)
            int index = 0;
            while (index < list.size() && list.get(index).priority > renderPriority) {
                index++;
            }

            list.add(index, entry);
        }

        return object;
    }

    public static Object updatePriority(Object object, EngineContext context, int renderPriority) {

        if (object instanceof Drawable d) {

            List<ListEntry> list;

            if (object instanceof UIDrawable) {
                list = context.getUiDrawables();
            } else {
                list = context.getWorldDrawables();
            }

            // Remove old entry
            list.removeIf(entry -> entry.drawable == d);

            ListEntry newEntry = new ListEntry(d, renderPriority);

            int index = 0;
            while (index < list.size() &&
                    list.get(index).priority <= renderPriority) {
                index++;
            }

            // Add sorted entry
            list.add(index, newEntry);
        }

        if (object instanceof Clickable c) {

            List<ListEntry> list;

            list = context.getClickables();

            // Remove old entry
            list.removeIf(entry -> entry.clickable == c);

            ListEntry newEntry = new ListEntry(c, renderPriority);

            // Find the appropriate index for insertion (Descending order)
            int index = 0;
            while (index < list.size() && list.get(index).priority > renderPriority) {
                index++;
            }

            // Add sorted entry
            list.add(index, newEntry);
        }

        return object;
    }

    public static int getPriority(Object object) {

        int priority = 0;
        return priority;
    }
}
