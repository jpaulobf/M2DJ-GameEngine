import interfaces.IGame;
import interfaces.Lanes;
import interfaces.Stages;

/**
 * Represents the Snake in the Trunk
 */
public class TrunkSnake extends Snake {

    private volatile double additionalStep  = 0;
    private volatile boolean enabled        = false;

    //getter
    public boolean isEnabled()  {   return (this.enabled);  }

    /**
     * Class constructor
     */
    public TrunkSnake(IGame game, int windowWidth) {
        //call super constructor
        super(game, windowWidth);
        
        //recover the speed (if -1 - no snake)
        this.velocity = Stages.SNAKE_SPEED[Stages.CURRENT_STAGE[0]][1];

        //then recover position
        if (this.velocity > -1) {
            //pixels to center in the trunk
            byte offsetY = 10; 

            //if velocity != -1, this parameter must exist (otherwise, it's a definition error)
            this.positionX      = Stages.TRUNKS[Stages.CURRENT_STAGE[0]][2][3][0];
            this.positionY      = Lanes.riverLanes[2] + offsetY;
            this.calcPosition   = positionX;
            this.direction      = RIGHT;
            this.visible        = true;
            this.enabled        = true;
        } else {
            this.visible        = false;
            this.positionX      = -1_000;
            this.enabled        = false;
        }
    }

    @Override
    public void update(long frametime) {
        if (this.enabled) {
            this.framecounter += frametime;
            if (this.framecounter >= 300_000_000) {
                this.positionXFrame = (byte)((this.positionXFrame + 1)%4);
                this.framecounter = 0;
            }
            //calc the tile frame
            this.positionXSource = this.positionXFrame * this.width;
        
            //calc step times direction
            this.calcPosition += (((double)velocity / (double)(1_000_000D / (double)frametime) * this.direction) + (this.additionalStep));
            this.positionX = (short)(this.calcPosition/1000);
        }
    }

    /**
     * Trunk snake reset method
     */
    public void reset() {
        if ((this.velocity = Stages.SNAKE_SPEED[Stages.CURRENT_STAGE[0]][1]) != -1) {
            this.positionX      = -50_000;
            this.calcPosition   = positionX;
            this.direction      = RIGHT;
            this.enabled        = true;
        } else {
            this.visible        = false;
            this.positionX      = -1_000;
            this.enabled        = false;
        }
    }

    /**
     * Increment the velocity using the trunk velocity
     */
    public void setAditionalStep(double additionalStep) {
        this.additionalStep = additionalStep;
    }

    /**
     * Change snake direction
     */
    public void toogleDirection() {
        if (this.direction == RIGHT) {
            this.direction = LEFT;
        } else {
            this.direction = RIGHT;
        }
    }
}