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

package GameEngine.Interfaces.Drawables;

import GameEngine.Interfaces.ZIndexable;

/**
 * Represents a drawable UI element in the engine.
 * <p>
 * Implementations are rendered using a graphics context that is pre-configured
 * by the engine. The context is centered within the window and may be scaled.
 * <p>
 * Draw order is determined by the {@link ZIndexable} interface.
 */
public interface UIDrawable extends Drawable {
}
