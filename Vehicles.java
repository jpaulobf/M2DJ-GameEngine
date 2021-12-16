import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import Interfaces.Directions;
import Interfaces.Lanes;
import Interfaces.Stages;

import java.io.File;

public class Vehicles {
 
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
        private Vehicle [] vehicle              =  new Vehicle[12];

        /**
         * Load the tile image
         * @param g2d
         */
        public Vehicles(Graphics2D g2d, int windowWidth, int windowHeight) {
            this.g2d            = g2d;
            this.windowWidth    = windowWidth;
            this.windowHeight   = windowHeight;
    
            try {
                this.vehiclesTile = ImageIO.read(new File("images\\vehicules.png"));
            } catch (java.io.IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            for (byte i = 0; i < vehicle.length; i++) {
                vehicle[i] = new Vehicle(g2d);
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
                            calcPos = 0;
                        }
                    } else {
                        if (calcPos < -largerVehicule) {
                            calcPos = (this.windowWidth * 1000);
                        }
                    }
                    //atualiza a posição do objeto na array
                    Stages.stg1[i][j][2]        = (int)Math.round(calcPos);

                    //recupera e atualiza cada veículo
                    vehicle[index].type         = (byte)Stages.stg1[i][j][0];
                    vehicle[index].direction    = (byte)Stages.stg1[i][j][1];
                    vehicle[index].positionX    = (short)(Stages.stg1[i][j][2]/1000);
                    //incrementa o index ao final
                    vehicle[index++].positionY  = (short)Lanes.lanes[i];
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
                    vehicle[index++].draw(this.vehiclesTile, vehiclesImgX, vehiclesW);
                }
            }
        }
    }
    