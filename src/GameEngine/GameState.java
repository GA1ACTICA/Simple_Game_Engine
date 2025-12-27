package GameEngine;

import java.awt.Color;

public class GameState {

    // here you can store variables that you use
    // for you game that should also be accesable in other classes

    public boolean debug = false;
    public boolean debugVerbose = false; // used in Keys.java and Mouse.java

    public float exampleUpdateInterval = 125f; // ≈60fps expressed in milliseconds
    public Color backgroundColor = Color.WHITE;

    // first used to set window dimension and then later used for windown scaling
    // and drawing alignment
    public int width = 800;
    public int height = 800;

    public int x1 = 0;
    public int y1 = 0;
}
