package GameEngine.Interfaces;

public interface Clickable extends ZIndexable {

    void onClick(Runnable action);

    void executeOnClick();

    boolean contains(int mouseX, int mouseY);

    boolean getDisabled();

    boolean getVisible();
}
