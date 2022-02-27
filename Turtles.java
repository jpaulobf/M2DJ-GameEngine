import interfaces.Sprite;
import interfaces.SpriteCollection;
import interfaces.Stages;
import java.awt.Graphics2D;
import interfaces.Lanes;

/**
 * Collection of turtles class
 */
public class Turtles extends SpriteCollection {

    //define the turtles array
    private Turtle[] turtles            = new Turtle[Stages.CURRENT_STAGE_TURTLES[Stages.CURRENT_STAGE]];
    private Turtle[] offsetTurtles      = new Turtle[Stages.TURTLES.length];
    private double[] offsetPosX         = new double[offsetTurtles.length];
    private final short far             = -10_000;
    private int windowWidth1000         =  0;

    //Screen size ang G2D
    protected int windowWidth           = 0;
    protected int windowHeight          = 0;
    private volatile boolean stopped    = false;
    private Scenario scenarioRef        = null;

    /**
     * Constructor
     * @param g2d
     * @param windowWidth
     * @param windowHeight
     */
    public Turtles(Scenario scenarioRef, int windowWidth, int windowHeight) {
        this.scenarioRef        = scenarioRef;
        this.windowWidth        = windowWidth;
        this.windowHeight       = windowHeight;
        this.windowWidth1000    = this.windowWidth * 1_000;

        //instantiate the turtles objects and the offset turtles
        for (byte i = 0; i < turtles.length; i++) {
            turtles[i] = new Turtle(this);
            turtles[i].setScenarioOffsetY(this.scenarioRef.getScoreHeight());
        } 
        for (byte i = 0; i < offsetTurtles.length; i++) {
            offsetTurtles[i]             = new Turtle(this);
            offsetTurtles[i].positionX   = far;
            offsetTurtles[i].setScenarioOffsetY(this.scenarioRef.getScoreHeight());
        }

        for (int i = 0, index = 0; i < Stages.TURTLES[Stages.CURRENT_STAGE].length; i++) {
            if (Stages.TURTLES[Stages.CURRENT_STAGE][i].length != 0) {
                for (int j = 0; j < Stages.TURTLES[Stages.CURRENT_STAGE][i][3].length; j++) {
                    this.turtles[index++].ogPositionX  = Stages.TURTLES[Stages.CURRENT_STAGE][i][4][j];
                }
            }
        }
    }

    @Override
    public void update(long frametime) {

        byte index              = 0;
        byte indexLines         = 0;
        byte positionYOffset    = 16;

        if (!this.stopped) {
            for (int i = 0; i < Stages.TURTLES[Stages.CURRENT_STAGE].length; i++) {

                if (Stages.TURTLES[Stages.CURRENT_STAGE][i].length != 0) {

                    byte direction      = (byte)Stages.TURTLES[Stages.CURRENT_STAGE][i][0][0];
                    short velocity      = (short)Stages.TURTLES[Stages.CURRENT_STAGE][i][1][0];

                    for (int j = 0; j < Stages.TURTLES[Stages.CURRENT_STAGE][i][3].length; j++, index++) {

                        //read & set the turtles parameters
                        double step                     = (double)velocity / (double)(1_000_000D / (double)frametime);
                        double stepDir                  = step * direction;
                        double position                 = Stages.TURTLES[Stages.CURRENT_STAGE][i][4][j];
                        double calcPos                  = position + stepDir;
                        byte dive                       = (byte)Stages.TURTLES[Stages.CURRENT_STAGE][i][3][j];
                        turtles[index].calculatedStep   = stepDir;
                        turtles[index].type             = (byte)Stages.TURTLES[Stages.CURRENT_STAGE][i][2][0];
                        turtles[index].velocity         = velocity;

                        //update the turtles
                        turtles[index].update(frametime);

                        //update the width based on type
                        short width                     = turtles[index].getWidth();
                        int width1000                   = width * 1_000;
                        int windowWidthLessWidth1000    = ((this.windowWidth - width) * 1_000);

                        if ((calcPos < 0) && calcPos > (-width1000)) {
                            //define the necessary offset sprite parameters
                            this.offsetTurtles[indexLines].type       = this.turtles[index].type;
                            this.offsetTurtles[indexLines].direction  = direction;

                            //test if the position is "far" (first time), in this case, utilises the width of the turtles (reverse)
                            //otherwise, sum the current position to the next distance step
                            if (this.offsetTurtles[indexLines].positionX == this.far) {
                                this.offsetPosX[indexLines] = this.windowWidth1000;
                            } else {
                                this.offsetPosX[indexLines] = (this.offsetPosX[indexLines] + stepDir);
                            }

                            //set the offset turtles parameters
                            this.offsetTurtles[indexLines].positionX        = (short)(this.offsetPosX[indexLines]/1_000);
                            this.offsetTurtles[indexLines].positionY        = (short)Lanes.riverLanes[i] + positionYOffset; 
                            this.offsetTurtles[indexLines].calculatedStep   = stepDir;
                            this.offsetTurtles[indexLines].update(frametime);

                        } else if (calcPos < (-width1000)) {
                            calcPos = windowWidthLessWidth1000;
                        } else if (calcPos < windowWidthLessWidth1000 && calcPos > windowWidthLessWidth1000 - 5_000) {
                            this.offsetTurtles[indexLines].positionX = this.far;
                        }

                        //store the new X position in the array
                        Stages.TURTLES[Stages.CURRENT_STAGE][i][4][j]      = (int)Math.round(calcPos);

                        //set the turtles parameters
                        this.turtles[index].direction    = direction;
                        this.turtles[index].dive         = dive;
                        this.turtles[index].positionX    = (short)(position/1_000);
                        this.turtles[index].positionY    = (short)Lanes.riverLanes[i] + positionYOffset; //incrementa o index ao final
                    }

                    if (Stages.TURTLES[Stages.CURRENT_STAGE][i].length > 0) {
                        indexLines++;
                    }
                }
            }
        }
    }

    @Override
    public void draw(long frametime) {
        int index = 0;
        //draw the turtles
        for (byte i = 0; i < Stages.TURTLES[Stages.CURRENT_STAGE].length; i++) {
            for (byte j = 0; Stages.TURTLES[Stages.CURRENT_STAGE][i].length != 0 && j < Stages.TURTLES[Stages.CURRENT_STAGE][i][3].length; j++, index++) {
                turtles[index].draw(frametime);
            }
        }
        //draw the offset turtles (when necessary)
        for (byte j = 0; j < offsetTurtles.length; j++) {
            if (offsetTurtles[j].positionX > far) {
                offsetTurtles[j].draw(frametime);
            }
        }
    }

    @Override
    protected Sprite[] getSpriteCollection() {
        return (java.util.stream.Stream.concat(java.util.Arrays.stream(this.turtles), 
                                               java.util.Arrays.stream(this.offsetTurtles)).toArray(Sprite[]::new));
    }
    
    /**
     * Toogle the stop control
     */
    public void toogleStop() {
        this.stopped = !this.stopped;
    }

    /**
     * reset method
     */
    public void reset() {
        for (int i = 0, index = 0; i < Stages.TURTLES[Stages.CURRENT_STAGE].length; i++) {
            if (Stages.TURTLES[Stages.CURRENT_STAGE][i].length != 0) {
                for (int j = 0; j < Stages.TURTLES[Stages.CURRENT_STAGE][i][3].length; j++) {
                    Stages.TURTLES[Stages.CURRENT_STAGE][i][4][j] = this.turtles[index].ogPositionX;
                    this.turtles[index++].resetAnimation();
                }
            }
        }
        for (byte i = 0; i < offsetTurtles.length; i++) {
            offsetTurtles[i].positionX = far;
            offsetTurtles[i].resetAnimation();
        }
    }

    @Override
    public Graphics2D getG2D() {
        return (this.scenarioRef.getGameRef().getG2D());
    }
}
