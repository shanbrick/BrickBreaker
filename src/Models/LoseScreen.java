package Models;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import SwingShapes.Rectangle;

public class LoseScreen extends JPanel {
    private Rectangle screen;

    public LoseScreen() {
        screen = new Rectangle();
        screen.setColor(Color.gray);
        screen.setSize(200, 200);
    }

    public int getXLocation() {
        return screen.getXLocation();
    }

    public void setXLocation(int xLocation) {
        screen.setLocation(xLocation, screen.getYLocation());
    }

    public int getYLocation() {
        return screen.getYLocation();
    }

    public void setYLocation(int yLocation) {
        screen.setLocation(screen.getXLocation(), yLocation);
    }

    public int getWidth() {
        return screen.getWidth();
    }

    public int getHeight() {
        return screen.getHeight();
    }

    public void draw(Graphics2D g) {
        screen.paint(g);
        g.drawString("YOU LOST HAHAHHAHA", 200, 200);
    }
}
