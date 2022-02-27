import java.awt.Graphics2D;
import interfaces.Lanes;
import interfaces.Sprite;
import interfaces.SpriteCollection;
import interfaces.Stages;

/**
 * Class representing a collection of vehicle
 */
public class Vehicles extends SpriteCollection {
 
    //The tile image, and its elements (positions)
    protected int windowWidth           = 0;
    protected int windowHeight          = 0;
    private Scenario scenarioRef        = null;

    //define the vehicules array
    private Vehicle [] vehicles         = new Vehicle[Stages.CURRENT_STAGE_CARS[Stages.CURRENT_STAGE]];
    private volatile boolean stopped    = false; 

    public Scenario getScenarioRef() {
        return (this.scenarioRef);
    }

    /**
     * Vehicles constructor
     * @param game
     * @param windowWidth
     * @param windowHeight
     */
    public Vehicles(Scenario scenarioRef, int windowWidth, int windowHeight) {
        this.scenarioRef    = scenarioRef;
        this.windowWidth    = windowWidth;
        this.windowHeight   = windowHeight;
        
        for (byte i = 0; i < vehicles.length; i++) {
            vehicles[i] = new Vehicle(this);
            vehicles[i].setScenarioOffsetY(this.scenarioRef.getScoreHeight());
        }

        for (int i = 0, index = 0; i < Stages.CARS[Stages.CURRENT_STAGE].length; i++) {
            for (int j = 0; j < Stages.CARS[Stages.CURRENT_STAGE][i][3].length; j++) {
                vehicles[index++].ogPositionX = Stages.CARS[Stages.CURRENT_STAGE][i][3][j];
            }
        }
    }
        
    /**
     * Updates the elements on the screen
     */
    @Override
    public void update(long frametime) {
        double step     = 0d;
        double calcPos  = 0d;
        byte direction  = 0;
        double position = 0;
        short velocity  = 0;
        byte index      = 0;

        if (!this.stopped) {
            for (int i = 0; i < Stages.CARS[Stages.CURRENT_STAGE].length; i++) {

                direction   = (byte)Stages.CARS[Stages.CURRENT_STAGE][i][0][0];
                velocity    = (short)Stages.CARS[Stages.CURRENT_STAGE][i][1][0];

                for (int j = 0; j < Stages.CARS[Stages.CURRENT_STAGE][i][3].length; j++) {

                    position             = Stages.CARS[Stages.CURRENT_STAGE][i][3][j];
                    step                 = (double)velocity / (double)(1_000_000D / (double)frametime);
                    calcPos              = position + (step * direction);
                    vehicles[index].type = (byte)Stages.CARS[Stages.CURRENT_STAGE][i][2][0];

                    if (direction == RIGHT) {
                        if (calcPos > (this.windowWidth * 1000) + Vehicle.largerVehicule) {
                            calcPos = 0 - Vehicle.largerVehicule;
                        }
                    } else {
                        if (calcPos < -Vehicle.largerVehicule) {
                            calcPos = (this.windowWidth * 1000);
                        }
                    }
                    //atualiza a posição do objeto na array
                    Stages.CARS[Stages.CURRENT_STAGE][i][3][j] = (int)Math.round(calcPos);

                    //recupera e atualiza cada veículo
                    vehicles[index].direction    = direction;
                    vehicles[index].positionX    = (short)(position/1000);
                    
                    //incrementa o index ao final
                    vehicles[index].width        = (byte)Vehicle.vehiclesW[vehicles[index].type];
                    vehicles[index++].positionY  = (short)Lanes.streetLanes[i];
                }
            }
        }
    }

    /**
     * Draw the graphical vehicles elements
     */
    @Override
    public void draw(long frametime) {
        int index = 0;
        for (byte i = 0; i < Stages.CARS[Stages.CURRENT_STAGE].length; i++) {
            for (byte j = 0; j < Stages.CARS[Stages.CURRENT_STAGE][i][3].length; j++, index++) {
                this.vehicles[index].draw(frametime);
            }
        }
    }

    @Override
    protected Sprite[] getSpriteCollection() {
        return (this.vehicles);
    }

    /**
     * Toogle the stop control
     */
    public void toogleStop() {
        this.stopped = !this.stopped;
    }

    /**
     * Reset the current Vehicles state
     */
    public void reset() {
        for (int i = 0, index = 0; i < Stages.CARS[Stages.CURRENT_STAGE].length; i++) {
            for (int j = 0; j < Stages.CARS[Stages.CURRENT_STAGE][i][3].length; j++) {
                Stages.CARS[Stages.CURRENT_STAGE][i][3][j] = vehicles[index++].ogPositionX;
            }
        }
    }

    @Override
    public Graphics2D getG2D() {
        return (this.scenarioRef.getGameRef().getG2D());
    }
}