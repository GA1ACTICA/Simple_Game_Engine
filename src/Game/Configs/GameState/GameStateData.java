/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the GPL 3.0 License.
 * See LICENSE file in the project root for full license information.

 Coppyright © 2026 Galactica
 */

package Game.Configs.GameState;

import java.awt.Color;

public class GameStateData {

    // here you can store variables that you use
    // for you game that should also be accesable in other classes

    public boolean debug = false;
    public boolean debugVerbose = false; // used in Keys.java and Mouse.java

    public float exampleUpdateInterval = 1000 / 60; // ≈60fps expressed in milliseconds
    public Color backgroundColor = Color.WHITE;

    // first used to set window dimension and then later used for windown scaling
    // and drawing alignment
    public int width = 1000;
    public int height = 1000;
}
