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
import AdvancedRendering.uiRendering.MenuContainer.GameMenu;

/**
 * Functional interface representing a custom drawing action.
 * <p>
 * This interface allows predefining rendering logic that can be executed
 * during a component's draw phase.
 * <p>
 * Implementations receive a {@link Graphics2D} context and may perform
 * arbitrary drawing operations using it.
 *
 * <p>
 * Example usage from {@link GameMenu}:
 * </p>
 *
 * <pre>{@code
 * private Painter customDrawAction = (g) -> {
 *     g.setColor(new Color(10, 10, 10, 125));
 *     g.fillRect(x, y, width, height);
 *
 *     g.setColor(Color.BLACK);
 *     g.setFont(new Font("SansSerif", Font.PLAIN, 25));
 *     AdvancedGraphics.centerAlignedString(
 *             g,
 *             x + width / 2,
 *             (int) (y + height * 0.2),
 *             "This is a menu");
 * };
 *
 * public void draw(Graphics g) {
 *     if (!show)
 *         return;
 *
 *     customDrawAction.paint((Graphics2D) g);
 * }
 * }</pre>
 */
@FunctionalInterface
public interface Painter {
    void paint(Graphics2D g);
}
