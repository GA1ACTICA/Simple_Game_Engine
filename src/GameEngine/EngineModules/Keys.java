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

package GameEngine.EngineModules;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import Game.Configs.GameState.GameState;

public class Keys implements KeyListener {

    private Set<Integer> keysPressed = new HashSet<>();
    private Set<Character> keysTyped = new HashSet<>();

    private final GameState state;

    public Keys(GameState state) {
        this.state = state;
    }

    /**
     * This method is strictly intended for use by the game engine.
     * It is public only because it is defined in an interface, and external
     * callers should avoid invoking it directly.
     * 
     * @param e the {@link java.awt.event.KeyEvent} received from AWT
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        /*
         * This is necessary since keyPressed is repeatedly called when a button is
         * being held
         */
        if (!keysPressed.contains(keyCode)) {

            if (state.data().debugVerbose)
                System.out.println("Key: %s %s was pressed".formatted(e.getKeyChar(), keyCode));

            keysPressed.add(keyCode);
        }
    }

    /**
     * This method is strictly intended for use by the game engine.
     * It is public only because it is defined in an interface, and external
     * callers should avoid invoking it directly.
     * 
     * @param e the {@link java.awt.event.KeyEvent} received from AWT
     */
    @Override
    public void keyReleased(KeyEvent e) {

        if (state.data().debugVerbose && keysPressed.contains(e.getKeyCode()))
            System.out.println("Key: %s %s was released".formatted(e.getKeyChar(), e.getKeyCode()));

        keysPressed.remove(e.getKeyCode());
        keysTyped.remove(e.getKeyChar());
    }

    /**
     * This method is strictly intended for use by the game engine.
     * It is public only because it is defined in an interface, and external
     * callers should avoid invoking it directly.
     * 
     * @param e the {@link java.awt.event.KeyEvent} received from AWT
     */
    @Override
    public void keyTyped(KeyEvent e) {
        keysTyped.add(e.getKeyChar());
    }

    /**
     * Returns a set of key codes representing all currently pressed keys.
     * Each key code corresponds to a standard {@link java.awt.event.KeyEvent} key
     * code.
     * 
     * @return {@code Set<Integer>} a Set of key codes representing the currently
     *         pressed keys
     */
    public Set<Integer> getKeysPressed() {
        return keysPressed;
    }

    /**
     * Returns a set of characters representing all currently pressed keys.
     * Characters are case sensitive and will be capitalized if applicable.
     * Non-Unicode characters are ignored.
     * 
     * @return {@code Set<Character>} a Set of Characters for the currently pressed
     *         keys
     */
    public Set<Character> getKeysTyped() {
        return keysTyped;
    }
}