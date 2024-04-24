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

public class GamePanel extends JPanel {
	private Timer timer;
	private GameLoop gameLoop;
	public static int WIDTH;
	public static int HEIGHT;
	// private Paddle paddle;
	// private Ball ball;
	// private Direction directionPressed;
	// private Brick[][] bricks;
	// private int totalNumberOfBricks;
	// private int numberOfBricksHit;

	public GamePanel() {
		Window.setTitle("Brick Breaker");
		setDoubleBuffered(true);
		gameLoop = new GameLoop();

		// paddle = new Paddle();
		// ball = new Ball();
		// bricks = new Brick[3][6];
		
		// int brickWidth = 120;
		// int brickHeight = 20;
		// int horizontalSpacing = 5;
		// int verticalSpacing = 5;
		// int spacingFromWallX = 20;
		// int spacingFromWallY = 5;
		
		// for (int i = 0; i < bricks.length; i++) {
		// 	for (int j = 0; j < bricks[i].length; j++) {
		// 		int brickXLocation = j * brickWidth + (j * horizontalSpacing) + spacingFromWallX;
		// 		int brickYLocation = i * brickHeight + (i * verticalSpacing) + spacingFromWallY;
		// 		bricks[i][j] = new Brick(brickXLocation, brickYLocation, brickWidth, brickHeight);
		// 		totalNumberOfBricks++;
		// 	}
		// }

		timer = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// move paddle
				// if (directionPressed != null) {
				// 	if (directionPressed == Direction.LEFT) {
				// 		paddle.moveLeft();
				// 		if (ball.intersects(paddle)) {
				// 			ball.setXLocation(paddle.getXLocation() - ball.getWidth());
				// 		}
				// 	} 
				// 	else if (directionPressed == Direction.RIGHT) {
				// 		paddle.moveRight();
				// 		if (ball.intersects(paddle)) {
				// 			ball.setXLocation(paddle.getXLocation() + paddle.getWidth());
				// 		}
				// 	}
				// }

				// // move ball y axis
				// ball.moveY();

				// // check ball y axis wall collisions
				// if (ball.getYDirection() == Direction.UP && ball.getYLocation() < 0) {
				// 	ball.setYDirection(Direction.DOWN);
				// 	ball.setYLocation(0);
				// } 
				
				// // check ball y axis paddle collisions
				// paddleBallCollisionHandler(Axis.Y);
				
				// // check ball y axis brick collisions
				// brickBallCollisionHandler(Axis.Y);
				
				// // if all bricks hit, game over
				// if (allBricksHit()) {
				// 	System.exit(1);
				// }

				// // move ball y axis
				// ball.moveX();
				
				// // check ball x axis wall collisions
				// if (ball.getXDirection() == Direction.LEFT && ball.getXLocation() < 0) {
				// 	ball.setXDirection(Direction.RIGHT);
				// 	ball.setXLocation(0);
				// } 
				// else if (ball.getXDirection() == Direction.RIGHT && ball.getXLocation() + ball.getWidth() > getWidth()) { 
				// 	ball.setXDirection(Direction.LEFT);
				// 	ball.setXLocation(getWidth() - ball.getWidth());
				// }

				// // check ball x axis paddle collisions
				// paddleBallCollisionHandler(Axis.X);
				
				// // check ball x axis brick collisions
				// brickBallCollisionHandler(Axis.X);
				
				// // if all bricks hit, game over
				// if (allBricksHit()) {
				// 	System.exit(1);
				// }
				
				// // if ball hits bottom of the screen, game over
				// if (ball.getYLocation() + ball.getHeight() > getHeight()) {
				// 	System.exit(1);
				// }
				gameLoop.update();
				repaint();
			}
		});

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					gameLoop.setDirectionPressed(Direction.LEFT);
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					gameLoop.setDirectionPressed(Direction.RIGHT);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					if (gameLoop.getDirectionPressed() == Direction.LEFT) {
						gameLoop.setDirectionPressed(null);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					if (gameLoop.getDirectionPressed() == Direction.RIGHT) {
						gameLoop.setDirectionPressed(null);
					}
				}
			}

		});

		this.setFocusable(true);

	}

	public void startGame() {
		HEIGHT = getHeight();
		WIDTH = getWidth();
		gameLoop.setup();
		timer.start();
		this.grabFocus();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		gameLoop.draw(graphics);
	}
	
	
}
