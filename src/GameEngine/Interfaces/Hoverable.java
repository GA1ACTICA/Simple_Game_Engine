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

/**
 * Represents an element that can be detected by the mouse cursor.
 * <p>
 * The engine uses this interface to determine which element is currently
 * under the mouse pointer. Hover state is managed by the engine and should
 * not be modified directly by implementations unless explicitly required.
 * <p>
 * Implementations must define their own hitbox via {@link #contains(int, int)}.
 * <p>
 * Rendering order and hover priority are determined by the {@link ZIndexable}
 * interface.
 */
public interface Hoverable extends ZIndexable {

    boolean isHovered();

    /**
     * Sets the hovered state of this element.
     * <p>
     * This method is typically called by the engine during mouse processing.
     *
     * @param isHovered {@code true} if the element is hovered
     */
    void setHovered(boolean isHovered);

    /**
     * Determines whether the given mouse coordinates are within this element.
     *
     * @param mouseX the mouse x-coordinate
     * 
     * @param mouseY the mouse y-coordinate
     * 
     * @return {@code true} if the point is inside this element
     */
    boolean contains(int mouseX, int mouseY);

    boolean isVisible();
}
