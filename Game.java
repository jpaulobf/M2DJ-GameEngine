import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.VolatileImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.RenderingHints;

/*
    Project:    Modern 2D Java Game Engine
    Purpose:    Provide basics functionalities to write 2D games in Java in a more modern approach
    Author:     Joao P. B. Faria
    Date:       Octuber 2021
    WTCD:       This class, provides a stage for the game.
*/
public class Game extends JFrame {

    private static final long serialVersionUID  = 1L;

    //this window properties
    private int positionX                       = 0;
    private int positionY                       = 0;

    //width and height of the window
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
    private boolean showFPS                     = true;
    //width and height of window for base metrics of the game
    private int wwm                             = 1344;
    private int whm                             = 832;


    //the game variables go here...
    private Scenario scenario = null;
    Frog frog = null;

    /*
        WTMD: some responsabilites here:
    */
    public Game() {

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
        
        //////////////////////////////////////////////////////////////////////
        // ->>>  now, for the canvas
        //////////////////////////////////////////////////////////////////////

        //initialize the canvas
        this.canvas = new JPanel(null);
        this.canvas.setSize(windowWidth, windowHeight);
        this.canvas.setBackground(Color.BLACK);
        this.setVisible(true);
        this.canvas.setOpaque(true);
        
        //final parameters for the window
        this.add(canvas);
        this.pack();
        this.setLocationRelativeTo(null);

        //show the game screen
        this.setVisible(true);
        this.requestFocus();

        //////////////////////////////////////////////////////////////////////
        // ->>>  create the game elements objects
        //////////////////////////////////////////////////////////////////////
        scenario = new Scenario(g2d, this.wwm, this.whm);
        frog = new Frog(g2d, scenario);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                frog.move(e.getKeyCode());
            }
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 27) {setVisible(false); System.exit(0);}
            }
        });
    }

    public void update(long frametime) {

        //how many pixels per second I want?
        //ex.: movement at 200px/s
        //To do so, I need to divide 1_000_000_000 by the exactly frametime to know the current FPS
        //With this number in hand,  divide the pixel distance by the current fps
        //this must be your maximum movement amount in pixels
        //example:
        //double movementPerSecond = 100D;
        //double step = movementPerSecond / (double)(1_000_000_000D / (double)frametime);


        //////////////////////////////////////////////////////////////////////
        // ->>>  update the game elements
        //////////////////////////////////////////////////////////////////////
        scenario.update(frametime);
        frog.update(frametime);
      
    }
    
    /*
        WTMD: This method draw the current screen, some steps described here:
            1) Clear the stage
        */
    public void draw(long frametime) {

        //update the window size variables if the user resize it.
        this.windowHeight   = this.getHeight();
        this.windowWidth    = this.getWidth();

        if (this.g2d != null) {
            
            //this graphical device (g2d) points to backbuffer, so, we are making things behide the scenes
            //clear the stage
            this.g2d.setBackground(Color.BLACK);
            this.g2d.clearRect(0, 0, this.resolutionW, this.resolutionH);

            //////////////////////////////////////////////////////////////////////
            // ->>>  draw the game elements
            //////////////////////////////////////////////////////////////////////
            scenario.draw(frametime);
            frog.draw(frametime);

            //verify if the user want to show the FPS
            if (this.showFPS) {
                this.g2d.setColor(new Color(255, 255, 255, 80));
                this.g2d.drawString("fps: " + (int)(1_000_000_000D / frametime), this.wwm - 70, 20);
            }

            //At least, copy the backbuffer to the canvas screen
            this.canvas.getGraphics().drawImage(this.bufferImage, 0, 0, this.windowWidth, this.windowHeight, 
                                                                  0, 0, this.wwm, this.whm, this);
        }
    }

    /*
        Description: main method
    */
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new GameEngine(0), "engine");
        thread.start();
    }
}
