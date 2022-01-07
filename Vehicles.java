import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Interfaces.Directions;
import Interfaces.Lanes;
import Interfaces.Sprite;
import Interfaces.Stages;
import Interfaces.SpriteCollection;

/**
 * Class representing a collection of vehicle
 */
public class Vehicles implements SpriteCollection {
 
        //The tile image, and its elements (positions)
        protected int windowWidth               = 0;
        protected int windowHeight              = 0;
        protected Graphics2D g2d                = null;
        
        //for the vehicles tiles
        private final int vehiclesImgX[][]      = { {0,54}, {155,107}, {204,249}, {337,294}, {461,382} };
        private final int vehiclesW[]           = { 45, 45, 43, 41, 76 };
        private final int largerVehicule        = 76_000;

        //vehicles tiles
        private BufferedImage vehiclesTile      = null;

        //define the vehicules array
        private Vehicle [] vehicles              =  new Vehicle[12];

        /**
         * Load the tile image
         * @param g2d
         */
        public Vehicles(Graphics2D g2d, int windowWidth, int windowHeight) {
            this.g2d            = g2d;
            this.windowWidth    = windowWidth;
            this.windowHeight   = windowHeight;
    
            //Get the already loaded image from loader
            this.vehiclesTile   = (BufferedImage)LoadingStuffs.getInstance().getStuff("vehiclesTile");

            for (byte i = 0; i < vehicles.length; i++) {
                vehicles[i] = new Vehicle();
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
            byte index      = 0;

            for (int i = 0; i < Stages.stg1.length; i++) {
                for (int j = 0; j < Stages.stg1[i].length; j++) {
                    direction   = (byte)Stages.stg1[i][j][1];
                    position    = Stages.stg1[i][j][2];
                    velocity    = (short)Stages.stg1[i][j][3];
                    step        = (double)velocity / (double)(1_000_000D / (double)frametime);
                    calcPos     = position + (step * direction);
                    if (direction == Directions.RIGHT) {
                        if (calcPos > (this.windowWidth * 1000) + largerVehicule) {
                            calcPos = 0 - largerVehicule;
                        }
                    } else {
                        if (calcPos < -largerVehicule) {
                            calcPos = (this.windowWidth * 1000);
                        }
                    }
                    //atualiza a posição do objeto na array
                    Stages.stg1[i][j][2]        = (int)Math.round(calcPos);

                    //recupera e atualiza cada veículo
                    vehicles[index].type         = (byte)Stages.stg1[i][j][0];
                    vehicles[index].direction    = (byte)Stages.stg1[i][j][1];
                    vehicles[index].positionX    = (short)(Stages.stg1[i][j][2]/1000);
                    //incrementa o index ao final
                    vehicles[index].width        = (byte)vehiclesW[vehicles[index].type];
                    vehicles[index++].positionY  = (short)Lanes.lanes[i];
                }
            }
        }
    
        /**
         * Draw the graphical vehicles elements
         */
        public void draw(long frametime) {
            int index = 0;
            for (byte i = 0; i < Stages.stg1.length; i++) {
                for (byte j = 0; j < Stages.stg1[i].length; j++) {
                    vehicles[index++].draw(this.vehiclesTile, vehiclesImgX, vehiclesW, this.g2d);
                }
            }
        }

        /**
         * Test if some of the vehicles are coliding
         * Return a number # of the coliding vehicle or #-1 if don't
         * @param sprite
         */
        @Override
        public int testColision(Sprite sprite) {
            //gate check
            if (sprite == null) return -1;
            
            //important: it's not necessary to test if the vehicle array is null, because it was initialized in the constructor
            for (int cnt = 0; cnt < this.vehicles.length; cnt++) {
                if (this.vehicles[cnt].isColiding(sprite)) {
                    return (cnt);
                }
            }

            //if the code reach here, no colision
            return (-1);
        }
    }
    