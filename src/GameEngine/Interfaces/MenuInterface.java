package GameEngine.Interfaces;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

public interface MenuInterface {

    void show();

    void hide();

    public interface MenuSetSize {
        void setSize(int width, int height);
    }

    public interface MenuSetPosition {
        void setPosition(int x, int y);

        void setPosition(Point position);

        void changePosition(int x, int y);
    }

    public interface MenuSetColor {
        void setColor(Color color);
    }

    public interface MenuSetImage {
        void setImage(Image image);
    }

    public interface MenuSetHoverVisual {
        void setHoverColor(Color hoverColor);

        void setHoverImage(Image hoverImage);
    }

    public interface MenuSetToggleVisual {
        void setToggleColor(Color toggleColor);

        void SetToggleImage(Image toggleImage);
    }

}