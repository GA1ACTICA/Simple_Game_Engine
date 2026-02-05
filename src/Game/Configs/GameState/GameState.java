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
    protected void succsessfullExportLog(GameStateData object, String path) {
        if (state.data().debug)
            System.out.println('\n' + "Successfully exported ('%s') to %s".formatted(object, path));
    }

    @Override
    protected void succsessfullImportLog(GameStateData object, String path) {
        if (state.data().debug)
            System.out.println('\n' + "Successfully imported ('%s') to %s".formatted(path, object));
    }

}
