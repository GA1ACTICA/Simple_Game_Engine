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

package GameEngine.EngineModules;

import java.util.ArrayList;
import java.util.List;

import GameEngine.Interfaces.Clickable;
import GameEngine.Interfaces.Updatable;
import GameEngine.Interfaces.Drawables.CursorDrawable;
import GameEngine.Interfaces.Drawables.Drawable;

public class EngineContext {
    private final List<Drawable> worldDrawables = new ArrayList<>();
    private final List<Drawable> uiDrawables = new ArrayList<>();

    private final List<CursorDrawable> cursorDrawables = new ArrayList<>();

    private final List<Updatable> updatables = new ArrayList<>();

    private final List<Clickable> clickables = new ArrayList<>();

    public List<Drawable> getWorldDrawables() {
        return worldDrawables;
    }

    public List<Drawable> getUiDrawables() {
        return uiDrawables;
    }

    public List<CursorDrawable> getCursorDrawables() {
        return cursorDrawables;
    }

    public List<Updatable> getUpdatables() {
        return updatables;
    }

    public List<Clickable> getClickables() {
        return clickables;
    }

}
