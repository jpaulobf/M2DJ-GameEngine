import interfaces.IGame;
import interfaces.Lanes;
import interfaces.Stages;

/**
 * Represents the Snake in the sidewalk
 */
public class SidewalkSnake extends Snake {

    private volatile boolean sorted = false;

    /**
     * Class constructor
     */
    public SidewalkSnake(IGame game, int windowWidth) {
        super(game, windowWidth);
        this.nextStage();
    }

    @Override
    public void update(long frametime) {
        if (!this.stopped) {
            if (Stages.SNAKE_SPEED[Stages.CURRENT_STAGE[0]][0] > -1) {
                //add the framecounter
                this.framecounter += frametime;
                if (this.framecounter >= 150_000_000) {
                    this.positionXFrame = (byte)((this.positionXFrame + 1)%4);
                    this.framecounter = 0;
                }

                //calc the tile frame
                this.positionXSource = this.positionXFrame * this.width;

                //sort what to do, when reach the outerscreen
                if (this.positionX < -2*this.width || this.positionX > this.windowWidth + this.width) {
                    if (!this.sorted) {
                        this.sortDirection();
                    }
                } else {
                    this.sorted = false;
                }

                //calc step times direction
                this.calcPosition += ((double)velocity / (double)(1_000_000D / (double)frametime) * direction);
                this.positionX = (short)(this.calcPosition/1000);
            } else {
                this.positionX  = -1000;
                this.visible    = false;
            }
        }
    }

    /**
     * sort the next direction
     */
    private synchronized void sortDirection() {
        byte dice = (byte)(Math.random() * 10);
        if (dice < 5) {
            if (this.direction == LEFT) {
                this.direction = RIGHT;
            } else {
                this.direction = LEFT;
            }
        } else {
            if (this.direction == LEFT) {
                this.calcPosition = (this.windowWidth + this.width) * 1000;
            } else {
                this.calcPosition = (-2*this.width) * 1000;
            }
            this.positionX = (short)(this.calcPosition/1000);
        }
        this.sorted = true;
    }

    /**
     * Set the stage
     */
    public void nextStage() {
        this.positionY      = Lanes.streetLanes[0] - 64;
        this.velocity       = Stages.SNAKE_SPEED[Stages.CURRENT_STAGE[0]][0];
        this.reset();
    }

     /**
     * Reset the snake
     */
    public void reset() {
        this.framecounter   = 0;
        this.direction      = RIGHT;
        this.positionX      = -2*this.width;
        this.calcPosition   = this.positionX * 1000;
        if (Stages.SNAKE_SPEED[Stages.CURRENT_STAGE[0]][0] > -1) {
            this.visible = true;
        }
    }
}