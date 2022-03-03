import interfaces.IGame;
import interfaces.Lanes;
import interfaces.Stages;

/**
 * Represents the Snake in the Trunk
 */
public class TrunkSnake extends Snake {

    /**
     * Class constructor
     */
    public TrunkSnake(IGame game, int windowWidth) {
        super(game, windowWidth);
        this.positionX      = 100;
        this.positionY      = Lanes.riverLanes[3] - 60;
        this.direction      = RIGHT;
        this.velocity       = Stages.SNAKE_SPEED[Stages.CURRENT_STAGE][0];
        this.calcPosition   = this.positionX;
        this.visible        = false;
    }

    @Override
    public void update(long frametime) {
        if (Stages.SNAKE_SPEED[Stages.CURRENT_STAGE][0] > -1) {
            
            this.framecounter += frametime;
            if (this.framecounter >= 0 && this.framecounter < 150_000_000) {
                this.positionXSource = 0;
            } else if (this.framecounter >= 150_000_000 && this.framecounter < 300_000_000) {
                this.positionXSource = this.width;
            } else if (this.framecounter >= 300_000_000 && this.framecounter < 450_000_000) {
                this.positionXSource = 2 * this.width;
            } else if (this.framecounter >= 450_000_000 && this.framecounter < 600_000_000) {
                this.positionXSource = 3 * this.width;
            } else if (this.framecounter >= 600_000_000 && this.framecounter < 750_000_000) {
                this.positionXSource = 4 * this.width;
            } else {
                this.framecounter = 0;
            }
           
            //calc step times direction
            this.calcPosition += ((double)velocity / (double)(1_000_000D / (double)frametime) * direction);
            this.positionX = (short)(this.calcPosition/1000);

        } else {
            this.positionX = -1000;
            this.visible = false;
        }
    }

    

}