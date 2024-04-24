package Game;

import java.awt.Graphics2D;

import Models.Ball;
import Models.Brick;
import Models.Paddle;
import Utils.Axis;
import Utils.Direction;

public class GameLoop {
	private Paddle paddle;
	private Ball ball;
	private Direction directionPressed;
	private Brick[][] bricks;
	private int totalNumberOfBricks;
	private int numberOfBricksHit;
    private CollisionHandler collisionHandler;
    
    public void setup() {
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
				int brickXLocation = j * brickWidth + (j * horizontalSpacing) + spacingFromWallX;
				int brickYLocation = i * brickHeight + (i * verticalSpacing) + spacingFromWallY;
				bricks[i][j] = new Brick(brickXLocation, brickYLocation, brickWidth, brickHeight);
				totalNumberOfBricks++;
			}
		}

        paddle.setXLocation(GamePanel.WIDTH / 2 - paddle.getWidth() / 2);
		paddle.setYLocation(GamePanel.HEIGHT - paddle.getHeight() - 10);

		int paddleX2 = paddle.getXLocation() + paddle.getWidth();
		int difference = paddleX2 - paddle.getXLocation();
		ball.setXLocation(paddle.getXLocation() + (difference / 2) - (ball.getWidth() / 2));
		ball.setYLocation(paddle.getYLocation() - ball.getHeight() - 5);

        collisionHandler = new CollisionHandler(ball, paddle, bricks);
    }

    public void update() {
        if (directionPressed != null) {
            if (directionPressed == Direction.LEFT) {
                paddle.moveLeft();
                if (ball.intersects(paddle)) {
                    ball.setXLocation(paddle.getXLocation() - ball.getWidth());
                }
            } 
            else if (directionPressed == Direction.RIGHT) {
                paddle.moveRight();
                if (ball.intersects(paddle)) {
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
        collisionHandler.paddleBallCollisionHandler(Axis.Y);
        
        // check ball y axis brick collisions
        boolean isBrickYCollision = collisionHandler.brickBallCollisionHandler(Axis.Y);
        if (isBrickYCollision) {
            numberOfBricksHit++;
        }

        // if all bricks hit, game over
        if (allBricksHit()) {
            System.exit(0);
        }

        // move ball y axis
        ball.moveX();
        
        // check ball x axis wall collisions
        if (ball.getXDirection() == Direction.LEFT && ball.getXLocation() < 0) {
            ball.setXDirection(Direction.RIGHT);
            ball.setXLocation(0);
        } 
        else if (ball.getXDirection() == Direction.RIGHT && ball.getXLocation() + ball.getWidth() > GamePanel.WIDTH) { 
            ball.setXDirection(Direction.LEFT);
            ball.setXLocation(GamePanel.WIDTH - ball.getWidth());
        }

        // check ball x axis paddle collisions
        collisionHandler.paddleBallCollisionHandler(Axis.X);
        
        // check ball x axis brick collisions
        boolean isBrickXCollision= collisionHandler.brickBallCollisionHandler(Axis.X);
        if (isBrickXCollision) {
            numberOfBricksHit++;
        }

        // if all bricks hit, game over
        if (allBricksHit()) {
            System.exit(1);
        }
        
        // if ball hits bottom of the screen, game over
        if (ball.getYLocation() + ball.getHeight() >= GamePanel.HEIGHT) {
            System.exit(0);
        }
    }

    public void draw(Graphics2D graphics) {
        paddle.draw(graphics);
		ball.draw(graphics);
		for (Brick[] brickArray : bricks) {
			for (Brick brick : brickArray) {
				if (!brick.getIsHit()) {
					brick.draw(graphics);
				}
			}
		}
    }

	private boolean allBricksHit() {
		return numberOfBricksHit == totalNumberOfBricks;
	}

    public Direction getDirectionPressed() {
        return this.directionPressed;
    }

    // GamePanel file uses this to tell GameLoop which direction was pressed (left or right)
    public void setDirectionPressed(Direction directionPressed) {
        this.directionPressed = directionPressed;
    }
}
