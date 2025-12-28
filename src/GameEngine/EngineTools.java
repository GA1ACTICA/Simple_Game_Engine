package GameEngine;

import java.awt.Image;

import javax.swing.ImageIcon;

public class EngineTools {

    @SuppressWarnings("unused")
    private final GameState state;

    public EngineTools(GameState state) {
        this.state = state;
    }

    /**
     * 
     * @param filePathFromProject
     * @return
     */
    public Image getImage(String filePathFromProject) {
        Image image = new ImageIcon(getClass().getResource("../" + filePathFromProject)).getImage();
        return image;
    }

}
