/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the GPL 3.0 License.
 * See LICENSE file in the project root for full license information.

 Coppyright © 2026 Galactica
 */

package GameEngine.EngineModules;

import java.util.List;

import GameEngine.Interfaces.*;
import GameEngine.Interfaces.Drawables.CursorDrawable;
import GameEngine.Interfaces.Drawables.Drawable;
import GameEngine.Interfaces.Drawables.UIDrawable;
import Utils.ErrorManagement;

public class ClassFactory {

    public static Object create(Object object, EngineContext context) {
        return create(object, context, -1);
    }

    // TODO: seperate adding to list into seperate method for better docstring

    public static Object create(Object object, EngineContext context, int zIndex) {

        if (object instanceof Drawable drawable) {

            List<Drawable> list;

            if (object instanceof UIDrawable) {
                list = context.getUiDrawables();
            } else {
                list = context.getWorldDrawables();
            }

            int index = 0;
            while (index < list.size() &&
                    list.get(index).getZIndex() <= zIndex) {
                index++;
            }

            // Add sorted entry
            list.add(index, drawable);
        }

        if (object instanceof Updatable updatable) {
            context.getUpdatables().add(updatable);
        }

        if (object instanceof CursorDrawable cursorDrawable) {
            context.getCursorDrawables().add(cursorDrawable);
        }

        if (object instanceof Clickable clickable) {
            List<Clickable> list = context.getClickables();

            // Find the appropriate index for insertion (Descending order)
            int index = 0;
            while (index < list.size() &&
                    list.get(index).getZIndex() > zIndex) {
                index++;
            }

            list.add(index, clickable);
        }

        return object;
    }

    public static Object updatePriority(Object object, EngineContext context, int zIndex) {

        if (object instanceof Drawable drawable) {

            List<Drawable> list;

            if (object instanceof UIDrawable) {
                list = context.getUiDrawables();
            } else {
                list = context.getWorldDrawables();
            }

            // Remove old entry
            list.removeIf(entry -> entry == drawable);

            int index = 0;
            while (index < list.size() &&
                    list.get(index).getZIndex() <= zIndex) {
                index++;
            }

            // Add sorted entry
            list.add(index, drawable);
        }

        if (object instanceof Clickable clickable) {

            List<Clickable> list;

            list = context.getClickables();

            // Remove old entry
            list.removeIf(entry -> entry == clickable);

            // Find the appropriate index for insertion (Descending order)
            int index = 0;
            while (index < list.size() && list.get(index).getZIndex() > zIndex) {
                index++;
            }

            // Add sorted entry
            list.add(index, clickable);
        }

        return object;
    }

    public static int getPriority(Object object) throws Exception {
        if (object instanceof ZIndexable zIndexable)
            return zIndexable.getZIndex();

        Exception e = new IllegalArgumentException(
                object.getClass().getSimpleName() + " must implement ZIndexable");

        ErrorManagement.reportError(e, "Inavlid object passed to getPriority");
        throw e;
    }
}
