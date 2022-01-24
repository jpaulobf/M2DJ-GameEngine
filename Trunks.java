import java.awt.Graphics2D;
import interfaces.Lanes;
import interfaces.Sprite;
import interfaces.SpriteCollection;
import interfaces.Stages;

/**
 * Class representing the collection of trunks in each stage.
 */
public class Trunks extends SpriteCollection {

    //Screen size ang G2D
    protected int windowWidth           = 0;
    protected int windowHeight          = 0;
    protected Graphics2D g2d            = null;

    //define the trunks array
    private TreeTrunk[] trunks          = new TreeTrunk[Stages.CURRENT_STAGE_TRUNKS[Stages.CURRENT_STAGE]];
    private TreeTrunk[] offsetTrunks    = new TreeTrunk[5];
    private double[] offsetPosX         = new double[offsetTrunks.length];
    private final short far             = -10_000;
    private int windowWidth1000         =  0;

    /**
     * Load the tile image
     * @param g2d
     */
    public Trunks(Graphics2D g2d, int windowWidth, int windowHeight) {
        this.g2d                = g2d;
        this.windowWidth        = windowWidth;
        this.windowHeight       = windowHeight;
        this.windowWidth1000    = this.windowWidth * 1_000;

        //instantiate the trunks objects and the offset trunks
        for (byte i = 0; i < trunks.length; i++) {
            trunks[i] = new TreeTrunk(this.g2d);
        } 
        for (byte i = 0; i < offsetTrunks.length; i++) {
            offsetTrunks[i]             = new TreeTrunk(this.g2d);
            offsetTrunks[i].positionX   = far;
        }
    }

    /**
     * Updates the elements on the screen
     */
    @Override
    public void update(long frametime) {

        byte index          = 0;
        byte indexLines     = 0;

        for (int i = 0; i < Stages.S1_TRUNKS.length; i++) {

            if (Stages.S1_TRUNKS[i].length != 0) {

                byte direction = (byte)Stages.S1_TRUNKS[i][0][0];
                short velocity = (short)Stages.S1_TRUNKS[i][1][0];
                
                for (int j = 0; j < Stages.S1_TRUNKS[i][3].length; j++, index++) {

                    //read & set the trunk parameters
                    double step                     = (double)velocity / (double)(1_000_000D / (double)frametime);
                    double position                 = Stages.S1_TRUNKS[i][3][j];
                    double calcPos                  = position + step;
                    trunks[index].calculatedStep    = step;
                    trunks[index].type              = (byte)Stages.S1_TRUNKS[i][2][0];

                    //update the trunk
                    trunks[index].update(frametime);

                    //update the width based on type
                    short width                     = trunks[index].getWidth();
                    int width1000                   = width * 1_000;
                    int windowWidthLessWidth1000    = ((this.windowWidth - width) * 1_000);

                    //if the trunk touch the left side of the screen 
                    //we need to create another offset trunk to represent the loop
                    if (calcPos > (windowWidthLessWidth1000) && (calcPos < this.windowWidth1000)) {
                        //define the necessary offset sprite parameters
                        this.offsetTrunks[indexLines].type       = this.trunks[index].type;
                        this.offsetTrunks[indexLines].direction  = direction;
                        
                        //test if the position is "far" (first time), in this case, utilises the width of the trunk (reverse)
                        //otherwise, sum the current position to the next distance step
                        if (this.offsetTrunks[indexLines].positionX == this.far) {
                            this.offsetPosX[indexLines] = -width1000;
                        } else {
                            this.offsetPosX[indexLines] = (this.offsetPosX[indexLines] + step);
                        }

                        //set the offset trunk parameters
                        this.offsetTrunks[indexLines].positionX      = (short)(this.offsetPosX[indexLines]/1_000);
                        this.offsetTrunks[indexLines].positionY      = (short)Lanes.riverLanes[i];
                        this.offsetTrunks[indexLines].calculatedStep = step;
                        this.offsetTrunks[indexLines].update(frametime);

                    //when the trunk surpass the entire screen
                    //hide the offset trunk (back it to "far"), and set the main trunk back to the begining (calcPos = 0)
                    } else if (calcPos > this.windowWidth1000) {
                        calcPos = 0;
                    } else if (calcPos > 0 && calcPos < 5_000) {
                        this.offsetTrunks[indexLines].positionX = this.far;
                    }

                    //store the new X position in the array
                    Stages.S1_TRUNKS[i][3][j]   = (int)Math.round(calcPos);

                    //set the trunk parameters
                    this.trunks[index].direction    = direction;
                    this.trunks[index].positionX    = (short)(position/1_000);
                    this.trunks[index].positionY    = (short)Lanes.riverLanes[i]; //incrementa o index ao final
                }

                indexLines++;
            }
        }
    }

    /**
     * Draw the graphical vehicles elements
     */
    @Override
    public void draw(long frametime) {
        int index = 0;
        //draw the main trunks
        for (byte i = 0; i < Stages.S1_TRUNKS.length; i++) {
            for (byte j = 0; Stages.S1_TRUNKS[i].length != 0 && j < Stages.S1_TRUNKS[i][3].length; j++, index++) {
                trunks[index].draw(frametime);
            }
        }
        //draw the offset trunks (when necessary)
        for (byte j = 0; j < offsetTrunks.length; j++) {
            if (offsetTrunks[j].positionX > far) {
                offsetTrunks[j].draw(frametime);
            }
        }
    }

    @Override
    protected Sprite[] getSpriteCollection() {
        return (java.util.stream.Stream.concat(java.util.Arrays.stream(this.trunks), 
                                               java.util.Arrays.stream(this.offsetTrunks)).toArray(Sprite[]::new));
    }
}
