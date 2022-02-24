import interfaces.IGame;
import java.awt.Dimension;

/**
 * Game factory
 */
public class GameFactory {
    public static IGame getGameInstance() {
        return (new Game());
    }
}
