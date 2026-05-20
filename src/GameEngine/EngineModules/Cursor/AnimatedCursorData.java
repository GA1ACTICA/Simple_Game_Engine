package GameEngine.EngineModules.Cursor;

import java.util.List;

public class AnimatedCursorData {

    private List<Frame> frames;

    List<Frame> getFrames() {
        return frames;
    }

    static class Frame {

        private String image;
        private int durationMs;
        private int[] hotspot;

        String getImage() {
            return image;
        }

        int getDurationMs() {
            return durationMs;
        }

        int[] getHotspot() {
            return hotspot;
        }

        int getHotspotX() {
            return hotspot[0];
        }

        int getHotspotY() {
            return hotspot[1];
        }
    }
}
