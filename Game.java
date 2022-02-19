import util.Audio;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import interfaces.IGame;

/**
 * Class responsable for the game
 */
public class Game implements IGame {

    //the game statemachine goes here
    private StateMachine gameState          = null;

    //some support and the graphical device itself
    private Graphics2D g2d                  = null;

    //desktop properties
    private int resolutionH                 = 0;
    private int resolutionW                 = 0;

    //extra volatile image for hud
    private final byte HUDHeight            = 40;

    //the game variables go here...
    private Scenario scenario               = null;
    private HUD hud                         = null;
    private Frog frog                       = null;
    private GameOver gameOver               = null;
    private Message message                 = null;
    private Timer timer                     = null;
    private volatile Audio theme            = null;
    private volatile Audio gameoverTheme    = null;
    private volatile long framecounter      = 0;
    private volatile boolean mute           = false;
    private volatile boolean canContinue    = true;

    //width and height of window for base metrics of the game (minus HUD)
    private final int wwm                   = 1344;
    private final int whm                   = 832;

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
        this.gameState      = new StateMachine(this);
        this.scenario       = new Scenario(this, this.wwm, this.whm);
        this.frog           = new Frog(this);
        this.hud            = new HUD(this, this.wwm, this.whm, this.HUDHeight);
        this.gameOver       = new GameOver(this, this.wwm, this.whm);
        this.message        = new Message(this, this.wwm, this.whm);
        this.timer          = new Timer(this, this.wwm, this.whm);
        this.theme          = (Audio)LoadingStuffs.getInstance().getStuff("theme");
        this.gameoverTheme  = (Audio)LoadingStuffs.getInstance().getStuff("gameover-theme");
    }
    
    /**
     * Update the game logic / receives the frametime
     * @param frametime
     */
    @Override
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
        if (this.gameState.getCurrentState() == StateMachine.STAGING) {
            this.framecounter += frametime;
            if (this.framecounter == frametime) {
                this.scenario.update(frametime);
                this.frog.update(frametime);
                this.hud.update(frametime);
                this.timer.update(frametime);
            } else {
                this.message.update(frametime);
                if (!this.message.finished()) {
                    this.message.showStageAnnouncement();
                } else {
                    this.framecounter = 0;
                    this.message.showing(false);
                    this.message.toogleStageAnnouncement();
                    this.gameState.setCurrentState(StateMachine.IN_GAME);
                }
            }
        } else if (this.gameState.getCurrentState() == StateMachine.IN_GAME) {
            this.framecounter += frametime;
            if (this.framecounter == frametime) {
                this.theme.playContinuously();
            }

            this.scenario.update(frametime);
            this.frog.update(frametime);
            this.hud.update(frametime);
            this.timer.update(frametime);
            this.message.update(frametime);
            
            if (this.frog.getLives() == 0) { //after possible colision, check lives.
                this.gameState.setCurrentState(StateMachine.GAME_OVER);
                this.framecounter = 0;
            }
        } else if (this.gameState.getCurrentState() == StateMachine.GAME_OVER) {
            this.framecounter += frametime;
            if (this.framecounter >= 7_000_000_000L) {
                this.framecounter = 0;
                this.frog.resetLives();
                this.softReset();
                this.theme.playContinuously();
                this.gameState.setCurrentState(StateMachine.IN_GAME);
            } else if (this.framecounter == frametime) { //run just once
                this.theme.stop();
                this.gameoverTheme.play();
            }
        }
    }

    /**
     * Draw the game / receives the frametime
     * WTMD: This method draw the current screen, some steps described here:
                1) Clear the stage
     * @param frametime
     */
    @Override
    public synchronized void draw(long frametime) {

        //this graphical device (g2d) points to backbuffer, so, we are making things behide the scenes
        //clear the stage
        this.g2d.setBackground(Color.BLACK);
        this.g2d.clearRect(0, 0, this.resolutionW, this.resolutionH);

        //////////////////////////////////////////////////////////////////////
        // ->>>  draw the game elements
        //////////////////////////////////////////////////////////////////////
        if (this.gameState.getCurrentState() == StateMachine.STAGING) {
            this.message.draw(frametime);
        } else if (this.gameState.getCurrentState() == StateMachine.IN_GAME) {
            this.scenario.draw(frametime);
            this.frog.draw(frametime);
            this.hud.draw(frametime);
            this.message.draw(frametime);
            this.timer.draw(frametime);
        } else if (this.gameState.getCurrentState() == StateMachine.GAME_OVER) {
            this.gameOver.draw(frametime);
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
     * Mute / unmute the game theme
     */
    @Override
    public void toogleMuteTheme() {
        if (!this.mute) {
            this.theme.pause();
        } else {
            this.theme.playContinuously();
        }
        this.mute = !this.mute;
    }

    /**
     * Stop the theme position
     */
    @Override
    public void stopTheme() {
        this.theme.stop();
    }

    /**
     * Update game graphics
     * @param g2d
     */
    @Override
    public void updateGraphics2D(Graphics2D g2d) {
        this.g2d = g2d;
    }

    /**
     * Recupera o G2D
     * @return
     */
    public Graphics2D getG2D() {
        return (this.g2d);
    }

    /**
     * Toogle the pause button
     */
    @Override
    public void tooglePause() {
        this.toogleMuteTheme();
        this.frog.tooglePause();
        this.scenario.tooglePause();
        this.timer.tooglePause();
    }

    /**
     * Game reset
     */
    @Override
    public void softReset() {
        this.scenario.getVehicles().reset();
        this.scenario.getTrunks().reset();
        this.scenario.getTurtles().reset();
        this.scenario.getDockers().reset();
        this.hud.reset();
        this.timer.reset();
        this.frog.frogReset();
    }

    /**
     * Game keypress
     */
    public void keyPressed(int keyCode) {
        if (this.canContinue) {
            this.canContinue = false;
            this.movement(keyCode);
        }
    }

    /**
     * Game keyRelease
     */
    public void keyReleased(int keyCode) {
        this.canContinue = true;
        if (keyCode == 77) {this.toogleMuteTheme();}
        if (keyCode == 80) {this.tooglePause();}
        if (keyCode == 82) {this.softReset();}
    }

    /**
     * Accessor methods
     * @return
     */
    public Scenario getScenario()       {   return (this.scenario); }
    public HUD getHud()                 {   return this.hud;        }
    public Frog getFrog()               {   return this.frog;       }
    public GameOver getGameOver()       {   return this.gameOver;   }
    public StateMachine getGameState()  {   return this.gameState;  }
    public Message getMessages()        {   return this.message;    }
    public Timer getTimer()             {   return this.timer;      }

    @Override
    public int getInternalResolutionWidth() {
        return (this.wwm);
    }

    @Override
    public int getInternalResolutionHeight() {
        return (this.whm + this.HUDHeight);
    }
}