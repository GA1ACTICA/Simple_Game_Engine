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

public interface Clickable extends ZIndexable {

    /**
     * Sets the action to be executed when the clickable object is clicked.
     *
     * <p>
     * The provided {@link Runnable} will be invoked when the clickable object is
     * clicked or
     * the method {@link #executeOnClick()} is called.
     * </p>
     *
     * <p>
     * Example usage:
     * 
     * <pre>
     * clickableObject.onClick(() -> {
     *     System.out.println("Object clicked!");
     * });
     * </pre>
     *
     * @param action the action to execute on click
     */
    void onClick(Runnable action);

    /**
     * Executes the action set via {@link #onClick(Runnable)}.
     *
     * <p>
     * This is typically called by the input handling system when a click
     * interaction is completed, but can also be invoked manually to simulate
     * a click.
     * </p>
     */
    void executeOnClick();

    /**
     * Called when a mouse press is detected on this clickable component.
     * <p>
     * This method is invoked by the input handling system and should not
     * be called directly by user code.
     */
    void onPressed();

    /**
     * Called when a mouse release is detected on this clickable component.
     * <p>
     * This method is invoked by the input handling system and should not
     * be called directly by user code.
     */
    void onReleased();

    /**
     * Checks whether the specified point is within this clickable component.
     *
     * <p>
     * <b>Note:</b> This method does not concern itself if other objects overlap.
     * </p>
     * 
     * <p>
     * This method is used by the input handling system to determine whether
     * the component should respond to mouse interactions.
     * </p>
     *
     * @param mouseX the x-coordinate of the point
     * 
     * @param mouseY the y-coordinate of the point
     * 
     * @return true if the point is inside the clickable area, false otherwise
     */
    boolean contains(int mouseX, int mouseY);

    boolean isEnabled();

    boolean isVisible();
}
