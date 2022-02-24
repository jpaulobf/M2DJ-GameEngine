import interfaces.IGame;

/**
 * Game factory
 */
public class GameFactory {
    public static IGame getGameInstance() {
        return (new Game());
    }
}
