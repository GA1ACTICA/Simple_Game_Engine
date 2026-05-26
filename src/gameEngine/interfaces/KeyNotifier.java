package gameEngine.interfaces;

@FunctionalInterface
public interface KeyNotifier {
    /**
     * A simple notification method that is invoked when a key is pressed.
     * <p>
     * It is invoked either once when a key if first pressed or repeatedly when a
     * key is held down.
     * <p>
     * <b>Note:</b> The time between invocations when a key is held down is
     * dependent on the individual users setting on their device.
     * 
     * @see gameEngine.engineModules.Keys#getKeysTyped() getKeysTyped()
     */
    void keyNotification();
}
