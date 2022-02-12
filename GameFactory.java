import interfaces.IGame;
import java.awt.Graphics2D;
import java.awt.Dimension;

/**
 * Game factory
 */
public class GameFactory {
    public static IGame getGameInstance(Graphics2D g2d, Dimension size) {
        return (new Game(g2d, size));
    }
}
