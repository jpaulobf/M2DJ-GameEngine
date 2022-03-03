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
        this.positionY      = Lanes.streetLanes[0] - 64;
        this.velocity       = Stages.SNAKE_SPEED[Stages.CURRENT_STAGE][0];
        this.reset();
    }

    @Override
    public void update(long frametime) {
        if (!this.stopped) {
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
                this.positionX = -1000;
                this.visible = false;
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
     * Reset the snake
     */
    public void reset() {
        this.direction      = RIGHT;
        this.positionX      = -2*this.width;
        this.calcPosition   = this.positionX * 1000;
    }
}