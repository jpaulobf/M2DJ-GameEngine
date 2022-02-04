import util.Audio;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * Class responsable for the game
 */
public class Game {

    //the game statemachine goes here
    private StateMachine gameState      = null;

    //some support and the graphical device itself
    private Graphics2D g2d              = null;

    //desktop properties
    private int resolutionH             = 0;
    private int resolutionW             = 0;

    //extra volatile image for hud
    private final byte HUDHeight        = 40;

    //the game variables go here...
    private Scenario scenario           = null;
    private HUD hud                     = null;
    private Frog frog                   = null;
    private GameOver gameOver           = null;
    private volatile Audio theme        = null;
    private long framecounter           = 0;

    //width and height of window for base metrics of the game (minus HUD)
    private final int wwm               = 1344;
    private final int whm               = 832;

    /**
     * Game constructor
     * @param g2d
     * @param size
     */
    public Game(Graphics2D g2d, Dimension size) {

        //create the backbuffer from the size of screen resolution to avoid any resize process penalty
        this.g2d            = g2d;

        //screen resolution size
        this.resolutionW    = (int)size.getWidth();
        this.resolutionH    = (int)size.getHeight();

        //////////////////////////////////////////////////////////////////////
        // ->>>  create the game elements objects
        //////////////////////////////////////////////////////////////////////
        this.scenario       = new Scenario(this.g2d, this.wwm, this.whm);
        this.frog           = new Frog(this.g2d, this.scenario);
        this.hud            = new HUD(this.g2d, this.wwm, this.whm, this.HUDHeight, this.frog);
        this.gameOver       = new GameOver(this.g2d, this.wwm, this.whm);
        this.gameState      = new StateMachine(this);
        this.theme          = (Audio)LoadingStuffs.getInstance().getStuff("theme");
    }
    
    /**
     * Update the game logic / receives the frametime
     * @param frametime
     */
    public synchronized void update(long frametime) {
        //how many pixels per second I want?
        //ex.: movement at 200px/s
        //To do so, I need to divide 1_000_000_000 (1 second) by the exactly frametime to know the current FPS
        //With this number in hand, divide the pixel distance by the current fps
        //this must be your maximum movement amount in pixels
        //example:
        //double movementPerSecond = 100D;
        //double step = movementPerSecond / (double)(1_000_000_000D / (double)frametime);

        //////////////////////////////////////////////////////////////////////
        // ->>>  update the game elements
        //////////////////////////////////////////////////////////////////////
        if (this.gameState.getCurrentState() == StateMachine.STARTING) {
            this.theme.play(-1);
        } else if (this.gameState.getCurrentState() == StateMachine.IN_GAME) {
            this.scenario.update(frametime);
            this.frog.update(frametime);
            this.hud.update(frametime);
            //after possible colision, check lives.
            if (this.frog.getLives() == 0) {
                this.gameState.setCurrentState(StateMachine.GAME_OVER);
            }
        } else if (this.gameState.getCurrentState() == StateMachine.GAME_OVER) {
            this.framecounter += frametime;
            if (this.framecounter >= 3_000_000_000L) {
                this.framecounter = 0;
                this.frog.frogReset();
                this.frog.resetLives();
                this.hud.reset();
                this.gameState.setCurrentState(StateMachine.IN_GAME);
            } else {
                this.gameState.setCurrentState(StateMachine.GAME_OVER);
            }
        }
    }

    /**
     * Draw the game / receives the frametime
     * WTMD: This method draw the current screen, some steps described here:
                1) Clear the stage
     * @param frametime
     */
    public synchronized void draw(long frametime) {

        if (this.gameState.getCurrentState() != StateMachine.STARTING) {

            //this graphical device (g2d) points to backbuffer, so, we are making things behide the scenes
            //clear the stage
            this.g2d.setBackground(Color.BLACK);
            this.g2d.clearRect(0, 0, this.resolutionW, this.resolutionH);

            //////////////////////////////////////////////////////////////////////
            // ->>>  draw the game elements
            //////////////////////////////////////////////////////////////////////
            if (this.gameState.getCurrentState() == StateMachine.IN_GAME) {
                this.scenario.draw(frametime);
                this.frog.draw(frametime);
                this.hud.draw(frametime);
            } else if (this.gameState.getCurrentState() == StateMachine.GAME_OVER) {
                this.gameOver.draw(frametime);
            }

        } else {
            this.gameState.setCurrentState(StateMachine.IN_GAME);
        }
    }

    /**
     * Control the game main character movement
     * @param keyDirection
     */
    public void movement(int keyDirection) {
        if (this.gameState.getCurrentState() == StateMachine.IN_GAME) {
            this.frog.move(keyDirection);
        }
    }

    /**
     * Update game graphics
     * @param g2d
     */
    public void setGraphics2D(Graphics2D g2d) {
        this.g2d = g2d;
    }
}