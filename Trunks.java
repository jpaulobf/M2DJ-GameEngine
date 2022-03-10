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

    //define the trunks array
    private Trunk[] trunks              = null;
    private Trunk[] offsetTrunks        = null;
    private double[] offsetPosX         = null;
    private final short far             = -10_000;
    private int windowWidth1000         =  0;
    private volatile boolean stopped    = false;
    private Scenario scenarioRef        = null;
    private TrunkSnake trunkSnake       = null;
    private boolean hasTrunkInThirdLine = false;
    private volatile boolean reseting   = false;

    /**
     * Trunks constructor
     * @param game
     * @param windowWidth
     * @param windowHeight
     */
    public Trunks(Scenario scenarioRef, int windowWidth, int windowHeight) {
        this.scenarioRef        = scenarioRef;
        this.windowWidth        = windowWidth;
        this.windowHeight       = windowHeight;
        this.windowWidth1000    = this.windowWidth * 1_000;

        //create the snake in the trunk object
        this.trunkSnake         = new TrunkSnake(this.scenarioRef.getGameRef(), windowWidth);
        this.trunkSnake.setScenarioOffsetY(this.scenarioRef.getScoreHeight());

        //create objects of the stage
        this.nextStage();
    }

    /**
     * Updates the elements on the screen
     */
    @Override
    public synchronized void update(long frametime) {

        byte index              = 0;
        byte indexLines         = 0;
        byte positionYOffset    = 16;

        if (!this.stopped) {

            for (int i = 0; i < Stages.TRUNKS[Stages.CURRENT_STAGE[0]].length; i++) {

                if (Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i].length != 0) {

                    byte direction = (byte)Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i][0][0];
                    short velocity = (short)Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i][1][0];
                    
                    for (int j = 0; j < Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i][3].length; j++, index++) {

                        //read & set the trunk parameters
                        double step                     = (double)velocity / (double)(1_000_000D / (double)frametime);
                        double position                 = Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i][3][j];
                        double calcPos                  = position + step;
                        trunks[index].calculatedStep    = step;
                        trunks[index].type              = (byte)Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i][2][j];

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
                            this.offsetTrunks[indexLines].positionY      = (short)Lanes.riverLanes[i] + positionYOffset;
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
                        Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i][3][j] = (int)Math.round(calcPos);

                        //set the trunk parameters
                        this.trunks[index].direction    = direction;
                        this.trunks[index].positionX    = (short)(position/1_000);
                        this.trunks[index].positionY    = (short)Lanes.riverLanes[i] + positionYOffset; //incrementa o index ao final

                        if (i == 2 && this.hasTrunkInThirdLine && j == 0) {
                            if (this.trunkSnake.getPositionX() > this.windowWidth) {
                                this.trunkSnake.reset();
                            }
                            if ((this.trunkSnake.getDirection() == RIGHT) &&
                                (this.trunks[index].positionX + this.trunks[index].width) < this.trunkSnake.getPositionX() + this.trunkSnake.getWidth()) {
                                this.trunkSnake.toogleDirection();
                            } else if ((this.trunkSnake.getDirection() == LEFT) &&
                                       (this.trunks[index].positionX > this.trunkSnake.getPositionX())) {
                                this.trunkSnake.toogleDirection();
                            }
                            this.trunkSnake.setAditionalStep(step);
                            this.trunkSnake.update(frametime);
                        }
                    }

                    indexLines++;
                }
            }
        }
    }

    /**
     * Draw the graphical vehicles elements
     */
    @Override
    public synchronized void draw(long frametime) {
        int index = 0;
        //draw the main trunks
        for (byte i = 0; i < Stages.TRUNKS[Stages.CURRENT_STAGE[0]].length; i++) {
            for (byte j = 0; Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i].length != 0 && j < Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i][3].length; j++, index++) {
                trunks[index].draw(frametime);
                if (i == 2 && this.hasTrunkInThirdLine && j == 0) {
                    this.trunkSnake.draw(frametime);
                }
            }
        }
        //draw the offset trunks (when necessary)
        for (byte j = 0; j < offsetTrunks.length; j++) {
            if (offsetTrunks[j].positionX > far) {
                offsetTrunks[j].draw(frametime);
                if (j == 1) {
                    this.trunkSnake.draw(frametime);
                }
            }
        }
    }

    /**
     * Set the stage
     */
    @Override
    public void nextStage() {
        //stop update
        this.stopped = true;
        this.reseting = true;

        //clean the current trunks array
        for (int i = 0; this.trunks != null && i < this.trunks.length; i++) {
            this.trunks[i] = null;
        } for (int i = 0; this.offsetTrunks != null && i < this.offsetTrunks.length; i++) {
            this.offsetTrunks[i] = null;
        }

        //create new array with the trunks of this stage
        this.trunks         = new Trunk[Stages.CURRENT_STAGE_TRUNKS[Stages.CURRENT_STAGE[0]]];
        this.offsetTrunks   = new Trunk[5];
        this.offsetPosX     = new double[offsetTrunks.length];

        //instantiate the trunks objects and the offset trunks
        for (byte i = 0; i < trunks.length; i++) {
            trunks[i] = new Trunk(this);
            trunks[i].setScenarioOffsetY(this.scenarioRef.getScoreHeight());
        } 
        for (byte i = 0; i < offsetTrunks.length; i++) {
            offsetTrunks[i]             = new Trunk(this);
            offsetTrunks[i].positionX   = far;
            offsetTrunks[i].setScenarioOffsetY(this.scenarioRef.getScoreHeight());
        }

        //save og pos for reset
        for (int i = 0, index = 0; i < Stages.TRUNKS[Stages.CURRENT_STAGE[0]].length; i++) {
            if (Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i].length != 0) {
                if (i == 2) {
                    this.hasTrunkInThirdLine = true;
                }
                for (int j = 0; j < Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i][3].length; j++) {
                    this.trunks[index++].ogPositionX = Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i][3][j];
                }
            }
        }

        //set new object to the snake in the trunk 
        this.trunkSnake.nextStage();

        //start the update
        this.stopped = false;
        this.reseting = false;
    }

    @Override
    protected Sprite[] getSpriteCollection() {
        return (java.util.stream.Stream.concat(java.util.Arrays.stream(this.trunks), 
                                               java.util.Arrays.stream(this.offsetTrunks)).toArray(Sprite[]::new));
    }
    
    /**
     * test the colision
     */
    @Override
    public int testCollision(Sprite sprite) {
        int colliding = super.testCollision(sprite);
        if (colliding == -1) {
            return (colliding);
        } else {
            if (this.getSpriteCollection()[colliding].getType() == 3) {
                Sprite temp = this.getSpriteCollection()[colliding];
                if (temp.isColliding(sprite, 98, 0)) {
                    return (-2);
                } else {
                    return (colliding);
                }
            }
        }
        return (colliding);
    }

    /**
     * Toogle the stop control
     */
    public void toogleStop() {
        this.stopped = !this.stopped;
    }

    /**
     * Reset the current trucks state
     */
    public void reset() {
        for (int i = 0, index = 0; i < Stages.TRUNKS[Stages.CURRENT_STAGE[0]].length; i++) {
            if (Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i].length != 0) {
                for (int j = 0; j < Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i][3].length; j++) {
                    Stages.TRUNKS[Stages.CURRENT_STAGE[0]][i][3][j] = this.trunks[index++].ogPositionX;
                }
            }
        }
        for (byte i = 0; i < offsetTrunks.length; i++) {
            offsetTrunks[i].positionX   = far;
        }
    }

    //getters
    @Override
    public Graphics2D getG2D() {return (this.scenarioRef.getGameRef().getG2D());}
    public TrunkSnake getTrunkSnake()   { return (this.trunkSnake); }
}