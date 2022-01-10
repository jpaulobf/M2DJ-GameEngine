import java.awt.Graphics2D;
import interfaces.Lanes;
import interfaces.Sprite;
import interfaces.SpriteCollection;
import interfaces.Stages;

public class Trunks extends SpriteCollection {

    //The tile image, and its elements (positions)
    protected int windowWidth       = 0;
    protected int windowHeight      = 0;
    protected Graphics2D g2d        = null;

    //define the vehicules array
    private TreeTrunk [] trunks     =  new TreeTrunk[8];
    
    /**
     * Load the tile image
     * @param g2d
     */
    public Trunks(Graphics2D g2d, int windowWidth, int windowHeight) {
        this.g2d            = g2d;
        this.windowWidth    = windowWidth;
        this.windowHeight   = windowHeight;
        for (byte i = 0; i < trunks.length; i++) {
            trunks[i] = new TreeTrunk(this.g2d);
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

        //TODO: TAKE CARE OF OFF-SCREEN TRUNKS... DONT KNOW HOW YET...

        for (int i = 0; i < Stages.STAGE1_TRUNKS.length; i++) {
            for (int j = 0; j < Stages.STAGE1_TRUNKS[i].length; j++) {
                if (Stages.STAGE1_TRUNKS[i][j].length > 0) {
                    direction   = (byte)Stages.STAGE1_TRUNKS[i][j][1];
                    position    = Stages.STAGE1_TRUNKS[i][j][2];
                    velocity    = (short)Stages.STAGE1_TRUNKS[i][j][3];
                    step        = (double)velocity / (double)(1_000_000D / (double)frametime);
                    calcPos     = position + (step * direction);

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
                    Stages.STAGE1_TRUNKS[i][j][2]        = (int)Math.round(calcPos);

                    //recupera e atualiza cada veículo
                    trunks[index].type          = (byte)Stages.STAGE1_TRUNKS[i][j][0];
                    trunks[index].direction     = direction;
                    trunks[index].positionX     = (short)(position/1000);
                    //trunks[index].width         = (byte)Vehicle.vehiclesW[vehicles[index].type];
                    trunks[index++].positionY   = (short)Lanes.riverLanes[i]; //incrementa o index ao final
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

        //TODO: TAKE CARE OF OFF-SCREEN TRUNKS... DONT KNOW HOW YET...

        for (byte i = 0; i < Stages.STAGE1_TRUNKS.length; i++) {
            for (byte j = 0; j < Stages.STAGE1_TRUNKS[i].length; j++) {
                if (Stages.STAGE1_TRUNKS[i][j].length > 0) {
                    trunks[index++].draw(frametime);
                }
            }
        }
    }

    @Override
    protected Sprite[] getSpriteCollection() {
        return (this.trunks);
    }
}
