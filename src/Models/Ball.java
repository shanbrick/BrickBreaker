package Models;

import java.awt.Color;
import java.awt.Graphics2D;

import SwingShapes.Rectangle;

public class Ball {
	private Rectangle ball;
	private Direction xDirection;
	private Direction yDirection;
	private int speed = 4;
	
	public Ball() {
		ball = new Rectangle();
		ball.setColor(Color.black);
		ball.setSize(10,  10);
		xDirection = Direction.LEFT;
		yDirection = Direction.UP;
	}
	
	public int getXLocation() {
		return ball.getXLocation();
	}
	
	public void setXLocation(int xLocation) {
		ball.setLocation(xLocation, ball.getYLocation());
	}
	
	public int getYLocation() {
		return ball.getYLocation();
	}
	
	public void setYLocation(int yLocation) {
		ball.setLocation(ball.getXLocation(), yLocation);
	}
	
	public Direction getXDirection() {
		return xDirection;
	}

	public void setXDirection(Direction xDirection) {
		this.xDirection = xDirection;
	}

	public Direction getYDirection() {
		return yDirection;
	}

	public void setYDirection(Direction yDirection) {
		this.yDirection = yDirection;
	}
	
	public int getWidth() {
		return ball.getWidth();
	}
	
	public int getHeight() {
		return ball.getHeight();
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void moveX() {
		ball.setLocation(ball.getXLocation() + xDirection.getVelocity() * speed, ball.getYLocation());
	}
	
	public void moveY() {
		ball.setLocation(ball.getXLocation(), ball.getYLocation() + yDirection.getVelocity() * speed);
	}
	
	public void draw(Graphics2D g) {
		ball.paint(g);
	}
}
