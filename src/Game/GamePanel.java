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
import Models.Ball;
import Models.Brick;
import Models.Paddle;
import Utils.Axis;
import Utils.Direction;

public class GamePanel extends JPanel {
	private Timer timer;
	private GameLoop gameLoop;
	public static int WIDTH;
	public static int HEIGHT;

	public GamePanel() {
		Window.setTitle("Brick Breaker");
		setDoubleBuffered(true);
		gameLoop = new GameLoop();

		timer = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
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
