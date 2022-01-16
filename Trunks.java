import java.awt.Graphics2D;

import contracts.Lanes;
import contracts.Sprite;
import contracts.SpriteCollection;
import contracts.Stages;

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
     * Get the last trunk step
     * @param trunk
     * @return
     */
    public double getTrunkMovementStep(int trunk) {
        if (trunk > (trunks.length-1)) {
            return (offsetTrunks[trunk-trunks.length].currentStep);
        } else {
            return (trunks[trunk].currentStep);
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
            for (int j = 1; j < Stages.S1_TRUNKS[i].length; j++, index++) {

                //read & set the trunk parameters
                trunks[index].type          = (byte)Stages.S1_TRUNKS[i][j][0];
                short width                 = trunks[index].getWidth();
                byte direction              = (byte)Stages.S1_TRUNKS[i][0][0];
                double position             = Stages.S1_TRUNKS[i][j][1];
                short velocity              = (short)Stages.S1_TRUNKS[i][j][2];
                double step                 = (double)velocity / (double)(1_000_000D / (double)frametime);
                double stepDir              = step * direction;
                double calcPos              = position + stepDir;
                trunks[index].currentStep   = stepDir;

                //update the trunk
                trunks[index].update(frametime);

                if (direction == RIGHT) {
                    //if the trunk touch the left side of the screen 
                    //we need to create another offset trunk to represent the loop
                    if (calcPos > ((this.windowWidth - width) * 1_000) && (calcPos < windowWidth1000)) {
                        //define the necessary offset sprite parameters
                        offsetTrunks[indexLines].type       = trunks[index].type;
                        offsetTrunks[indexLines].direction  = direction;
                        
                        //test if the position is "far" (first time), in this case, utilises the width of the trunk (reverse)
                        //otherwise, sum the current position to the next distance step
                        if (offsetTrunks[indexLines].positionX == far) {
                            offsetPosX[indexLines] = (-width) * 1_000;
                        } else {
                            offsetPosX[indexLines] = (offsetPosX[indexLines] + stepDir);
                        }

                        //set the offset trunk parameters
                        offsetTrunks[indexLines].positionX   = (short)(offsetPosX[indexLines]/1_000);
                        offsetTrunks[indexLines].positionY   = (short)Lanes.riverLanes[i];
                        offsetTrunks[indexLines].currentStep = stepDir;
                        offsetTrunks[indexLines].update(frametime);

                    //when the trunk surpass the entire screen
                    //hide the offset trunk (back it to "far"), and set the main trunk back to the begining (calcPos = 0)
                    } else if (calcPos > windowWidth1000) {
                        calcPos = 0;
                        offsetTrunks[indexLines].positionX = far;
                    }
                } else {
                    if ((calcPos < 0) && calcPos > (-width * 1_000)) {

                        //define the necessary offset sprite parameters
                        offsetTrunks[indexLines].type       = trunks[index].type;
                        offsetTrunks[indexLines].direction  = direction;

                        //test if the position is "far" (first time), in this case, utilises the width of the trunk (reverse)
                        //otherwise, sum the current position to the next distance step
                        if (offsetTrunks[indexLines].positionX == far) {
                            offsetPosX[indexLines] = (this.windowWidth) * 1_000;
                        } else {
                            offsetPosX[indexLines] = (offsetPosX[indexLines] + stepDir);
                        }

                        //set the offset trunk parameters
                        offsetTrunks[indexLines].positionX   = (short)(offsetPosX[indexLines]/1_000);
                        offsetTrunks[indexLines].positionY   = (short)Lanes.riverLanes[i];
                        offsetTrunks[indexLines].currentStep = stepDir;
                        offsetTrunks[indexLines].update(frametime);

                    } else if (calcPos < (-width * 1_000)) {
                        calcPos = ((this.windowWidth - width) * 1_000);
                        offsetTrunks[indexLines].positionX = far;
                    }
                }

                //store the new X position in the array
                Stages.S1_TRUNKS[i][j][1]   = (int)Math.round(calcPos);

                //set the trunk parameters
                trunks[index].direction         = direction;
                trunks[index].positionX         = (short)(position/1_000);
                trunks[index].positionY       = (short)Lanes.riverLanes[i]; //incrementa o index ao final
            }

            if (Stages.S1_TRUNKS[i].length > 0) {
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
            for (byte j = 1; j < Stages.S1_TRUNKS[i].length; j++, index++) {
                if (Stages.S1_TRUNKS[i][j].length > 0) {
                    trunks[index].draw(frametime);
                }
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
