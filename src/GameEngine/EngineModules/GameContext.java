package GameEngine.EngineModules;

import java.util.ArrayList;
import java.util.List;

import GameEngine.Interfaces.Drawable;
import GameEngine.Interfaces.Updatable;

public class GameContext {
    private final List<Drawable> worldDrawables = new ArrayList<>();
    private final List<Updatable> updatables = new ArrayList<>();
    private final List<Drawable> uiDrawables = new ArrayList<>();

    public List<Drawable> getWorldDrawables() {
        return worldDrawables;
    }

    public List<Updatable> getUpdatables() {
        return updatables;
    }

    public List<Drawable> getUiDrawables() {
        return uiDrawables;
    }
}
