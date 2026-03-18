/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the GPL 3.0 License.
 * See LICENSE file in the project root for full license information.
 *
 *Coppyright © 2026 Galactica
 */

package GameEngine.Interfaces.Drawables;

import java.awt.Graphics;

import GameEngine.Interfaces.ZIndexable;

public interface Drawable extends ZIndexable {
    void draw(Graphics g);
}