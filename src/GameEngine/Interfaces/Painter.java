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

package GameEngine.Interfaces;

import java.awt.Graphics2D;

@FunctionalInterface
public interface Painter {
    void paint(Graphics2D g);
}
