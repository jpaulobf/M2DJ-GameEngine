package engine;

import game.Game;
import interfaces.GameInterface;

/**
 * Game factory
 */
public class GameFactory {
    public static GameInterface getGameInstance() {
        return (new Game());
    }
}
