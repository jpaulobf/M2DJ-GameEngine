import javax.swing.JFrame;
import javax.swing.JPanel;
import interfaces.Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.RenderingHints;
import java.awt.EventQueue;

/*
    Project:    Modern 2D Java Game Engine
    Purpose:    Provide basics functionalities to write 2D games in Java in a more modern approach
    Author:     Joao P. B. Faria
    Date:       Octuber 2021
    WTCD:       This class, provides a stage for the game.
*/
public class Frogger extends JFrame implements Game {

    private static final long serialVersionUID  = 1L;

    //this window properties
    private int positionX                       = 0;
    private int positionY                       = 0;

    //width and height of the window
    //private int windowWidth                   = 1240;
    //private int windowHeight                  = 700;
    private int windowWidth                     = 1344;
    private int windowHeight                    = 832;

    //desktop properties
    private int resolutionH                     = 0;
    private int resolutionW                     = 0;
    
    //the first 'canvas' & the backbuffer (for simple doublebuffer strategy)
    private JPanel canvas                       = null;
    private VolatileImage bufferImage           = null;

    //some support and the graphical device itself
    private GraphicsEnvironment ge              = null;
    private GraphicsDevice dsd                  = null;
    private Graphics2D g2d                      = null;
    private BufferStrategy bufferStrategy       = null;
    private boolean showFPS                     = true;
    //width and height of window for base metrics of the game
    private int wwm                             = 1344;
    private int whm                             = 832;

    //the game statemachine goes here
    private StateMachine gameState              = null;

    //the game variables go here...
    private Scenario scenario                   = null;
    private Frog frog                           = null;
    private GameOver gameOver                   = null;
    private volatile boolean canContinue        = true;
    private boolean fullscreen                  = false;
    private boolean isFullScreenAvailable       = false;
    private long framecounter                   = 0;

    /*
        WTMD: some responsabilites here:
    */
    public Frogger() {

        //////////////////////////////////////////////////////////////////////
        //set some properties for this window
        //////////////////////////////////////////////////////////////////////
        Dimension basic = new Dimension(this.windowWidth, this.windowHeight);
        this.setPreferredSize(basic);
        this.setMinimumSize(basic);
        this.setUndecorated(true);

        //default operation on close (exit in this case)
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //recover the desktop resolution
        Dimension size = Toolkit.getDefaultToolkit(). getScreenSize();

        //and save this values
        this.resolutionH = (int)size.getHeight();
        this.resolutionW = (int)size.getWidth();

        //center the current window regards the desktop resolution
        this.positionX = (int)((size.getWidth() / 2) - (this.windowWidth / 2));
        this.positionY = (int)((size.getHeight() / 2) - (this.windowHeight / 2));
        this.setLocation(this.positionX, this.positionY);

        //create the backbuffer from the size of screen resolution to avoid any resize process penalty
        this.ge             = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.dsd            = ge.getDefaultScreenDevice();
        this.bufferImage    = dsd.getDefaultConfiguration().createCompatibleVolatileImage(this.wwm, this.whm);
        this.g2d            = (Graphics2D)bufferImage.getGraphics();
        this.g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        //verify if fullscreen is posible
        this.isFullScreenAvailable = dsd.isFullScreenSupported();
        this.setUndecorated(true);
        this.setResizable(false);

        //////////////////////////////////////////////////////////////////////
        // ->>>  now, for the canvas
        //////////////////////////////////////////////////////////////////////
        //initialize the canvas
        this.canvas = new JPanel(null);
        this.canvas.setSize(windowWidth, windowHeight);
        this.canvas.setBackground(Color.BLACK);
        this.canvas.setOpaque(true);
        
        //final parameters for the window
        this.add(canvas);

        //verify if fullscreen mode is supported & desired
        if (fullscreen && isFullScreenAvailable) {
            // set to Full-screen mode
            this.setIgnoreRepaint(true);
            dsd.setFullScreenWindow(this);
            this.setBufferStrategy();
            validate();
        } else {
            this.pack();
            this.setLocationRelativeTo(null);
        }

        //show the game screen
        this.setVisible(true);
        this.requestFocus();

        //////////////////////////////////////////////////////////////////////
        // ->>>  create the game elements objects
        //////////////////////////////////////////////////////////////////////
        scenario    = new Scenario(g2d, this.wwm, this.whm);
        frog        = new Frog(g2d, scenario);
        gameOver    = new GameOver(g2d, this.wwm, this.whm);
        gameState   = new StateMachine(this);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public synchronized void keyPressed(KeyEvent e) {
                if (canContinue) {
                    canContinue = false;
                    frog.move(e.getKeyCode());
                }
            }
            @Override
            public synchronized void keyReleased(KeyEvent e) {
                canContinue = true;
                if (e.getKeyCode() == 27) {setVisible(false); System.exit(0);}
            }
        });
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
        if (this.gameState.getCurrentState() == StateMachine.IN_GAME) {
            scenario.update(frametime);
            frog.update(frametime);
            //after possible colision, check lives.
            if (frog.getLives() == 0) {
                this.gameState.setCurrentState(StateMachine.GAME_OVER);
            }
        } else if (this.gameState.getCurrentState() == StateMachine.GAME_OVER) {
            this.framecounter += frametime;
            if (framecounter >= 3_000_000_000L) {
                this.framecounter = 0;
                frog.frogReset();
                frog.resetLives();
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

        //update the window size variables if the user resize it.
        this.windowHeight   = this.getHeight();
        this.windowWidth    = this.getWidth();

        if (fullscreen && isFullScreenAvailable) {
            //set the buffer strategy
            this.g2d = (Graphics2D)this.bufferStrategy.getDrawGraphics();

            //render the game elements
            this.renderGameElements(frametime);

            //At least, copy the backbuffer to the backbuffer
            this.g2d.drawImage(this.bufferImage, 0, 0, this.windowWidth, this.windowHeight, 
                                                 0, 0, this.wwm, this.whm, this);

            this.renderFPSLayer(frametime, this.g2d);

            //show the buffer content
            this.g2d.dispose();
            if (!this.bufferStrategy.contentsLost()) {
                this.bufferStrategy.show();
            }
        } else {
            //verify if the Graphics element isn't lost
            if (this.g2d != null) {
            
                //render the game elements
                this.renderGameElements(frametime);
    
                this.renderFPSLayer(frametime, (Graphics2D)this.bufferImage.getGraphics());

                //At least, copy the backbuffer to the canvas screen
                this.canvas.getGraphics().drawImage(this.bufferImage, 0, 0, this.windowWidth, this.windowHeight, 
                                                                      0, 0, this.wwm, this.whm, this);
            }
        }
    }

    /**
     * Show FPS Layer
     * @param frametime
     * @param g
     */
    private void renderFPSLayer(long frametime, Graphics2D g) {
        //verify if the user want to show the FPS
        if (this.showFPS) {
            g.setColor(Color.WHITE);
            g.drawString("fps: " + (int)(1_000_000_000D / frametime), 10, 20);
        }
    }

    /**
     * Render the game elements
     * @param frametime
     */
    private void renderGameElements(long frametime) {
        //this graphical device (g2d) points to backbuffer, so, we are making things behide the scenes
        //clear the stage
        this.g2d.setBackground(Color.BLACK);
        this.g2d.clearRect(0, 0, this.resolutionW, this.resolutionH);
   
        //////////////////////////////////////////////////////////////////////
        // ->>>  draw the game elements
        //////////////////////////////////////////////////////////////////////
        //if (g2d != null) {
        //    scenario.setG2d(g2d);
        //    frog.setG2d(g2d);
        //}
        if (this.gameState.getCurrentState() == StateMachine.IN_GAME) {
            scenario.draw(frametime);
            frog.draw(frametime);
        } else if (this.gameState.getCurrentState() == StateMachine.GAME_OVER) {
            gameOver.draw(frametime);
        }
    }

    /**
     * Create the bufferstrategy
     */
    private void setBufferStrategy() { 
        try {
            EventQueue.invokeAndWait(new Runnable() {
                public void run() { 
                    createBufferStrategy(2); 
                }});
        } catch (Exception e) {
            System.exit(0);
        }
        try {     
            Thread.sleep(500);
        } catch(InterruptedException ex){}
        this.bufferStrategy = super.getBufferStrategy();
    }

    /*
        Description: main method
    */
    /*
    public static void main(String[] args) throws Exception {
        //enable openGL
        System.setProperty("sun.java2d.opengl", "True");

        //start the thread
        Thread thread = new Thread(new GameEngine(0, new Frogger()), "engine");
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }*/
}
