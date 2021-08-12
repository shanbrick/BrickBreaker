package Models;

import java.awt.Color;
import java.awt.Graphics2D;

import SwingShapes.Rectangle;

public class Ball {
	private Rectangle ball;
	private Direction xDirection;
	private Direction yDirection;
	private int speed = 4;
	private int previousXLocation;
	private int previousYLocation;
	
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
		previousXLocation = ball.getXLocation();
		ball.setLocation(xLocation, ball.getYLocation());
	}
	
	public int getYLocation() {
		return ball.getYLocation();
	}
	
	public void setYLocation(int yLocation) {
		previousYLocation = ball.getYLocation();
		ball.setLocation(ball.getXLocation(), yLocation);
	}
	
	public int getPreviousXLocation() {
		return previousXLocation;
	}

	public void setPreviousXLocation(int previousXLocation) {
		this.previousXLocation = previousXLocation;
	}

	public int getPreviousYLocation() {
		return previousYLocation;
	}

	public void setPreviousYLocation(int previousYLocation) {
		this.previousYLocation = previousYLocation;
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

	public void move() {
		previousXLocation = ball.getXLocation();
		previousYLocation = ball.getYLocation();
		ball.setLocation(ball.getXLocation() + xDirection.getVelocity() * speed, ball.getYLocation() + yDirection.getVelocity() * speed);
	}
	
	public void moveX() {
		previousXLocation = ball.getXLocation();
		ball.setLocation(ball.getXLocation() + xDirection.getVelocity() * speed, ball.getYLocation());
	}
	
	public void moveY() {
		previousYLocation = ball.getYLocation();
		ball.setLocation(ball.getXLocation(), ball.getYLocation() + yDirection.getVelocity() * speed);
	}
	
	public void draw(Graphics2D g) {
		ball.paint(g);
	}
}
