package gameEngine.interfaces;

public interface MouseNotifier {

    /**
     * Invoked when a click event is detected on any part of the window.
     */
    default void clickNotification(int x, int y) {
    };

    /**
     * Invoked when a scroll event is detected.
     */
    default void scrollNotification(float deltaScroll) {
    };

    /**
     * Invoked when a mouse movement or dragging event is detected.
     */
    default void movementNotification(int x, int y) {
    };

}
