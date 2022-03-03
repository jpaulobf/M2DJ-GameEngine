import interfaces.IGame;
import java.awt.image.BufferedImage;

/**
 * Base class for the snake
 */
public abstract class Snake extends SpriteImpl {

    protected volatile byte tileElements        = 0;
    protected volatile byte currentTyle         = 0;
    protected IGame gRef                        = null;
    private BufferedImage snakeTile             = null;
    protected volatile double positionXSource   = 0;
    protected volatile long framecounter        = 0;
    protected volatile boolean stopped          = false;
    protected volatile boolean visible          = true;
    protected int windowWidth                   = 0;
    protected double calcPosition               = 0D;

    /**
     * Class constructor
     * @param game
     */
    public Snake(IGame game, int windowWidth) {
        this.height             = 30;
        this.width              = 81;
        this.gRef               = game;
        this.snakeTile          = (BufferedImage)LoadingStuffs.getInstance().getStuff("snake-tile");
        this.windowWidth        = windowWidth;
        this.scenarioOffsetY    = this.gRef.getScenarioOffsetY();
    }

    @Override
    public void draw(long frametime) {
        if (this.visible) {
            if (direction == RIGHT) {
                this.gRef.getG2D().drawImage(this.snakeTile, (int)this.positionX, ((int)this.positionY + this.scenarioOffsetY), ((int)(this.positionX + this.width)), ((int)(this.positionY + this.height + this.scenarioOffsetY)), //dest w1, h1, w2, h2
                                                            ((int)this.positionXSource), (0), ((int)this.positionXSource + this.width), this.height, //source w1, h1, w2, h2
                                                            (null));
            } else {
                this.gRef.getG2D().drawImage(this.snakeTile, ((int)this.positionX + this.width), ((int)this.positionY + this.scenarioOffsetY), ((int)this.positionX), ((int)(this.positionY + this.height + this.scenarioOffsetY)), //dest w1, h1, w2, h2
                                                            ((int)this.positionXSource), (0), ((int)this.positionXSource + this.width), this.height, //source w1, h1, w2, h2
                                                            (null));
            }
        }
    }

    /**
     * Toogle the stop control
     */
    public void tooglePause() {
        this.stopped = !this.stopped;
    }
}