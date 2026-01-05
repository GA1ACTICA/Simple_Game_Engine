package GameEngine.EngineModules;

import java.awt.Image;

import javax.swing.ImageIcon;

public class EngineTools {

    /**
     * 
     * @param filePathFromProject
     * @return
     */
    public Image getImage(String filePathFromProject) {
        Image image = new ImageIcon(getClass().getResource(filePathFromProject)).getImage();
        return image;
    }

}
