package AdvancedRendering;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import GameEngine.*;

public class AdvancedGraphics {

    GameState gs;

    public AdvancedGraphics(GameState gs) {
        this.gs = gs;
    }

    // draw
    public void drawScaling3DRect(Graphics g, int startX, int startY, int sizeX, int sizeY, Boolean raised) {
        g.draw3DRect(gs.x1 + startX, gs.y1 + startY, sizeX, sizeY, raised);
    }

    public void drawScaling3DRect(Graphics g, int startX, int startY, int sizeX, int sizeY, int startAngle,
            int arcAngle) {
        g.drawArc(gs.x1 + startX, gs.y1 + startY, sizeX, sizeY, startAngle, arcAngle);
    }

    public void drawScalingBytes(Graphics g, byte[] data, int offset, int length, int startX, int startY) {
        g.drawBytes(data, offset, length, gs.x1 + startX, gs.y1 + startY);
    }

    public void drawScalingChars(Graphics g, char[] data, int offset, int length, int startX, int startY) {
        g.drawChars(data, offset, length, gs.x1 + startX, gs.y1 + startY);
    }

    public void drawScalingString(Graphics g, String string, int startX, int startY) {
        g.drawString(string, gs.x1 + startX, gs.y1 + startY);
    }

    public void drawScalingImage(Graphics g, Image img, int startX, int startY,
            ImageObserver observer) {
        g.drawImage(img, gs.x1 + startX, gs.y1 + startY, observer);
    }

    public void drawScalingImage(Graphics g, Image img, int startX, int startY, Color bgcolor,
            ImageObserver observer) {
        g.drawImage(img, gs.x1 + startX, gs.y1 + startY, bgcolor, observer);
    }

    public void drawScalingImage(Graphics g, Image img, int startX, int startY, int width, int height,
            ImageObserver observer) {
        g.drawImage(img, gs.x1 + startX, gs.y1 + startY, width, height, observer);
    }

    public void drawScalingImage(Graphics g, Image img, int startX, int startY, int width, int height, Color bgcolor,
            ImageObserver observer) {
        g.drawImage(img, gs.x1 + startX, gs.y1 + startY, width, height, bgcolor, observer);
    }

    public void drawScalingLine(Graphics g, int x1, int y1, int x2, int y2) {
        g.drawLine(gs.x1 + x1, gs.y1 + y1, gs.x1 + x2, gs.y1 + y2);
    }

    public void drawScalingOval(Graphics g, int startX, int startY, int sizeX, int sizeY) {
        g.drawOval(gs.x1 + startX, gs.y1 + startY, sizeX, sizeY);
    }

    public void drawScalingRect(Graphics g, int startX, int startY, int sizeX, int sizeY) {
        g.drawRect(gs.x1 + startX, gs.y1 + startY, sizeX, sizeY);
    }

    public void drawScalingRect(Graphics g, int startX, int startY, int sizeX, int sizeY, int arcWidth, int arcHeight) {
        g.drawRoundRect(gs.x1 + startX, gs.y1 + startY, sizeX, sizeY, arcWidth, arcHeight);
    }

    public void drawScalingRect(Graphics g, String str, int startX, int startY) {
        g.drawString(str, gs.x1 + startX, gs.y1 + startY);
    }

    // fill
    public void fillScalingRect(Graphics g, int startX, int startY, int sizeX, int sizeY) {
        g.fillRect(gs.x1 + startX, gs.y1 + startY, sizeX, sizeY);
    }

}
