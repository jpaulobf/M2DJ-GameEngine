import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.image.VolatileImage;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

/*
    WTCD: This class represents the Scenario and traps of the Game
*/
public class Scenario {

    //Scenario variables
    private Graphics2D g2d              = null;
    private int windowWidth             = 0;
    private int windowHeight            = 0;
    private final byte tileX            = 64;
    private final byte tileY            = 64;
    private final byte halfTileX        = tileX/2;
    private final byte halfTileY        = tileY/2;
    private VolatileImage bgBufferImage = null;
    private Graphics2D bgd2             = null;
    private Vehicle vehicle             = null;
    private BufferedImage sidewalk      = null;

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
        this.vehicle        = new Vehicle(g2d, windowWidth, windowHeight);
        this.drawBackground();
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
    private void drawBackground() {
        if (this.bgd2 == null) {

            try {
                this.sidewalk = ImageIO.read(new File("images\\sidewalk.png"));
            } catch (java.io.IOException e) {
                e.printStackTrace();
                return;
            }

            //create a backbuffer image for doublebuffer
            byte lines          = (byte)(this.windowHeight / tileY);
            this.bgBufferImage  = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleVolatileImage(this.windowWidth, this.windowHeight);
            this.bgd2           = (Graphics2D)bgBufferImage.getGraphics();

            //paint all bg in black
            this.bgd2.setBackground(Color.BLACK);
            this.bgd2.clearRect(0, 0, this.windowWidth, this.windowHeight);
            
            //paint each lane
            this.bgd2.setColor(new Color(0, 100, 0));
            this.bgd2.fillRect(0, 0, this.windowWidth, this.tileY);
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
            int bgoffset = 22; //TODO: REFACTOR
            this.bgd2.setColor(new Color(20, 20, 20));
            this.bgd2.fillRect(this.halfTileX * 0  + bgoffset, 0, this.halfTileX, this.tileY);
            this.bgd2.fillRect(this.halfTileX * 1  + bgoffset, 0, this.halfTileX, this.tileY);
            this.bgd2.fillRect(this.halfTileX * 2  + bgoffset, 0, this.halfTileX, this.tileY);
                
            bgoffset = 34; //TODO: REFACTOR
            this.bgd2.fillRect(this.halfTileX * 9  + bgoffset, 0, this.halfTileX, this.tileY);
            this.bgd2.fillRect(this.halfTileX * 10 + bgoffset, 0, this.halfTileX, this.tileY);
            this.bgd2.fillRect(this.halfTileX * 11 + bgoffset, 0, this.halfTileX, this.tileY);
 
            bgoffset = 46; //TODO: REFACTOR
            this.bgd2.fillRect(this.halfTileX * 18 + bgoffset, 0, this.halfTileX, this.tileY);
            this.bgd2.fillRect(this.halfTileX * 19 + bgoffset, 0, this.halfTileX, this.tileY);
            this.bgd2.fillRect(this.halfTileX * 20 + bgoffset, 0, this.halfTileX, this.tileY);

            bgoffset = 58; //TODO: REFACTOR
            this.bgd2.fillRect(this.halfTileX * 27 + bgoffset, 0, this.halfTileX, this.tileY);
            this.bgd2.fillRect(this.halfTileX * 28 + bgoffset, 0, this.halfTileX, this.tileY);
            this.bgd2.fillRect(this.halfTileX * 29 + bgoffset, 0, this.halfTileX, this.tileY);

            bgoffset = 70; //TODO: REFACTOR
            this.bgd2.fillRect(this.halfTileX * 36 + bgoffset, 0, this.halfTileX, this.tileY);
            this.bgd2.fillRect(this.halfTileX * 37 + bgoffset, 0, this.halfTileX, this.tileY);
            this.bgd2.fillRect(this.halfTileX * 38 + bgoffset, 0, this.halfTileX, this.tileY);

            //draw lane lines
            for (byte i = 8; i < lines - 1; i++) {
                this.bgd2.setStroke(new BasicStroke());
                this.bgd2.setColor(new Color(150, 150, 0));
                this.bgd2.drawLine(0, 
                                   (tileY * i), 
                                   this.windowWidth, 
                                   (tileY * i));
            }

            //draw lane inlines
            for (byte i = 7; i < lines - 1; i++) {
                this.bgd2.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
                this.bgd2.setColor(new Color(150, 150, 0, 50));
                this.bgd2.drawLine(0, 
                                   (tileY * i) + halfTileY, 
                                   this.windowWidth, 
                                   (tileY * i) + halfTileY);
            }

            //TODO: REFACTOR
            for (int i = 0; i < 30; i++) {
                this.bgd2.drawImage(this.sidewalk, i * 45, 384, (i * 45) + 45, 384 + 64, //dest w1, h1, w2, h2
                                                   0, 0, 45, 64, //source w1, h1, w2, h2
                                                   null);
            }

            //TODO: REFACTOR
            for (int i = 0; i < 30; i++) {
                this.bgd2.drawImage(this.sidewalk, i * 45, 767, (i * 45) + 45, 767 + 64, //dest w1, h1, w2, h2
                                                   0, 0, 45, 64, //source w1, h1, w2, h2
                                                   null);
            }
        }
    }

    /**
     * 
     * @param frametime
     */
    public void draw(long frametime) {
        
        //After draw the bg once, copy it to the graphic device
        this.g2d.drawImage(this.bgBufferImage, 0, 0, null);
        
        //draw the vehicles
        this.vehicle.draw(frametime);
    }

    /**
     * Update the scenario and its elements
     * @param frametime
     */
    public void update(long frametime) {

        //update the vehicles
        this.vehicle.update(frametime);
    }

    /**
     * Class representing the game vehicles
     */
    private class Vehicle extends Sprite {
        
        //The tile image, and its elements (positions)
        private BufferedImage vehiclesTile      = null;
        private final int lanes[]               = { 461, 525, 589, 653, 716 };
        private final int vehiclesW[]           = { 45, 45, 43, 41, 76 };
        private final int vehiclesImgX[][]      = { {0,54}, {155,107}, {204,249}, {337,294}, {461,382} };
        private final int vehicleH              = 38;
        private final int largerVehicule        = 76_000;
        private int windowWidth                 = 0;
        private int windowHeight                = 0;


        short [][] velocities = {{100}, {180}, {50}, {330}, {300}};
        int [][] positionX    = {{300_000, 650_000, 1000_000}, {100_000, 400_000, 700_000}, {100_000, 500_000, 900_000}, {100_000}, {100_000, 750_000}};

        //type, direction, start-position-x, velocity
        int [][][] stg1 =  { { {4, LEFT,  positionX[4][0], velocities[4][0]}, {4, LEFT,  positionX[4][1], velocities[4][0]}},
                             { {1, RIGHT, positionX[3][0], velocities[3][0]}},
                             { {2, LEFT,  positionX[2][0], velocities[2][0]}, {3, LEFT,  positionX[2][1], velocities[2][0]}, {1, LEFT, positionX[2][2], velocities[2][0]}}, 
                             { {3, RIGHT, positionX[1][0], velocities[1][0]}, {1, RIGHT, positionX[1][1], velocities[1][0]}, {4, RIGHT, positionX[1][2], velocities[1][0]}},
                             { {0, LEFT,  positionX[0][0], velocities[0][0]}, {0, LEFT,  positionX[0][1], velocities[0][0]}, {3, LEFT, positionX[0][2], velocities[0][0]}}
                           };

        /**
         * Load the tile image
         * @param g2d
         */
        public Vehicle(Graphics2D g2d, int windowWidth, int windowHeight) {
            this.g2d = g2d;
            this.windowWidth = windowWidth;
            this.windowHeight = windowHeight;

            try {
                this.vehiclesTile = ImageIO.read(new File("images\\vehicules.png"));
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Draw the vehicules in the estipulated lane:
         * veicules types { 0 - car1 | 1 - car2 | 2 - car3 | 3 - tractor | 4 - truck }
         * lanes { 0 - 4 }
        */ 
        private void drawVehicle(int type, int direction, int lane, int positionX) {
            
            //filter invalid values
            if (type < 0 || type > 4 || (direction != LEFT && direction != RIGHT) || lane < 0 || lane > 4) {
                return;
            }

            //draw the selected image
            direction = (direction == LEFT)?0:direction;
            this.g2d.drawImage(vehiclesTile, positionX, lanes[lane], positionX + vehiclesW[type], lanes[lane] + vehicleH, //dest w1, h1, w2, h2
                                             vehiclesImgX[type][direction], 0, vehiclesImgX[type][direction] + vehiclesW[type], vehicleH, //source w1, h1, w2, h2
                                             null);
        }

        /**
         * Draw the graphical vehicles elements
         */
        public void draw(long frametime) {

            byte direction  = 0;
            int position    = 0;
            byte type       = 0;
            byte lane       = 0;

            for (byte i = 0; i < stg1.length; i++) {
                for (byte j = 0; j < stg1[i].length; j++) {
                    type        = (byte)stg1[i][j][0];
                    direction   = (byte)stg1[i][j][1];
                    position    = (int)stg1[i][j][2]/1000;
                    lane        = i;

                    this.drawVehicle(type, direction, lane, position);
                }
            }
        }

        /**
         * Updates the elements on the screen
         */
        public void update(long frametime) {

            double step     = 0d;
            double calcPos  = 0d;
            byte direction  = 0;
            double position = 0;
            short velocity  = 0;

            for (int i = 0; i < stg1.length; i++) {
                for (int j = 0; j < stg1[i].length; j++) {
                    direction   = (byte)stg1[i][j][1];
                    position    = stg1[i][j][2];
                    velocity    = (short)stg1[i][j][3];
                    step        = (double)velocity / (double)(1_000_000D / (double)frametime);
                    calcPos     = position + (step * direction);
                    if (direction == RIGHT) {
                        if (calcPos > (this.windowWidth * 1000) + largerVehicule) {
                            calcPos = 0;
                        }
                    } else {
                        if (calcPos < -largerVehicule) {
                            calcPos = (this.windowWidth * 1000);
                        }
                    }
                    stg1[i][j][2] = (int)Math.round(calcPos);
                }
            }
        }
    }

    private class Turtle extends Sprite {

        @Override
        public void draw(long frametime) {
        }

        @Override
        public void update(long frametime) {
        }
    }

    private class Trunk extends Sprite {

        @Override
        public void draw(long frametime) {
        }

        @Override
        public void update(long frametime) {
        }
    }

    private class Snake extends Sprite {

        @Override
        public void draw(long frametime) {
        }

        @Override
        public void update(long frametime) {
        }
    }

    private class Monster extends Sprite {

        @Override
        public void draw(long frametime) {
        }

        @Override
        public void update(long frametime) {
        }
    }

    private class Gator extends Sprite {

        @Override
        public void draw(long frametime) {
        }

        @Override
        public void update(long frametime) {
        }
    }
}