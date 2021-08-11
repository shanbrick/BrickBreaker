package Models;

import java.awt.Color;
import java.awt.Graphics2D;

import SwingShapes.Rectangle;

public class Brick {
	private Rectangle brick;
	private boolean isHit;
	
	public Brick(int xLocation, int yLocation, int width, int height) {
		brick = new Rectangle();
		brick.setColor(Color.green);
		brick.setSize(width, height);
		brick.setLocation(xLocation, yLocation);
		isHit = false;
	}
	
	public int getXLocation() {
		return brick.getXLocation();
	}
	
	public int getYLocation() {
		return brick.getYLocation();
	}
	
	public int getWidth() {
		return brick.getWidth();
	}
	
	public int getHeight() {
		return brick.getHeight();
	}
	
	public boolean getIsHit() {
		return isHit;
	}
	
	public void setIsHit(boolean isHit) {
		this.isHit = isHit;
	}
	
	public void draw(Graphics2D g) {
		brick.paint(g);
	}
}
