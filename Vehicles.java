import java.awt.Graphics2D;

import interfaces.Directions;
import interfaces.Lanes;
import interfaces.Sprite;
import interfaces.SpriteCollection;
import interfaces.Stages;

/**
 * Class representing a collection of vehicle
 */
public class Vehicles extends SpriteCollection {
 
    //The tile image, and its elements (positions)
    protected int windowWidth               = 0;
    protected int windowHeight              = 0;
    protected Graphics2D g2d                = null;

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

        for (byte i = 0; i < vehicles.length; i++) {
            vehicles[i] = new Vehicle(this.g2d);
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
                    if (calcPos > (this.windowWidth * 1000) + Vehicle.largerVehicule) {
                        calcPos = 0 - Vehicle.largerVehicule;
                    }
                } else {
                    if (calcPos < -Vehicle.largerVehicule) {
                        calcPos = (this.windowWidth * 1000);
                    }
                }
                //atualiza a posição do objeto na array
                Stages.stg1[i][j][2]        = (int)Math.round(calcPos);

                //recupera e atualiza cada veículo
                vehicles[index].type         = (byte)Stages.stg1[i][j][0];
                vehicles[index].direction    = direction;
                vehicles[index].positionX    = (short)(position/1000);
                //incrementa o index ao final
                vehicles[index].width        = (byte)Vehicle.vehiclesW[vehicles[index].type];
                vehicles[index++].positionY  = (short)Lanes.streetLanes[i];
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
                this.vehicles[index++].draw(frametime);
            }
        }
    }

    @Override
    protected Sprite[] getSpriteCollection() {
        return (this.vehicles);
    }
}