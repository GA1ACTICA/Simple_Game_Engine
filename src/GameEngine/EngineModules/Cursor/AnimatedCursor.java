package GameEngine.EngineModules.Cursor;

import Utils.JsonUtils.JsonBacked;

public class AnimatedCursor extends JsonBacked<AnimatedCursorData> {

    protected AnimatedCursor(AnimatedCursorData initialData) {
        super(new AnimatedCursorData());

    }

    @Override
    protected void successfulExportLog(AnimatedCursorData object, String path) {
        System.out.println();
    }

    @Override
    protected void successfulImportLog(AnimatedCursorData data, String path) {
        System.out.println();
    }
}
