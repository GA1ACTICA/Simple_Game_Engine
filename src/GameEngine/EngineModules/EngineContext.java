package GameEngine.EngineModules;

import java.util.ArrayList;
import java.util.List;

import GameEngine.Interfaces.Updatable;
import GameEngine.Interfaces.Drawables.Drawable;

public class EngineContext {
    private final List<RenderEntry> worldDrawables = new ArrayList<>();
    private final List<RenderEntry> uiDrawables = new ArrayList<>();

    private final List<Drawable> cursorDrawables = new ArrayList<>();

    private final List<Updatable> updatables = new ArrayList<>();

    public List<RenderEntry> getWorldDrawables() {
        return worldDrawables;
    }

    public List<RenderEntry> getUiDrawables() {
        return uiDrawables;
    }

    public List<Drawable> getCursorDrawables() {
        return cursorDrawables;
    }

    public List<Updatable> getUpdatables() {
        return updatables;
    }

}
