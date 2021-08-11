package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import GUIControls.Window;
import Models.Ball;
import Models.Brick;
import Models.Direction;
import Models.Paddle;

public class Game extends JPanel {
	private Timer timer;
	private Paddle paddle;
	private Ball ball;
	private Direction directionPressed;
	private Brick[][] bricks;

	public Game() {
		Window.setTitle("Brick Breaker");
		setDoubleBuffered(true);

		paddle = new Paddle();
		ball = new Ball();
		bricks = new Brick[3][6];
		
		int brickWidth = 120;
		int brickHeight = 20;
		int horizontalSpacing = 5;
		int verticalSpacing = 5;
		int spacingFromWallX = 20;
		int spacingFromWallY = 5;
		
		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[i].length; j++) {
				bricks[i][j] = new Brick(j * brickWidth + (j * horizontalSpacing) + spacingFromWallX, i * brickHeight + (i * verticalSpacing) + spacingFromWallY, brickWidth, brickHeight);
			}
		}

		timer = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (directionPressed != null) {
					if (directionPressed == Direction.LEFT) {
						paddle.moveLeft();
					} 
					else if (directionPressed == Direction.RIGHT) {
						paddle.moveRight();
					}
				}

				ball.move();

				
				if (ball.getYDirection() == Direction.UP && ball.getYLocation() < 0) {
					ball.setYDirection(Direction.DOWN);
					ball.setYLocation(0);
				} 
				
				if (ball.getXDirection() == Direction.LEFT && ball.getXLocation() < 0) {
					ball.setXDirection(Direction.RIGHT);
					ball.setXLocation(0);
				} 
				else if (ball.getXDirection() == Direction.RIGHT && ball.getXLocation() + ball.getWidth() > getWidth()) { 
					ball.setXDirection(Direction.LEFT);
					ball.setXLocation(getWidth() - ball.getWidth());
				}
				
				if (isBallIntersectingPaddle(paddle, ball)) {
					if (ball.getYDirection() == Direction.UP) {
						ball.setYLocation(paddle.getYLocation() + paddle.getHeight());
						ball.setYDirection(Direction.DOWN);
					}
					else if (ball.getYDirection() == Direction.DOWN) {
						ball.setYLocation(paddle.getYLocation() - ball.getHeight());
						ball.setYDirection(Direction.UP);
					}
				}
				
				if (isBallIntersectingPaddle(paddle, ball)) {
					if (ball.getXDirection() == Direction.LEFT) {
						ball.setXLocation(paddle.getXLocation() + paddle.getWidth());
						ball.setXDirection(Direction.RIGHT);
					}
					else if (ball.getXDirection() == Direction.RIGHT) {
						ball.setXLocation(paddle.getXLocation() - ball.getWidth());
						ball.setXDirection(Direction.LEFT);
					}
				}
				
				boolean allBricksHit = true;
				outerLoop: for (int i = 0; i < bricks.length; i++) {
					for (int j = 0; j < bricks[i].length; j++) {
						Brick brick = bricks[i][j];
						if (!brick.getIsHit()) {
							allBricksHit = false;
							boolean collision = false;
							
							if (isBallIntersectingBrick(brick, ball)) {
								brick.setIsHit(true);
								collision = true;
								if (ball.getYDirection() == Direction.UP) {
									ball.setYLocation(brick.getYLocation() + brick.getHeight());
									ball.setYDirection(Direction.DOWN);
								}
								else if (ball.getYDirection() == Direction.DOWN) {
									ball.setYLocation(brick.getYLocation() - ball.getHeight());
									ball.setYDirection(Direction.UP);
								}
							}
							
							
							if (isBallIntersectingBrick(brick, ball)) {
								brick.setIsHit(true);
								collision = true;
								if (ball.getXDirection() == Direction.LEFT) {
									ball.setXLocation(brick.getXLocation() + brick.getWidth());
									ball.setXDirection(Direction.RIGHT);
								}
								else if (ball.getXDirection() == Direction.RIGHT) {
									ball.setXLocation(brick.getXLocation() - ball.getWidth());
									ball.setXDirection(Direction.LEFT);
								}
							}
							
							
							if (collision) {
								break outerLoop;
							}			
						}
					}
				}
				
				if (allBricksHit) {
					System.exit(1);
				}
				
				if (ball.getYLocation() + ball.getHeight() > getHeight()) {
					System.exit(1);
				}
				
				repaint();
			}
		});

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					directionPressed = Direction.LEFT;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					directionPressed = Direction.RIGHT;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					if (directionPressed == Direction.LEFT) {
						directionPressed = null;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					if (directionPressed == Direction.RIGHT) {
						directionPressed = null;
					}
				}
			}

		});

		this.setFocusable(true);

	}

	public void startGame() {
		paddle.setXLocation(getWidth() / 2 - paddle.getWidth() / 2);
		paddle.setYLocation(getHeight() - paddle.getHeight() - 10);

		int paddleX2 = paddle.getXLocation() + paddle.getWidth();
		int difference = paddleX2 - paddle.getXLocation();
		ball.setXLocation(paddle.getXLocation() + (difference / 2) - (ball.getWidth() / 2));
		ball.setYLocation(paddle.getYLocation() - ball.getHeight() - 5);
		
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D) g;
		paddle.draw(brush);
		ball.draw(brush);
		for (Brick[] brickArray : bricks) {
			for (Brick brick : brickArray) {
				if (!brick.getIsHit()) {
					brick.draw(brush);
				}
			}
		}
	}
	
	private boolean isBallIntersectingPaddle(Paddle paddle, Ball ball) {
		return paddle.getXLocation() < ball.getXLocation() + ball.getWidth() && paddle.getXLocation() + paddle.getWidth() > ball.getXLocation() && 
			paddle.getYLocation() < ball.getYLocation() + ball.getHeight() && paddle.getYLocation() + paddle.getHeight() > ball.getYLocation();
	}
	
	private boolean isBallIntersectingBrick(Brick brick, Ball ball) {
		return brick.getXLocation() < ball.getXLocation() + ball.getWidth() && brick.getXLocation() + brick.getWidth() > ball.getXLocation() && 
				brick.getYLocation() < ball.getYLocation() + ball.getHeight() && brick.getYLocation() + brick.getHeight() > ball.getYLocation();
	}
}
