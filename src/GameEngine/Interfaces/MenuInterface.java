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

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import AdvancedRendering.uiRendering.Button.RectButton;
import AdvancedRendering.uiRendering.CheckBox.RectCheckbox;
import AdvancedRendering.uiRendering.Misc.FPSCounter;

/**
 * Base interface for all menu components.
 * <p>
 * All menu components support visibility control via {@link #show()} and
 * {@link #hide()}.
 *
 * <p>
 * Additional behavior is defined through optional capability interfaces.
 * A component may implement one or more of these to expose extra functionality.
 *
 * <p>
 * Examples of optional capabilities:
 * <ul>
 * <li>{@link MenuSetColor} — allows changing color</li>
 * <li>{@link MenuSetSize} — allows resizing</li>
 * <li>{@link MenuSetPosition} — allows positioning</li>
 * </ul>
 *
 * <p>
 * Not all components support all capabilities. Callers should check
 * whether a component implements a capability before using it.
 */
public interface MenuInterface {

    void show();

    void hide();

    /**
     * Capability for components that support resizing.
     * <p>
     * Implemented by components such as {@link RectButton}
     */
    public interface MenuSetSize {
        void setSize(int width, int height);

        /**
         * Changes the components size relative to its current size.
         * 
         * @param dWidth  the width offset
         * @param dHeight the height offset
         */
        void translateSize(int dWidth, int dHeight);
    }

    /**
     * Capability for components that support positioning.
     * <p>
     * Implemented by components such as {@link FPSCounter}
     */
    public interface MenuSetPosition {
        void setPosition(int x, int y);

        void setPosition(Point position);

        /**
         * Moves the component relative to its current position.
         *
         * @param dx the horizontal offset
         * @param dy the vertical offset
         */
        void translatePosition(int dx, int dy);
    }

    /**
     * Capability for components that support one color.
     * <p>
     * Implemented by components such as {@link RectButton}
     */
    public interface MenuSetColor {
        void setColor(Color color);
    }

    /**
     * Capability for components that support one Image.
     * <p>
     * Implemented by components such as {@link RectButton}
     */
    public interface MenuSetImage {
        void setImage(Image image);
    }

    /**
     * Capability for components that support visual change when hovered.
     * <p>
     * Implemented by components such as {@link RectButton}, which update
     * their appearance when their hover state changes.
     */
    public interface MenuSetHoverVisual {
        /**
         * Sets the color used when the component is hovered.
         * 
         * @param hoverColor the color to apply when hovered
         */
        void setHoverColor(Color hoverColor);

        /**
         * Sets the image used when the component is hovered.
         * 
         * @param hoverImage the image to apply when hovered
         */
        void setHoverImage(Image hoverImage);
    }

    /**
     * Capability for components that support visual change when toggled.
     * <p>
     * Implemented by components such as {@link RectCheckbox}, which update
     * their appearance when their toggle state changes.
     */
    public interface MenuSetToggleVisual {
        /**
         * Sets the color used when the component is in the toggled state.
         *
         * @param toggleColor the color to apply when toggled
         */
        void setToggleColor(Color toggleColor);

        /**
         * Sets the image used when the component is in the toggled state.
         *
         * @param toggleImage the image to display when toggled
         */
        void setToggleImage(Image toggleImage);
    }
}