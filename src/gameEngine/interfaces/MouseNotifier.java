package gameEngine.interfaces;

import java.awt.event.MouseEvent;

public interface MouseNotifier {

    /**
     * Invoked when a click event is detected on any part of the window.
     * <p>
     * A click is usually represented as both a press and a release.
     */
    default void clickNotification(int x, int y) {
    };

    /**
     * Invoked when a press event is detected on any part of the window.
     */
    default void pressNotification(MouseEvent e) {
    };

    /**
     * Invoked when a release event is detected on any part of the window.
     */
    default void releaseNotification(MouseEvent e) {
    };

    /**
     * Invoked when a scroll event is detected.
     */
    default void scrollNotification(float deltaScroll) {
    };

    /**
     * Invoked when a mouse movement or dragging event is detected.
     */
    default void movementNotification(int x, int y, boolean dragging) {
    };

}
