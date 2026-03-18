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

import GameEngine.Interfaces.Drawables.Drawable;

public class RenderEntry {
    public final Drawable drawable;
    public final int priority;

    public RenderEntry(Drawable drawable, int priority) {
        this.drawable = drawable;
        this.priority = priority;
    }
}
