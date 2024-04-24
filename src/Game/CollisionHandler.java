package Game;

import Models.Axis;
import Models.Ball;
import Models.Brick;
import Models.Direction;
import Models.Paddle;

public class CollisionHandler {
    private Ball ball;
    private Paddle paddle;
    private Brick[][] bricks;

    public CollisionHandler(Ball ball, Paddle paddle, Brick[][] bricks) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
    }

    public boolean paddleBallCollisionHandler(Axis axis) {
		if (ball.intersects(paddle)) {
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
			return true;
		}
		return false;
	}

	public boolean brickBallCollisionHandler(Axis axis) {
		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[i].length; j++) {
				Brick brick = bricks[i][j];
				if (!brick.getIsHit()) {
					if (ball.intersects(brick)) {
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
						return true;
					}
				}
			}
		}
		return false;
	}
}
