package GameEngine.EngineModules;

import GameEngine.Interfaces.Clickable;
import GameEngine.Interfaces.Drawables.Drawable;

public class ListEntry {
    public Clickable clickable;
    public Drawable drawable;
    public final int priority;

    public ListEntry(Drawable drawable, int priority) {
        this.drawable = drawable;
        this.priority = priority;
    }

    public ListEntry(Clickable clickable, int priority) {
        this.clickable = clickable;
        this.priority = priority;
    }
}
