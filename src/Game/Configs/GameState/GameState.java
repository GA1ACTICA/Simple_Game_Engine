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

package Game.Configs.GameState;

import Utils.JsonUtils.JsonBacked;

public class GameState extends JsonBacked<GameStateData> {

    GameState state;

    public GameState() {
        super(new GameStateData());
    }

    public void setGameStateData(GameState state) {
        this.state = state;
    }

    @Override
    protected void successfulExportLog(GameStateData object, String path) {
        if (state.data().debug)
            System.out.println('\n' + "Successfully exported ('%s') to %s".formatted(object, path));
    }

    @Override
    protected void successfulImportLog(GameStateData object, String path) {
        if (state.data().debug)
            System.out.println('\n' + "Successfully imported ('%s') to %s".formatted(path, object));
    }

}
