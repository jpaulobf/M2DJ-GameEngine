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
        super(game, windowWidth);
        if (Stages.TRUNKS[Stages.CURRENT_STAGE].length != 0 && 
            Stages.TRUNKS[Stages.CURRENT_STAGE][2].length != 0 && 
            Stages.TRUNKS[Stages.CURRENT_STAGE][3].length != 0) {
            this.positionX      = Stages.TRUNKS[Stages.CURRENT_STAGE][2][3][0];
            this.positionY      = Lanes.riverLanes[3] - 60;
            this.velocity       = Stages.SNAKE_SPEED[Stages.CURRENT_STAGE][1];
            this.calcPosition   = positionX;
            this.direction      = RIGHT;
            this.visible        = true;
            this.enabled        = true;
        } else {
            this.visible        = false;
            this.positionX      = -1_000;
        }
        if (this.velocity == -1) {
            this.visible        = false;
            this.positionX      = -1_000;
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

        } else {
            this.positionX = -1_000;
            this.visible = false;
        }
    }

    /**
     * Trunk snake reset method
     */
    public void reset() {
        this.positionX      = -50;
        this.direction      = RIGHT;
        this.calcPosition   = positionX * 1_000;
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