package AdvancedRendering.Menu.MenuContet;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;

import AdvancedRendering.Menu.GameButtonScaling;
import GameEngine.GamePanel;

public class GameButton {

    private final List<GameButtonScaling> buttons = new ArrayList<>();

    private boolean borderPainted = false;
    private boolean foucusPainted = false;
    private boolean rolloverEnabled = false;
    private boolean contentAreaFilled = true;

    private final GamePanel panel;

    public GameButton(GamePanel panel) {
        this.panel = panel;
    }

    public JButton addButton(int x, int y, int width, int height, int fontSize, String buttonLable) {

        JButton button = new JButton(buttonLable);
        GameButtonScaling buttonBounds = new GameButtonScaling(x, y, width, height, fontSize,
                button, panel);

        button.setBounds(x, y, width, height);

        button.setBorderPainted(this.borderPainted);
        button.setFocusPainted(this.foucusPainted);
        button.setRolloverEnabled(this.rolloverEnabled);
        button.setContentAreaFilled(this.contentAreaFilled);

        buttons.add(buttonBounds);
        panel.add(button);

        return button;
    }

    /**
     * Allows you to run code when the specified button is pressed
     * 
     * @param button The button you want effected
     * 
     * @param action The code to be executed inside of {}
     * 
     * @see #actionCommand(JButton button, String buttonCommandName)
     */

    public void onClick(JButton button, Runnable action) {
        button.addActionListener(e -> action.run());
    }

    /**
     * @param button            The button you want effected
     * 
     * @param buttonCommandName Attaches a string identifier to the button so when
     *                          the button is clicked the string is sent to the
     *                          Actionlistner
     * 
     * @see #onClick(Runnable action)
     */

    public void actionCommand(JButton button, String buttonCommandName) {
        button.setActionCommand(buttonCommandName);
    }

    public void setButtonAtributes(
            JButton button,
            boolean borderPainted,
            boolean foucusPainted,
            boolean isEnabled,
            boolean contentAreaFilled,
            boolean rolloverEnabled) {
        button.setBorderPainted(borderPainted);
        button.setFocusPainted(foucusPainted);
        button.setEnabled(isEnabled);
        button.setContentAreaFilled(contentAreaFilled);
        button.setRolloverEnabled(rolloverEnabled);
    }

    public void setButtonColor(JButton button, Color backgroundColor, Color foregroundColor) {
        button.setForeground(foregroundColor);
        button.setBackground(backgroundColor);
    }

    public void setButtonIcon(JButton button, Icon buttonIcon, Icon hoverIcon, Icon pressedIcon) {
        button.setIcon(buttonIcon);
        button.setRolloverIcon(hoverIcon);
        button.setPressedIcon(pressedIcon);
    }

    public void setFont(JButton button, Font font) {
        button.setFont(font);
    }

    public void setEnabled(JButton button, boolean isEnabled) {
        button.setEnabled(isEnabled);
    }

    public void buttonDebugg(JButton button, String toolTip, String idName) {
        button.setToolTipText(toolTip);
        button.setName(idName);
    }

    public List<GameButtonScaling> getButtons() {
        return buttons;
    }

}
