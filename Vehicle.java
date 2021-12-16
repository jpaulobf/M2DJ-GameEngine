import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;


public class Vehicle extends Sprite {
        
    //The tile image, and its elements (positions)
    private BufferedImage vehiclesTile      = null;
    private int windowWidth                 = 0;
    private int windowHeight                = 0;
    
    //for the lane background
    private final int lanes[]               = { 461, 525, 589, 653, 716 };

    //for the vehicles tiles
    private final int vehiclesImgX[][]      = { {0,54}, {155,107}, {204,249}, {337,294}, {461,382} };
    private final int vehicleH              = 38;
    private final int vehiclesW[]           = { 45, 45, 43, 41, 76 };
    private final int largerVehicule        = 76_000;

    //Stage 1 parameters
    short [][] velocities = {{100}, {180}, {50}, {330}, {300}};
    int [][] positionX    = {{300_000, 650_000, 1000_000}, {100_000, 400_000, 700_000}, {100_000, 500_000, 900_000}, {100_000}, {100_000, 750_000}};

    //for each lane, each car parameter
    //type, direction, start-position-x, velocity
    int [][][] stg1 =  { { {4, LEFT,  positionX[4][0], velocities[4][0]}, {4, LEFT,  positionX[4][1], velocities[4][0]}},
                         { {1, RIGHT, positionX[3][0], velocities[3][0]}                                               },
                         { {2, LEFT,  positionX[2][0], velocities[2][0]}, {3, LEFT,  positionX[2][1], velocities[2][0]}, {1, LEFT,  positionX[2][2], velocities[2][0]}}, 
                         { {3, RIGHT, positionX[1][0], velocities[1][0]}, {1, RIGHT, positionX[1][1], velocities[1][0]}, {4, RIGHT, positionX[1][2], velocities[1][0]}},
                         { {0, LEFT,  positionX[0][0], velocities[0][0]}, {0, LEFT,  positionX[0][1], velocities[0][0]}, {3, LEFT,  positionX[0][2], velocities[0][0]}}
                       };

    /**
     * Load the tile image
     * @param g2d
     */
    public Vehicle(Graphics2D g2d, int windowWidth, int windowHeight) {
        this.g2d            = g2d;
        this.windowWidth    = windowWidth;
        this.windowHeight   = windowHeight;

        try {
            this.vehiclesTile = ImageIO.read(new File("images\\vehicules.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(-1);
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
}
