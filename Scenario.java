import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.image.VolatileImage;
import java.awt.image.BufferedImage;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import interfaces.IGame;

/*
    WTCD: This class represents the Scenario and traps of the Game
*/
public class Scenario {

    //Scenario variables
    private int windowWidth                 = 0;
    private int windowHeight                = 0;
    private int scoreHeight                 = 0;
    private VolatileImage bgBufferImage     = null;
    private Graphics2D bg2d                 = null;
    private Vehicles vehicles               = null;
    private Trunks trunks                   = null;
    private Dockers dockers                 = null;
    private Turtles turtles                 = null;
    private BufferedImage sidewalk          = null;
    private BufferedImage grass             = null;
    private BufferedImage subgrass          = null;
    private BufferedImage frogHome          = null;
    private volatile boolean showElements   = true;
    private volatile boolean reseting       = false;
    private IGame gameRef                   = null;

    //how many tiles in x and in y
    protected final byte tilesInX           = 21;
    protected final byte tilesInY           = 13;
    
    //each tile propertie
    private final byte tileX                = 64;
    private final byte tileY                = 64;
    protected final byte halfTileX          = (byte)(tileX / 2);
    protected final byte halfTileY          = (byte)(tileY / 2);

    public int getScoreHeight() {
        return (this.scoreHeight);
    }

    /**
     * Set to the next stage
     */
    public synchronized void nextStage() {
        //disable elements drawing
        this.toogleElements();

        //disable elements update
        this.toogleReseting();

        //recreate all elements
        this.vehicles.nextStage();
        this.trunks.nextStage();
        this.turtles.nextStage();
        this.dockers.nextStage();

        //enable elements update
        this.toogleReseting();

        //enable elements drawing
        this.toogleElements();
    }

    /**
     * Scenario Constructor
     * @param game
     * @param windowWidth
     * @param windowHeight
     */
    public Scenario(IGame game, int windowWidth, int windowHeight, int scoreHeight) {
        this.scoreHeight    = scoreHeight;
        this.windowHeight   = windowHeight;
        this.windowWidth    = windowWidth;
        this.gameRef        = game;

        //the game is construct having one resolution as target
        //any different resolution is streched in the final process
        //the code below, allow another approuche, calculating everything in a dynamic way (more cpu intensive)
        this.vehicles       = new Vehicles(this, windowWidth, windowHeight);
        this.trunks         = new Trunks(this, windowWidth, windowHeight);
        this.turtles        = new Turtles(this, windowWidth, windowHeight);
        this.dockers        = new Dockers(this);
        this.drawBackgroundInBuffer();

        final int imagePosX = 1;
        final int imagePosY = 34;

        BufferedImage animals   = (BufferedImage)LoadingStuffs.getInstance().getStuff("animalTiles");
        this.frogHome           = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(43, 45, Transparency.BITMASK);
        this.frogHome.getGraphics().drawImage(animals, 0, 0, this.frogHome.getWidth(), this.frogHome.getHeight(), 
                                                       imagePosX, imagePosY, imagePosX + this.frogHome.getWidth(), imagePosY + this.frogHome.getHeight(), null);
    }

    /**
     * Getters
     * @return
     */
    public byte getTileX()          {   return this.tileX;          }
    public byte getTileY()          {   return this.tileY;          }
    public byte getHalfTileY()      {   return this.halfTileY;      }
    public int getWindowWidth()     {   return this.windowWidth;    }
    public int getWindowHeight()    {   return this.windowHeight;   }

    /**
     * This private method construct the BG just once.
     * Than, when necessary it is ploted in the backbuffer.
     */
    private void drawBackgroundInBuffer() {
        if (this.bg2d == null) {

            //Get the already loaded image from loader
            this.sidewalk       = (BufferedImage)LoadingStuffs.getInstance().getStuff("sidewalk");
            this.grass          = (BufferedImage)LoadingStuffs.getInstance().getStuff("grass");
            this.subgrass       = (BufferedImage)LoadingStuffs.getInstance().getStuff("subgrass");

            //create a backbuffer image for doublebuffer
            byte lines          = (byte)(this.windowHeight / tileY);
            this.bgBufferImage  = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleVolatileImage(this.windowWidth, this.windowHeight);
            this.bg2d           = (Graphics2D)bgBufferImage.getGraphics();

            //paint all bg in black
            this.bg2d.setBackground(Color.BLACK);
            this.bg2d.clearRect(0, 0, this.windowWidth, this.windowHeight);
            
            //paint each lane
            this.bg2d.setColor(new Color(0, 0, 100));
            this.bg2d.fillRect(0, 0, this.windowWidth, this.tileY);
            this.bg2d.drawImage(this.grass, 0, 0, 72, this.grass.getHeight(),     //dest w1, h1, w2, h2
                                            107, 0, 179, this.grass.getHeight(),  //source w1, h1, w2, h2
                                            null);

            this.bg2d.drawImage(this.grass, (0 + 96 + 72), 0, (this.grass.getWidth() + 96 + 72), this.grass.getHeight(),     //dest w1, h1, w2, h2
                                            1, 0, this.grass.getWidth() - 2, this.grass.getHeight(),                         //source w1, h1, w2, h2
                                            null);
            
            this.bg2d.drawImage(this.grass, (0 + 96 + 348), 0, (this.grass.getWidth() + 96 + 348), this.grass.getHeight(),   //dest w1, h1, w2, h2
                                            1, 0, this.grass.getWidth() - 2, this.grass.getHeight(),                         //source w1, h1, w2, h2
                                            null);

            this.bg2d.drawImage(this.grass, (0 + 96 + 624), 0, (this.grass.getWidth() + 96 + 624), this.grass.getHeight(),   //dest w1, h1, w2, h2
                                            1, 0, this.grass.getWidth() - 2, this.grass.getHeight(),                         //source w1, h1, w2, h2
                                            null);

            this.bg2d.drawImage(this.grass, (0 + 96 + 900), 0, (this.grass.getWidth() + 96 + 900), this.grass.getHeight(),   //dest w1, h1, w2, h2
                                            1, 0, this.grass.getWidth() - 2, this.grass.getHeight(),                         //source w1, h1, w2, h2
                                            null);
            
            this.bg2d.drawImage(this.grass, (0 + 96 + 1176), 0, (this.grass.getWidth() + 96 + 1176), this.grass.getHeight(),   //dest w1, h1, w2, h2
                                            1, 0, this.grass.getWidth() - 2, this.grass.getHeight(),                         //source w1, h1, w2, h2
                                            null);
            
            this.bg2d.setColor(new Color(0, 0, 100));
            this.bg2d.fillRect(0, tileY, this.windowWidth, this.tileY);
            this.bg2d.fillRect(0, tileY * 2, this.windowWidth, this.tileY);
            this.bg2d.fillRect(0, tileY * 3, this.windowWidth, this.tileY);
            this.bg2d.fillRect(0, tileY * 4, this.windowWidth, this.tileY);
            this.bg2d.fillRect(0, tileY * 5, this.windowWidth, this.tileY);

            //middle lane
            this.bg2d.setColor(new Color(0, 0, 100));
            this.bg2d.fillRect(0, tileY * 6, this.windowWidth, this.tileY - 5);
            
            //final lane (goal)
            this.bg2d.setColor(new Color(0, 0, 0));
            this.bg2d.fillRect(0, tileY * 12, this.windowWidth, this.tileY);

            //paint each frogger dock
            this.bg2d.setColor(new Color(20, 20, 20));
            //first dock

            this.bg2d.drawImage(this.subgrass, 72, 0, this.subgrass.getWidth() + 72, this.subgrass.getHeight(),     //dest w1, h1, w2, h2
                                               0, 0, this.subgrass.getWidth(), this.subgrass.getHeight(),  //source w1, h1, w2, h2
                                               null);
            //second dock
            this.bg2d.drawImage(this.subgrass, 348, 0, this.subgrass.getWidth() + 348, this.subgrass.getHeight(),     //dest w1, h1, w2, h2
                                               0, 0, this.subgrass.getWidth(), this.subgrass.getHeight(),  //source w1, h1, w2, h2
                                               null);
            //third dock
            this.bg2d.drawImage(this.subgrass, 624, 0, this.subgrass.getWidth() + 624, this.subgrass.getHeight(),     //dest w1, h1, w2, h2
                                               0, 0, this.subgrass.getWidth(), this.subgrass.getHeight(),  //source w1, h1, w2, h2
                                               null);
            //fourth dock
            this.bg2d.drawImage(this.subgrass, 900, 0, this.subgrass.getWidth() + 900, this.subgrass.getHeight(),     //dest w1, h1, w2, h2
                                               0, 0, this.subgrass.getWidth(), this.subgrass.getHeight(),  //source w1, h1, w2, h2
                                               null);
            //fifth dock
            this.bg2d.drawImage(this.subgrass, 1176, 0, this.subgrass.getWidth() + 1176, this.subgrass.getHeight(),     //dest w1, h1, w2, h2
                                               0, 0, this.subgrass.getWidth(), this.subgrass.getHeight(),  //source w1, h1, w2, h2
                                               null);

            //draw lane lines
            for (byte i = 8; i < lines - 1; i++) {
                this.bg2d.setStroke(new BasicStroke());
                this.bg2d.setColor(new Color(150, 150, 0));
                this.bg2d.drawLine(0, (tileY * i), this.windowWidth, (tileY * i));
                this.bg2d.drawLine(0, (tileY * i)+1, this.windowWidth, (tileY * i)+1);
            }

            //draw lane inlines
            for (byte i = 7; i < lines - 1; i++) {
                this.bg2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
                this.bg2d.setColor(new Color(150, 150, 0, 50));
                this.bg2d.drawLine(0, (tileY * i) + halfTileY, this.windowWidth, (tileY * i) + halfTileY);
            }

            //fixed image source sizes
            byte bgImageW = 45;
            byte bgImageH = 64;

            //draw the safe spot (middle and bottom)
            for (int i = 0; i < 31; i++) {
                this.bg2d.drawImage(this.sidewalk, i * bgImageW, (6 * tileY), ((i * bgImageW) + bgImageW), (7 * tileY),     //dest w1, h1, w2, h2
                                                   0, 0, bgImageW, bgImageH,                                                //source w1, h1, w2, h2
                                                   null);
                this.bg2d.drawImage(this.sidewalk, i * bgImageW, (12 * tileY), ((i * bgImageW) + bgImageW), (13 * tileY),   //dest w1, h1, w2, h2
                                                   0, 0, bgImageW, bgImageH,                                                //source w1, h1, w2, h2
                                                   null);
            }
        }
    }

    /**
     * Update the scenario and its elements
     * @param frametime
     */
    public void update(long frametime) {
        if (!this.reseting) {
            this.vehicles.update(frametime);
            this.trunks.update(frametime);
            this.turtles.update(frametime);
            this.dockers.update(frametime);
        }
    }

    /**
     * 
     * @param frametime
     */
    public void draw(long frametime) {
        //After construct the bg once, copy it to the graphic device
        this.gameRef.getG2D().drawImage(this.bgBufferImage, 0, this.scoreHeight, null);
        short xPos = 0;
        for (int cnt = 0; cnt < dockers.getIsInDock().length; cnt++) {
            if (dockers.getIsInDock()[cnt]) {
                xPos = (short)((cnt * 276) + 99);
                this.gameRef.getG2D().drawImage(this.frogHome,   xPos, 
                                                                 this.scoreHeight + 16, 
                                                                 xPos + this.frogHome.getWidth(), 
                                                                 this.frogHome.getHeight() + 16 + this.scoreHeight, //destine
                                                               0, 0, this.frogHome.getWidth(), this.frogHome.getHeight(), null); //source
            }
        }

        //draw the vehicles
        if (this.showElements) {
            this.vehicles.draw(frametime);
            this.trunks.draw(frametime);
            this.turtles.draw(frametime);
            this.dockers.draw(frametime);
        }
    }

    /**
     * Accessors
     * @return
     */
    public Vehicles getVehicles()   {   return (this.vehicles); }
    public Trunks getTrunks()       {   return (this.trunks);   }
    public Turtles getTurtles()     {   return (this.turtles);  }
    public Dockers getDockers()     {   return (this.dockers);  }

    /**
     * Toogle the pause button
     */
    public void tooglePause() {
        this.vehicles.toogleStop();
        this.trunks.toogleStop();
        this.turtles.toogleStop();
        this.dockers.toogleStop();
    }

    public synchronized void toogleElements() {
        this.showElements = !this.showElements;
    }

    public synchronized void toogleReseting() {
        this.reseting = !this.reseting;
    }

    public IGame getGameRef() {
        return (this.gameRef);
    }
}