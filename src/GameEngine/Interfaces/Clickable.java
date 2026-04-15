/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 *
 * Copyright © 2026 Galactica
 */

package GameEngine.Interfaces;

public interface Clickable {

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
     * <pre>{@code
     * clickableObject.onClick(() -> {
     *     System.out.println("Object clicked!");
     * });
     * }</pre>
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

    boolean isEnabled();

}
