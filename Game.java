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

/*
    Project:    Modern 2D Java Game Engine
    Purpose:    Provide basics functionalities to write 2D games in Java in a more modern approach
    Author:     Mr. Joao P. B. Faria
    Date:       Octuber 2021
    WTCD:       This class, provides a stage for the game.
*/
public class Game extends JFrame {

    private static final long serialVersionUID  = 1L;

    //this window properties
    private int positionX                       = 0;
    private int positionY                       = 0;
    private int windowWidth                     = 800;
    private int windowHeight                    = 600;

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

    //this screen control logic parameter   
    private int selectedItem                    = 0;
    
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
        
        //add a keylistener
        this.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 39) {if (selectedItem < 2) { selectedItem++;paint(g2d);}}
                if (e.getKeyCode() == 37) {if (selectedItem > 0) {selectedItem--;paint(g2d);}}
                if (e.getKeyCode() == 27) {setVisible(false); System.exit(0);}
            }
        });

        //create the backbuffer from the size of screen resolution to avoid any resize process penalty
        this.ge             = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.dsd            = ge.getDefaultScreenDevice();
        this.bufferImage    = dsd.getDefaultConfiguration().createCompatibleVolatileImage(this.resolutionW, this.resolutionH);
        this.g2d            = (Graphics2D)bufferImage.getGraphics();
        
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
        this.setVisible(true);
        this.requestFocus();
    }

    public void update(long frametime) {

    }
    
    /*
        WTMD: This method draw the current screen, some steps described here:
            1) Clear the stage
        */
    public void draw(long frametime) {

        //update the window size variables if the user resize it.
        this.windowHeight = this.getHeight();
        this.windowWidth  = this.getWidth();

        if (this.g2d != null) {
            
            //this graphical device (g2d) points to backbuffer, so, we are making things behide the scenes
            //clear the stage
            this.g2d.setBackground(Color.BLACK);
            this.g2d.clearRect(0, 0, this.resolutionW, this.resolutionH);

            this.g2d.setColor(Color.WHITE);
            this.g2d.drawString(frametime + "", 100, 100);
    
            //At least, copy the backbuffer to the canvas screen
            this.canvas.getGraphics().drawImage(this.bufferImage, 0, 0, this);
        }
    }

    /*
        Description: main method
    */
    public static void main(String[] args) throws Exception {
        Thread thread1 = new Thread(new GameEngine(), "engine");
        thread1.start();
    }
}
