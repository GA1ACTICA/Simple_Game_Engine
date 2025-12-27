package AdvancedRendering.Menu.MenuContet;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import AdvancedRendering.Menu.GameButtonScaling;
import GameEngine.*;

public class GameMenu implements Drawable {

        int x = 300;
        int y = 200;
        int width = 100;
        int height = 100;
        int boarderHeight = 10;
        int boarderWidth = 10;
        boolean resizable = false;
        boolean show = false;

        boolean hasBoarders = true;

        Color cTopLeft = Color.BLUE;
        Color cTop = Color.CYAN;
        Color cTopRight = Color.GREEN;
        Color cLeft = Color.MAGENTA;
        Color cRight = Color.ORANGE;
        Color cBottomLeft = Color.PINK;
        Color cBottom = Color.RED;
        Color cBottomRight = Color.YELLOW;

        Image iTopLeft = null;
        Image iTop = null;
        Image iTopRight = null;
        Image iLeft = null;
        Image iRight = null;
        Image iBottomLeft = null;
        Image iBottom = null;
        Image iBottomRight = null;

        Color backgroundColor = Color.WHITE;
        Image backgroundImage = null;

        private final GameState gs;
        private JButton button;
        private final GamePanel panel;

        public GameMenu(GameState gs, GamePanel panel) {
                this.gs = gs;
                this.panel = panel;
        }

        @Override
        public void draw(Graphics g) {

                if (show) {

                        // setSize for menu without boarders
                        if (!hasBoarders && !resizable) {
                                g.setColor(backgroundColor);
                                g.fillRect(x, y, width, height);
                        }

                        // setSize for menu with boarders
                        if (hasBoarders) {
                                g.setColor(backgroundColor);
                                g.fillRect(x, y, width, height);

                                g.setColor(cTopLeft);
                                g.fillRect(x - boarderWidth, y - boarderHeight, boarderWidth, boarderHeight);

                                g.setColor(cTop);
                                g.fillRect(x, y - boarderHeight, width, boarderHeight);

                                g.setColor(cTopRight);
                                g.fillRect(x + width, y - boarderHeight, boarderWidth, boarderHeight);

                                g.setColor(cLeft);
                                g.fillRect(x - boarderWidth, y, boarderWidth, height);

                                g.setColor(cLeft);
                                g.fillRect(x + width, y, boarderWidth, height);

                                g.setColor(cBottomLeft);
                                g.fillRect(x - boarderWidth, y + height, boarderWidth, boarderHeight);

                                g.setColor(cBottom);
                                g.fillRect(x, y + height, width, boarderHeight);

                                g.setColor(cBottomRight);
                                g.fillRect(x + width, y + height, boarderWidth, boarderHeight);

                        }

                }
        }

        /**
         * Sets the size of a menu
         * 
         * @param startX The X coordinate where the menu originates from
         * 
         * @param startY The Y coordinate where the menu originates from
         *
         * @param sizeX  The menues width
         * 
         * @param sizeY  The menues height
         * 
         * @see #setSize(int startX, int startY, int sizeX, int sizeY, int
         *      boarderHeight,
         *      int boarderWidth,
         *      boolean resizable)
         */
        public void setSize(int x, int y, int width, int height) {

                // limit this to 0 < x
                if (x <= 0 || y <= 0) {
                        throw new IllegalArgumentException("Invalid numerical value");
                }
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
                hasBoarders = false;
                resizable = false;

        }

        /**
         * Sets the size of a menu and it's boarders
         * 
         * @param startX        The X coordinate where the menu originates from
         * 
         * @param startY        The Y coordinate where the menu originates from
         *
         * @param sizeX         The menues width
         * 
         * @param sizeY         The menues height
         * 
         * @param boarderHeight The boarders height
         * 
         * @param boarderWidth  The boarders width
         * 
         * @param resizable     Sets the menu to be resizable or not
         * 
         * @see #setSize(int startX, int startY, int sizeX, int sizeY)
         */
        public void setSize(int x, int y, int width, int height, int boarderHeight, int boarderWidth,
                        boolean resizable) {

                // limit this to 0 < x
                if (x <= 0 || y <= 0 || boarderHeight <= 0 || boarderWidth <= 0) {
                        throw new IllegalArgumentException("Invalid numerical value");
                }
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
                this.boarderHeight = boarderHeight;
                this.boarderWidth = boarderWidth;

                this.resizable = resizable;
                show = true;
        }

        /**
         * 
         * @param backgroundColor
         */
        public void backgroundColor(Color backgroundColor) {

                this.backgroundColor = backgroundColor;
                backgroundImage = null;

        }

        /**
         * @param backgroundImage
         */
        public void backgroundImage(Image backgroundImage) {

                this.backgroundImage = backgroundImage;
                backgroundColor = null;

        }

        /**
         * @param topLeft
         * @param top
         * @param topRight
         * @param left
         * @param right
         * @param bottomLeft
         * @param bottom
         * @param bottomRight
         */
        public void boarderColors(Color topLeft, Color top, Color topRight, Color left, Color right, Color bottomLeft,
                        Color bottom, Color bottomRight) {

                this.cTopLeft = topLeft;
                this.cTop = top;
                this.cTopRight = topRight;
                this.cLeft = left;
                this.cRight = right;
                this.cBottomLeft = bottomLeft;
                this.cBottom = bottom;
                this.cBottomRight = bottomRight;

                iTopLeft = null;
                iTop = null;
                iTopRight = null;
                iLeft = null;
                iRight = null;
                iBottomLeft = null;
                iBottom = null;
                iBottomRight = null;
        }

        /**
         * @param topLeft
         * @param top
         * @param topRight
         * @param left
         * @param right
         * @param bottomLeft
         * @param bottom
         * @param bottomRight
         */
        public void boarderImage(Image topLeft, Image top, Image topRight, Image left, Image right, Image bottomLeft,
                        Image bottom, Image bottomRight) {

                this.iTopLeft = topLeft;
                this.iTop = top;
                this.iTopRight = topRight;
                this.iLeft = left;
                this.iRight = right;
                this.iBottomLeft = bottomLeft;
                this.iBottom = bottom;
                this.iBottomRight = bottomRight;

                cTopLeft = null;
                cTop = null;
                cTopRight = null;
                cLeft = null;
                cRight = null;
                cBottomLeft = null;
                cBottom = null;
                cBottomRight = null;

        }

        public void show() {
                show = true;
                if (this.button != null) {
                        this.button.setVisible(true);

                }

        }

        public void hide() {
                show = false;

                if (this.button != null) {
                        this.button.setVisible(false);
                }

        }

}