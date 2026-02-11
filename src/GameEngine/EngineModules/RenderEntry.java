package GameEngine.EngineModules;

import GameEngine.Interfaces.Drawables.Drawable;

public class RenderEntry {
    public final Drawable drawable;
    public final int priority;

    public RenderEntry(Drawable drawable, int priority) {
        this.drawable = drawable;
        this.priority = priority;
    }
}
