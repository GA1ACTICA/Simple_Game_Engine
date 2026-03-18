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

package GameEngine.Interfaces;

public interface Clickable extends ZIndexable {

    void onClick(Runnable action);

    void executeOnClick();

    boolean contains(int mouseX, int mouseY);

    boolean getDisabled();

    boolean getVisible();
}
