import GUIControls.Window;
import Game.Game;

public class Main {
    public static void main(String[] args) {
        new Window();
        Game game = new Game();
        Window.setContentPane(game);
        Window.setResizeable(false);
        game.startGame();
    }
}
