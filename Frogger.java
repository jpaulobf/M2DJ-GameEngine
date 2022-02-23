import javax.swing.JFrame;
import javax.swing.JPanel;
import interfaces.ICanvasEngine;
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
import java.awt.EventQueue;
import java.awt.RenderingHints;
import interfaces.IGame;

/*
    Project:    Modern 2D Java Game Engine
    Purpose:    Provide basics functionalities to write 2D games in Java in a more modern approach
    Author:     Joao P. B. Faria
    Date:       Octuber 2021
    WTCD:       This class, provides a stage for the game.
*/
public class Frogger implements Runnable {

    /**
     * Game Canvas
     */
    private class Canvas extends JFrame implements ICanvasEngine {

        private static final long serialVersionUID  = 1L;

        //this window properties
        private int positionX                       = 0;
        private int positionY                       = 0;

        //width and height of the window
        //private int windowWidth                   = 1079;
        //private int windowHeight                  = 700;
        private int windowWidth                     = 1344;
        private int windowHeight                    = 872;
        private int fullScreenWidth                 = 0;
        private int fullScreenHeight                = 0;
        private int fullScreenXPos                  = 0;
        private int fullScreenYPos                  = 0;
        private int fullscreenState                 = 0;
        
        //the first 'canvas' & the backbuffer (for simple doublebuffer strategy)
        private JPanel canvas                       = null;
        private IGame game                          = null;

        //some support and the graphical device itself
        private GraphicsEnvironment ge              = null;
        private GraphicsDevice dsd                  = null;
        private BufferStrategy bufferStrategy       = null;
        private Graphics2D g2d                      = null;
        private Dimension size                      = null;

        //the backbuffer (for simple doublebuffer strategy)
        private VolatileImage bufferImage           = null;

        //show or hide the game FPS
        private boolean showFPS                     = true;

        //control and fullscreen controller
        private boolean fullscreen                  = false;
        private boolean isFullScreenAvailable       = false;

        /**
         * Game canvas constructor
        */
        public Canvas() {
            //////////////////////////////////////////////////////////////////////
            //set some properties for this window
            //////////////////////////////////////////////////////////////////////

            //recover the desktop resolution
            this.size = Toolkit.getDefaultToolkit(). getScreenSize();

            //Verify if Windows width/height fits the current resolution
            // otherwise, resize it.
            if (this.windowHeight > this.size.getHeight()) {
                this.windowWidth = (int)((double)this.windowWidth / (double)this.windowHeight * (double)this.size.getHeight());
                this.windowHeight = (int)this.size.getHeight();
            } if (this.windowWidth > this.size.getWidth()) {
                this.windowHeight = (int)((double)this.windowHeight * (double)this.size.getWidth() / (double)this.windowWidth);
                this.windowWidth = (int)this.size.getWidth();
            }

            Dimension basic = new Dimension(this.windowWidth, this.windowHeight);
            this.setPreferredSize(basic);
            this.setMinimumSize(basic);
            this.setUndecorated(true);

            //default operation on close (exit in this case)
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);

            //center the current window regards the desktop resolution
            this.positionX  = (int)((size.getWidth() / 2) - (this.windowWidth / 2));
            this.positionY  = (int)((size.getHeight() / 2) - (this.windowHeight / 2));
            this.setLocation(this.positionX, this.positionY);

            //create the backbuffer from the size of screen resolution to avoid any resize process penalty
            this.ge             = GraphicsEnvironment.getLocalGraphicsEnvironment();
            this.dsd            = ge.getDefaultScreenDevice();
            this.bufferImage    = dsd.getDefaultConfiguration().createCompatibleVolatileImage((int)size.getWidth()*2, (int)size.getHeight()*2);
            this.g2d            = (Graphics2D)bufferImage.getGraphics();
            this.g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

            //verify if fullscreen is posible
            this.isFullScreenAvailable = dsd.isFullScreenSupported();
            this.setUndecorated(true);
            this.setResizable(false);

            this.fullScreenHeight   = (int)size.getHeight();
            this.fullScreenWidth    = (int)size.getWidth();
            this.fullScreenXPos     = 0;
            this.fullScreenYPos     = 0;

            //////////////////////////////////////////////////////////////////////
            // ->>>  now, for the canvas
            //////////////////////////////////////////////////////////////////////
            //initialize the canvas
            this.canvas = new JPanel(null);
            this.canvas.setSize(this.windowWidth, this.windowHeight);
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

            //start the game controller
            this.game = GameFactory.getGameInstance(g2d, size);

            //KeyListener
            this.addKeyListener(new KeyAdapter() {
                @Override
                public synchronized void keyPressed(KeyEvent e) {
                    game.keyPressed(e.getKeyCode());
                }
                @Override
                public synchronized void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == 113) {toogleFullscreenResolution();}
                    if (e.getKeyCode() == 27) {setVisible(false); System.exit(0);}
                    game.keyReleased(e.getKeyCode());
                }
            });     

            //show the game screen
            this.setVisible(true);
            this.requestFocus();
        }

        /**
         * Update the game logic / receives the frametime
         * @param frametime
         */
        public synchronized void update(long frametime) {
            this.game.update(frametime);
        }

        /**
         * Change screen stretch on the fly (F2)
         */
        public void toogleFullscreenResolution() {
            this.fullscreenState = (this.fullscreenState + 1)%3;

            switch (this.fullscreenState) {
                case 0:
                    this.fullScreenHeight   = (int)size.getHeight();
                    this.fullScreenWidth    = (int)this.getWidth();
                    this.fullScreenXPos     = 0;
                    this.fullScreenYPos     = 0;
                    break;
                case 1:
                    // calc fullscreen width/height
                    this.fullScreenHeight   = (int)size.getHeight();
                    this.fullScreenWidth    = (size.getHeight() > this.windowHeight)?(int)((double)this.windowWidth/(double)this.windowHeight*(double)size.getHeight()):this.getWidth();
                    this.fullScreenXPos     = (int)((size.getWidth() - this.fullScreenWidth) / 2);
                    this.fullScreenYPos     = (int)((size.getHeight() - this.fullScreenHeight) / 2);
                    this.fullScreenWidth    += this.fullScreenXPos;
                    this.fullScreenHeight   += this.fullScreenYPos;
                    break;
                case 2:
                    this.fullScreenHeight   = this.windowHeight;
                    this.fullScreenWidth    = this.windowWidth;
                    this.fullScreenXPos     = (int)((size.getWidth() - this.fullScreenWidth) / 2);
                    this.fullScreenYPos     = (int)((size.getHeight() - this.fullScreenHeight) / 2);
                    this.fullScreenWidth    += this.fullScreenXPos;
                    this.fullScreenHeight   += this.fullScreenYPos;
                    break;
            }
        }
        
        /**
         * Draw the game / receives the frametime
         * WTMD: This method draw the current screen, some steps described here:
                    1) Clear the stage
        * @param frametime
        */
        public synchronized void draw(long frametime) {

            if (fullscreen && isFullScreenAvailable) {
                //set the buffer strategy
                this.g2d = (Graphics2D)this.bufferStrategy.getDrawGraphics();
                this.g2d.setBackground(Color.BLACK);
                this.g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

                //update the game graphics
                //this.game.updateGraphics2D(this.g2d);

                //render the game elements
                this.game.draw(frametime);

                //At least, copy the backbuffer to the backbuffer
                this.g2d.drawImage(this.bufferImage, this.fullScreenXPos, this.fullScreenYPos, this.fullScreenWidth, this.fullScreenHeight,  //destine
                                                     0, 0, this.game.getInternalResolutionWidth(), this.game.getInternalResolutionHeight(), // source
                                                     this);

                //render the fps counter
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
                    this.game.draw(frametime);
        
                    //render the fps counter
                    this.renderFPSLayer(frametime, this.g2d);

                    //At least, copy the backbuffer to the canvas screen
                    this.canvas.getGraphics().drawImage(this.bufferImage, 0, 0, this.windowWidth, this.windowHeight, //destine
                                                                          0, 0, game.getInternalResolutionWidth(), 
                                                                          game.getInternalResolutionHeight(), //source
                                                                          this);
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
         * Create the bufferstrategy
         */
        private void setBufferStrategy() { 
            try {
                EventQueue.invokeAndWait(new Runnable() {
                    public void run() { 
                        createBufferStrategy(3); 
                    }});
            } catch (Exception e) {
                System.exit(0);
            }
            try {     
                Thread.sleep(500);
            } catch(InterruptedException ex){}
            this.bufferStrategy = super.getBufferStrategy();
        }
    }

    /**
     * Class of GameEngine
     */
    private class GameEngine implements Runnable {

        private boolean isEngineRunning     = true;
        private long FPS240                 = (long)(1_000_000_000 / 240);
        private long FPS120                 = (long)(1_000_000_000 / 120);
        private long FPS90                  = (long)(1_000_000_000 / 90);
        private long FPS60                  = (long)(1_000_000_000 / 60);
        private long FPS30                  = (long)(1_000_000_000 / 30);
        private long TARGET_FRAMETIME       = FPS60;
        private boolean UNLIMITED_FPS       = false;
        private ICanvasEngine game          = null;
    
        /*
            WTMD: constructor
                    receives the target FPS (0, 30, 60, 120, 240) and starts the engine
        */
        public GameEngine(int targetFPS, ICanvasEngine game) {
    
            this.UNLIMITED_FPS = false;
            switch(targetFPS) {
                case 30:
                    this.TARGET_FRAMETIME = FPS30;
                    break;
                case 60:
                    this.TARGET_FRAMETIME = FPS60;
                    break;
                case 90:
                    this.TARGET_FRAMETIME = FPS90;
                    break;
                case 120:
                    this.TARGET_FRAMETIME = FPS120;
                    break;
                case 240:
                    this.TARGET_FRAMETIME = FPS240;
                    break;
                case 0:
                    this.UNLIMITED_FPS = true;
                    break;
                default:
                    this.TARGET_FRAMETIME = FPS30;
                    break;
            }
            this.game = game;
        }
        
        /* Método de execução da thread */
        @SuppressWarnings("unused") //just temporary, for the counter variable... delete this...
        public void run() {
            long timeReference      = System.nanoTime();
            long beforeUpdate       = 0;
            long afterUpdate        = 0;
            long beforeDraw         = 0;
            long afterDraw          = 0;
            long accumulator        = 0;
            long timeElapsed        = 0;
            long timeStamp          = 0;
            long counter            = 0;
    
            if (UNLIMITED_FPS) {
                while (isEngineRunning) {
    
                    //mark the time before the iteration
                    timeStamp = System.nanoTime();
    
                    //compute the time from previous iteration and the current
                    timeElapsed = (timeStamp - timeReference);
    
                    //save the difference in an accumulator to control the pacing
                    accumulator += timeElapsed;
    
                    //update the game (gathering input from user, and processing the necessary games updates)
                    this.update(timeElapsed);
    
                    //draw
                    this.draw(timeElapsed);
    
                    //update the referencial time with the initial time
                    timeReference = timeStamp;
                }
            } else {
                
                while (isEngineRunning) {
    
                    accumulator = 0;
    
                    //calc the update time
                    beforeUpdate = System.nanoTime();
    
                    //update the game (gathering input from user, and processing the necessary games updates)
                    this.update(TARGET_FRAMETIME);
    
                    //get the timestamp after the update
                    afterUpdate = System.nanoTime() - beforeUpdate;
                    
                    //only draw if there is some (any) enough time
                    if ((TARGET_FRAMETIME - afterUpdate) > 0) {
                        
                        beforeDraw = System.nanoTime();
    
                        //draw
                        this.draw(TARGET_FRAMETIME);
                        
                        //and than, store the time spent
                        afterDraw = System.nanoTime() - beforeDraw;
                    }
    
                    //reset the accumulator
                    accumulator = TARGET_FRAMETIME - (afterUpdate + afterDraw);
    
                    if (accumulator > 0) {
                        try {
                            Thread.sleep((long)(accumulator * 0.000001));
                        } catch (Exception e) {}
                    } else {
                        /*  
                        Explanation:
                            if the total time to execute, consumes more miliseconds than the target-frame's amount, 
                            is necessary to keep updating without render, to recover the pace.
                        Important: Something here isn't working with very slow machines. 
                                   So, this compensation have to be re-tested with this new approuch (exiting beforeUpdate).
                                   Please test this code with your scenario.
                        */
                        //System.out.println("Skip 1 frame... " + ++counter + " time(s)");
                        if (accumulator < 0) {
                            this.update(TARGET_FRAMETIME);
                        }
                    }
                }
            }
        }
    
        /* Método de update, só executa quando a flag permite */
        public void update(long frametime) {
            this.game.update(frametime);
        }
    
        /* Método de desenho, só executa quando a flag permite */
        public void draw(long frametime) {
            this.game.draw(frametime);
        }
    }

    //target FPS
    private int targetFPS = 0;

    /**
     * Thread Constructor
     * @param targetFPS
     */
    public Frogger(int targetFPS) {
        this.targetFPS  = targetFPS;
    }

    /**
     * Run the gameengine
     */
    @Override
    public void run() {
        new GameEngine(this.targetFPS, new Canvas()).run();
    }
}