import GUIControls.Window;
import Game.GamePanel;

public class Main {
    public static void main(String[] args) {
        new Window();
        GamePanel game = new GamePanel();
        Window.setContentPane(game);
        Window.setResizeable(false);
        game.startGame();
    }
}
