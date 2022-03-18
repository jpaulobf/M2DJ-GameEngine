package game;

/**
 * StateMachine class - control the state of the game
 */
public class StateMachine {
    
    public final static int LOADING     = 0;
    public final static int SPLASH      = 1;
    public final static int DEMO        = 2;
    public final static int MENU        = 3;
    public final static int OPTIONS     = 4;
    public final static int STARTING    = 5;
    public final static int STAGING     = 6;
    public final static int IN_GAME     = 7;
    public final static int CONTINUE    = 8;
    public final static int GAME_OVER   = 9;
    protected int currentState          = STARTING;
    protected Game referencetToGame     = null;

    /**
     * Constructor
     * @param game
     */
    public StateMachine(Game game) {
        this.currentState = STAGING;
        this.referencetToGame = game;
    }

    public int getCurrentState() {
        return (this.currentState);
    }

    public void setCurrentState(int state) {
        this.currentState = state;
    }
}
