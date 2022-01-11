import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.image.VolatileImage;
import java.awt.GraphicsEnvironment;

/*
    WTCD: This class represents the Scenario and traps of the Game
*/
public class Scenario {

    //Scenario variables
    private Graphics2D g2d              = null;
    private int windowWidth             = 0;
    private int windowHeight            = 0;
    private VolatileImage bgBufferImage = null;
    private Graphics2D bgd2             = null;
    private Vehicles vehicles           = null;
    private Trunks trunks               = null;
    private VolatileImage sidewalk      = null;
    private VolatileImage grass         = null;
    private VolatileImage subgrass      = null;

    //how many tiles in x and in y
    protected final byte tilesInX       = 21;
    protected final byte tilesInY       = 13;
    
    //each tile propertie
    private final byte tileX            = 64;
    private final byte tileY            = 64;
    protected final byte halfTileX      = (byte)(tileX / 2);
    protected final byte halfTileY      = (byte)(tileY / 2);

    //getters
    public Vehicles getVehicles() {
        return (this.vehicles);
    }

    /**
     * Constructor
     * @param g2d
     * @param windowWidth
     * @param windowHeight
     */
    public Scenario(Graphics2D g2d, int windowWidth, int windowHeight) {
        this.g2d            = g2d;
        this.windowHeight   = windowHeight;
        this.windowWidth    = windowWidth;

        //the game is construct having one resolution as target
        //any different resolution is streched in the final process
        //the code below, allow another approuche, calculating everything in a dynamic way (more cpu intensive)
        /*this.tileX          = (byte)(this.windowWidth / tilesInX);
        this.tileY          = (byte)(this.windowHeight / tilesInY);
        this.halfTileX      = (byte)(tileX / 2);
        this.halfTileY      = (byte)(tileY / 2);*/

        this.vehicles       = new Vehicles(g2d, windowWidth, windowHeight);
        this.trunks         = new Trunks(g2d, windowWidth, windowHeight);
        this.drawBackgroundInBuffer();
    }

    /**
     * Getters
     * @return
     */
    public byte getTileX()      {return this.tileX;}
    public byte getTileY()      {return this.tileY;}
    public byte getHalfTileY()  {return this.halfTileY;}

    /**
     * This private method construct the BG just once.
     * Than, when necessary it is ploted in the backbuffer.
     */
    private void drawBackgroundInBuffer() {
        if (this.bgd2 == null) {

            //Get the already loaded image from loader
            this.sidewalk       = (VolatileImage)LoadingStuffs.getInstance().getStuff("sidewalk");
            this.grass          = (VolatileImage)LoadingStuffs.getInstance().getStuff("grass");
            this.subgrass       = (VolatileImage)LoadingStuffs.getInstance().getStuff("subgrass");

            //create a backbuffer image for doublebuffer
            byte lines          = (byte)(this.windowHeight / tileY);
            this.bgBufferImage  = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleVolatileImage(this.windowWidth, this.windowHeight);
            this.bgd2           = (Graphics2D)bgBufferImage.getGraphics();

            //paint all bg in black
            this.bgd2.setBackground(Color.BLACK);
            this.bgd2.clearRect(0, 0, this.windowWidth, this.windowHeight);
            
            //paint each lane
            this.bgd2.setColor(new Color(0, 0, 100));
            this.bgd2.fillRect(0, 0, this.windowWidth, this.tileY);
            this.bgd2.drawImage(this.grass, 0, 0, 72, this.grass.getHeight(),     //dest w1, h1, w2, h2
                                            107, 0, 179, this.grass.getHeight(),  //source w1, h1, w2, h2
                                            null);

            this.bgd2.drawImage(this.grass, (0 + 96 + 72), 0, (this.grass.getWidth() + 96 + 72), this.grass.getHeight(),     //dest w1, h1, w2, h2
                                            1, 0, this.grass.getWidth() - 2, this.grass.getHeight(),                         //source w1, h1, w2, h2
                                            null);
            
            this.bgd2.drawImage(this.grass, (0 + 96 + 348), 0, (this.grass.getWidth() + 96 + 348), this.grass.getHeight(),   //dest w1, h1, w2, h2
                                            1, 0, this.grass.getWidth() - 2, this.grass.getHeight(),                         //source w1, h1, w2, h2
                                            null);

            this.bgd2.drawImage(this.grass, (0 + 96 + 624), 0, (this.grass.getWidth() + 96 + 624), this.grass.getHeight(),   //dest w1, h1, w2, h2
                                            1, 0, this.grass.getWidth() - 2, this.grass.getHeight(),                         //source w1, h1, w2, h2
                                            null);

            this.bgd2.drawImage(this.grass, (0 + 96 + 900), 0, (this.grass.getWidth() + 96 + 900), this.grass.getHeight(),   //dest w1, h1, w2, h2
                                            1, 0, this.grass.getWidth() - 2, this.grass.getHeight(),                         //source w1, h1, w2, h2
                                            null);
            
            this.bgd2.drawImage(this.grass, (0 + 96 + 1176), 0, (this.grass.getWidth() + 96 + 1176), this.grass.getHeight(),   //dest w1, h1, w2, h2
                                            1, 0, this.grass.getWidth() - 2, this.grass.getHeight(),                         //source w1, h1, w2, h2
                                            null);
            
            this.bgd2.setColor(new Color(0, 0, 100));
            this.bgd2.fillRect(0, tileY, this.windowWidth, this.tileY);
            this.bgd2.fillRect(0, tileY * 2, this.windowWidth, this.tileY);
            this.bgd2.fillRect(0, tileY * 3, this.windowWidth, this.tileY);
            this.bgd2.fillRect(0, tileY * 4, this.windowWidth, this.tileY);
            this.bgd2.fillRect(0, tileY * 5, this.windowWidth, this.tileY);

            //middle lane
            this.bgd2.setColor(new Color(0, 0, 100));
            this.bgd2.fillRect(0, tileY * 6, this.windowWidth, this.tileY - 5);
            
            //final lane (goal)
            this.bgd2.setColor(new Color(0, 0, 0));
            this.bgd2.fillRect(0, tileY * 12, this.windowWidth, this.tileY);

            //paint each frogger dock
            this.bgd2.setColor(new Color(20, 20, 20));
            //first dock

            this.bgd2.drawImage(this.subgrass, 72, 0, this.subgrass.getWidth() + 72, this.subgrass.getHeight(),     //dest w1, h1, w2, h2
                                               0, 0, this.subgrass.getWidth(), this.subgrass.getHeight(),  //source w1, h1, w2, h2
                                               null);
            //second dock
            this.bgd2.drawImage(this.subgrass, 348, 0, this.subgrass.getWidth() + 348, this.subgrass.getHeight(),     //dest w1, h1, w2, h2
                                               0, 0, this.subgrass.getWidth(), this.subgrass.getHeight(),  //source w1, h1, w2, h2
                                               null);
            //third dock
            this.bgd2.drawImage(this.subgrass, 624, 0, this.subgrass.getWidth() + 624, this.subgrass.getHeight(),     //dest w1, h1, w2, h2
                                               0, 0, this.subgrass.getWidth(), this.subgrass.getHeight(),  //source w1, h1, w2, h2
                                               null);
            //fourth dock
            this.bgd2.drawImage(this.subgrass, 900, 0, this.subgrass.getWidth() + 900, this.subgrass.getHeight(),     //dest w1, h1, w2, h2
                                               0, 0, this.subgrass.getWidth(), this.subgrass.getHeight(),  //source w1, h1, w2, h2
                                               null);
            //fifth dock
            this.bgd2.drawImage(this.subgrass, 1176, 0, this.subgrass.getWidth() + 1176, this.subgrass.getHeight(),     //dest w1, h1, w2, h2
                                               0, 0, this.subgrass.getWidth(), this.subgrass.getHeight(),  //source w1, h1, w2, h2
                                               null);

            //draw lane lines
            for (byte i = 8; i < lines - 1; i++) {
                this.bgd2.setStroke(new BasicStroke());
                this.bgd2.setColor(new Color(150, 150, 0));
                this.bgd2.drawLine(0, (tileY * i), this.windowWidth, (tileY * i));
                this.bgd2.drawLine(0, (tileY * i)+1, this.windowWidth, (tileY * i)+1);
            }

            //draw lane inlines
            for (byte i = 7; i < lines - 1; i++) {
                this.bgd2.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
                this.bgd2.setColor(new Color(150, 150, 0, 50));
                this.bgd2.drawLine(0, (tileY * i) + halfTileY, this.windowWidth, (tileY * i) + halfTileY);
            }

            //fixed image source sizes
            byte bgImageW = 45;
            byte bgImageH = 64;

            //draw the safe spot (middle and bottom)
            for (int i = 0; i < 31; i++) {
                this.bgd2.drawImage(this.sidewalk, i * bgImageW, (6 * tileY), ((i * bgImageW) + bgImageW), (7 * tileY),     //dest w1, h1, w2, h2
                                                   0, 0, bgImageW, bgImageH,                                                //source w1, h1, w2, h2
                                                   null);
                this.bgd2.drawImage(this.sidewalk, i * bgImageW, (12 * tileY), ((i * bgImageW) + bgImageW), (13 * tileY),   //dest w1, h1, w2, h2
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
        //update the vehicles
        this.vehicles.update(frametime);
        this.trunks.update(frametime);
    }

    /**
     * 
     * @param frametime
     */
    public void draw(long frametime) {
        //After construct the bg once, copy it to the graphic device
        this.g2d.drawImage(this.bgBufferImage, 0, 0, null);
        
        //draw the vehicles
        this.vehicles.draw(frametime);
        this.trunks.draw(frametime);
    }
}