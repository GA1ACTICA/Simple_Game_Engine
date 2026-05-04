/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 *
 * Copyright © 2026 Galactica
 */

package GameEngine.EngineModules;

import java.util.ArrayList;
import java.util.List;

import GameEngine.Interfaces.Hoverable;
import GameEngine.Interfaces.Updatable;
import GameEngine.Interfaces.Drawables.*;

public class EngineContext {

    private final List<Drawable> backBufferDrawable = new ArrayList<>();
    private volatile List<Drawable> frontBufferDrawable = new ArrayList<>();

    private final List<Drawable> backBufferUIDrawable = new ArrayList<>();
    private volatile List<Drawable> frontBufferUIDrawable = new ArrayList<>();

    private final List<CursorDrawable> backBufferCursorDrawable = new ArrayList<>();
    private volatile List<CursorDrawable> frontBufferCursorDrawable = new ArrayList<>();

    private final List<Updatable> backBufferUpdatables = new ArrayList<>();
    private volatile List<Updatable> frontBufferUpdatables = new ArrayList<>();

    private final List<Hoverable> backBufferHoverables = new ArrayList<>();
    private volatile List<Hoverable> frontBufferHoverables = new ArrayList<>();

    private List<List<?>> allLists = List.of(
            backBufferDrawable,
            backBufferUIDrawable,
            backBufferCursorDrawable,
            backBufferUpdatables,
            backBufferHoverables);

    List<List<?>> getAllLists() {
        return allLists;
    }

    /**
     * Returns the internal list of objects registered with the game engine during
     * the current frame that also implement {@link Drawable}.
     *
     * @return the mutable list of drawable objects
     */
    public List<Drawable> getWorldDrawables() {
        return frontBufferDrawable;
    }

    /**
     * Returns the internal list of objects registered with the game engine during
     * the current frame that also implement {@link UIDrawable}.
     *
     * @return the mutable list of drawable objects
     */
    public List<Drawable> getUiDrawables() {
        return frontBufferUIDrawable;
    }

    /**
     * Returns the internal list of objects registered with the game engine during
     * the current frame that also implement {@link CursorDrawable}.
     *
     * @return the mutable list of drawable objects
     */
    public List<CursorDrawable> getCursorDrawables() {
        return frontBufferCursorDrawable;
    }

    /**
     * Returns the internal list of objects registered with the game engine during
     * the current frame that also implement {@link Updatable}.
     *
     * @return the mutable list of updatable objects
     */
    public List<Updatable> getUpdatables() {
        return frontBufferUpdatables;
    }

    /**
     * Returns the internal list of objects registered with the game engine during
     * the current frame that also implement {@link Hoverable}.
     *
     * @return the mutable list of hoverable objects
     */
    public List<Hoverable> getHoverables() {
        return frontBufferHoverables;
    }

    List<Drawable> getBackBufferDrawable() {
        return backBufferDrawable;
    }

    List<Drawable> getBackBufferUIDrawable() {
        return backBufferUIDrawable;
    }

    List<CursorDrawable> getBackBufferCursorDrawable() {
        return backBufferCursorDrawable;
    }

    List<Updatable> getBackBufferUpdatables() {
        return backBufferUpdatables;
    }

    List<Hoverable> getBackBufferHoverables() {
        return backBufferHoverables;
    }

    void endFrame() {
        frontBufferDrawable = List.copyOf(backBufferDrawable);
        frontBufferUIDrawable = List.copyOf(backBufferUIDrawable);
        frontBufferCursorDrawable = List.copyOf(backBufferCursorDrawable);
        frontBufferUpdatables = List.copyOf(backBufferUpdatables);
        frontBufferHoverables = List.copyOf(backBufferHoverables);

        // backBufferDrawable.clear();
        // backBufferCursorDrawable.clear();
        // backBufferUIDrawable.clear();
    }

}
