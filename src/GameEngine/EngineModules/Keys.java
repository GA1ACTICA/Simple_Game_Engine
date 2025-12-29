package GameEngine.EngineModules;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Game.GameState;

public class Keys implements KeyListener {

    public int keyCodePressed;
    public char keyNamePressed;

    public int keyCodeReleased;
    public char keyNameReleased;

    public int keyCodeTyped;
    public char keyNameTyped;

    private final GameState state;

    public Keys(GameState state) {
        this.state = state;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyCodePressed = e.getKeyCode();
        keyNamePressed = e.getKeyChar();

        if (state.debugVerbose) {
            System.out.println("keyCodePressed: " + keyCodePressed + '\n');
            System.out.println("keyNamePressed: " + keyNamePressed + '\n');
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyCodeReleased = e.getKeyCode();
        keyNameReleased = e.getKeyChar();

        if (state.debugVerbose) {
            System.out.println("keyCodeReleased: " + keyCodeReleased + '\n');
            System.out.println("keyNameReleased: " + keyNameReleased + '\n');
            System.out.println("");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyCodeTyped = e.getKeyCode();
        keyNameTyped = e.getKeyChar();

        if (state.debugVerbose) {
            System.out.println("keyCodeTyped: " + keyCodeTyped + '\n');
            System.out.println("keyNameTyped: " + keyNameTyped + '\n');
            System.out.println("");
        }

    }
}