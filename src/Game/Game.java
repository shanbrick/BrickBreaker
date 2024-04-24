package Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import GUIControls.Window;
import Models.Axis;
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
	private boolean allBricksHit;

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
				// move paddle
				if (directionPressed != null) {
					if (directionPressed == Direction.LEFT) {
						paddle.moveLeft();
						if (isBallIntersectingPaddle()) {
							ball.setXLocation(paddle.getXLocation() - ball.getWidth());
						}
					} 
					else if (directionPressed == Direction.RIGHT) {
						paddle.moveRight();
						if (isBallIntersectingPaddle()) {
							ball.setXLocation(paddle.getXLocation() + paddle.getWidth());
						}
					}
				}

				// move ball y axis
				ball.moveY();

				// check ball y axis wall collisions
				if (ball.getYDirection() == Direction.UP && ball.getYLocation() < 0) {
					ball.setYDirection(Direction.DOWN);
					ball.setYLocation(0);
				} 
				
				// check ball y axis paddle collisions
				paddleBallCollisionHandler(Axis.Y);
				
				// check ball y axis brick collisions
				brickBallCollisionHandler(Axis.Y);
				
				// if all bricks hit, game over
				if (allBricksHit) {
					System.exit(1);
				}

				// move ball y axis
				ball.moveX();
				
				// check ball x axis wall collisions
				if (ball.getXDirection() == Direction.LEFT && ball.getXLocation() < 0) {
					ball.setXDirection(Direction.RIGHT);
					ball.setXLocation(0);
				} 
				else if (ball.getXDirection() == Direction.RIGHT && ball.getXLocation() + ball.getWidth() > getWidth()) { 
					ball.setXDirection(Direction.LEFT);
					ball.setXLocation(getWidth() - ball.getWidth());
				}

				// check ball x axis paddle collisions
				paddleBallCollisionHandler(Axis.X);
				
				// check ball x axis brick collisions
				brickBallCollisionHandler(Axis.X);
				
				// if all bricks hit, game over
				if (allBricksHit) {
					System.exit(1);
				}
				
				// if ball hits bottom of the screen, game over
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
		this.grabFocus();
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
	
	private void paddleBallCollisionHandler(Axis axis) {
		if (isBallIntersectingPaddle()) {
			if (axis == Axis.X) {
				if (ball.getXDirection() == Direction.LEFT) {
					ball.setXLocation(paddle.getXLocation() + paddle.getWidth());
					ball.setXDirection(Direction.RIGHT);
				}
				else if (ball.getXDirection() == Direction.RIGHT) {
					ball.setXLocation(paddle.getXLocation() - ball.getWidth());
					ball.setXDirection(Direction.LEFT);
				}
			}
			else if (axis == Axis.Y) {
				if (ball.getYDirection() == Direction.UP) {
					ball.setYLocation(paddle.getYLocation() + paddle.getHeight());
					ball.setYDirection(Direction.DOWN);
				}
				else if (ball.getYDirection() == Direction.DOWN) {
					ball.setYLocation(paddle.getYLocation() - ball.getHeight());
					ball.setYDirection(Direction.UP);
				}
			}
		}
	}
	
	private void brickBallCollisionHandler(Axis axis) {
		allBricksHit = true;
		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[i].length; j++) {
				Brick brick = bricks[i][j];
				if (!brick.getIsHit()) {
					allBricksHit = false;
					
					if (isBallIntersectingBrick(brick)) {
						brick.setIsHit(true);
						
						if (axis == Axis.X) {
							if (ball.getXDirection() == Direction.LEFT) {
								ball.setXLocation(brick.getXLocation() + brick.getWidth());
								ball.setXDirection(Direction.RIGHT);
							}
							else if (ball.getXDirection() == Direction.RIGHT) {
								ball.setXLocation(brick.getXLocation() - ball.getWidth());
								ball.setXDirection(Direction.LEFT);
							}
						}
						else if (axis == Axis.Y) {
							if (ball.getYDirection() == Direction.UP) {
								ball.setYLocation(brick.getYLocation() + brick.getHeight());
								ball.setYDirection(Direction.DOWN);
							}
							else if (ball.getYDirection() == Direction.DOWN) {
								ball.setYLocation(brick.getYLocation() - ball.getHeight());
								ball.setYDirection(Direction.UP);
							}
						}
						return;
					}
				}
			}
		}
	}
	
	private boolean isBallIntersectingPaddle() {
		return paddle.getXLocation() < ball.getXLocation() + ball.getWidth() && paddle.getXLocation() + paddle.getWidth() > ball.getXLocation() && 
			paddle.getYLocation() < ball.getYLocation() + ball.getHeight() && paddle.getYLocation() + paddle.getHeight() > ball.getYLocation();
	}
	
	private boolean isBallIntersectingBrick(Brick brick) {
		return brick.getXLocation() < ball.getXLocation() + ball.getWidth() && brick.getXLocation() + brick.getWidth() > ball.getXLocation() && 
				brick.getYLocation() < ball.getYLocation() + ball.getHeight() && brick.getYLocation() + brick.getHeight() > ball.getYLocation();
	}
}
