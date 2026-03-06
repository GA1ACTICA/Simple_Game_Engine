package GameEngine.EngineModules;

import java.util.ArrayList;
import java.util.List;

import GameEngine.Interfaces.Clickable;
import GameEngine.Interfaces.Updatable;
import GameEngine.Interfaces.Drawables.Drawable;

public class EngineContext {
    private final List<ListEntry> worldDrawables = new ArrayList<>();
    private final List<ListEntry> uiDrawables = new ArrayList<>();

    private final List<Drawable> cursorDrawables = new ArrayList<>();

    private final List<Updatable> updatables = new ArrayList<>();

    private final List<ListEntry> clickables = new ArrayList<>();

    public List<ListEntry> getWorldDrawables() {
        return worldDrawables;
    }

    public List<ListEntry> getUiDrawables() {
        return uiDrawables;
    }

    public List<Drawable> getCursorDrawables() {
        return cursorDrawables;
    }

    public List<Updatable> getUpdatables() {
        return updatables;
    }

    public List<ListEntry> getClickables() {
        return clickables;
    }

}
