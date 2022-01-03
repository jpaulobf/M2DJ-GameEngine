import Interfaces.Game;

public class StateMachine {
    
    public final static int LOADING     = 0;
    public final static int SPLASH      = 1;
    public final static int DEMO        = 2;
    public final static int MENU        = 3;
    public final static int OPTIONS     = 4;
    public final static int IN_GAME     = 5;
    public final static int CONTINUE    = 6;
    public final static int GAME_OVER   = 7;
    protected int currentState          = LOADING;
    protected Game referencetToGame     = null;

    public StateMachine(Game game) {
        this.currentState = LOADING;
    }

    public int getCurrentState() {
        return 0;
    }

    public void setCurrentState(int state) {
        this.currentState = state;
    }
}
