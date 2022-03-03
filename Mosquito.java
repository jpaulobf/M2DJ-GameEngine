import java.awt.Graphics2D;
import interfaces.SpriteCollection;
import interfaces.Stages;
import java.awt.image.BufferedImage;

/**
 * Mosquito class
 */
public class Mosquito extends SpriteImpl {

    private BufferedImage mosquitoSprite            = null;
    private volatile SpriteCollection spriteColRef  = null;
    private volatile Dockers dockers                = null;
    private final double [] positionsX              = {102, 378, 654, 930, 1206};
    private volatile boolean finished               = false;
    private volatile long framecounter              = 0;
    private volatile boolean isVisible              = false;
    private volatile byte sorted                    = -1;

    /**
     * Constructor
     */
    public Mosquito(SpriteCollection spriteCol) {
        this.spriteColRef   = spriteCol;
        this.mosquitoSprite = (BufferedImage)LoadingStuffs.getInstance().getStuff("mosquito");
        this.dockers        = (Dockers)this.spriteColRef;
        this.width          = (short)this.mosquitoSprite.getWidth();
        this.height         = (byte)this.mosquitoSprite.getHeight();
        this.positionY      = (this.height/2) + 15;
    }

    @Override
    public synchronized void update(long frametime) {
        //increment framecounter
        this.framecounter += frametime;
        
        //just one per cycle
        if (this.framecounter == frametime) {
            //start the process
            this.finished = false;

            if (!dockers.getDockersComplete()) {
                //recover the taked dockers
                boolean [] isInWhichDock = dockers.getIsInDock();

                //sort one free docker
                do {
                    this.sorted = (byte)(Math.random() * 5);
                } while (isInWhichDock[sorted] != false || sorted == this.dockers.getCurrentGatorHead());

                //after sort, set the mosquito
                this.dockers.setCurrentMosquito(sorted);

                //update the X & Y position
                this.positionX = this.positionsX[sorted];
            }
        } else {
            if (this.sorted != -1) {
                //set visible
                this.isVisible = true;

                //duration
                if (this.framecounter >= (Stages.MOSQUITO_CONFIG[Stages.CURRENT_STAGE][1] * 1_000_000_000L)) {
                    this.setInvisible();
                    this.finished = true;
                }
            } else {
                this.finished = true;
            }
        }
    }

    @Override
    public synchronized void draw(long frametime) {
        if (this.isVisible) {
            Graphics2D g2d = this.spriteColRef.getG2D();
            g2d.drawImage(this.mosquitoSprite, (int)this.positionX, (int)this.positionY + this.scenarioOffsetY, (int)(this.positionX + this.width), (int)(this.positionY + this.height + this.scenarioOffsetY), //dest w1, h1, w2, h2
                                                0, 0, this.width, this.height, //source w1, h1, w2, h2
                                                null);
        }
    }

    /**
     * Verify if the appearence (in this cycle) has finished
     * @return
     */
    public boolean appearenceFinished() {
        return (this.finished);
    }

    /**
     * Makes the mosquito invisible
     */
    public synchronized void setInvisible() {
        this.framecounter   = 0;
        this.isVisible      = false;
        this.positionX      = -1000;
        this.sorted         = -1;
        this.dockers.setCurrentMosquito((byte)-1);
    }

    /**
     * Verify if the mosquito is in the Docker
     */
    public synchronized boolean isInTheDocker(int docker) {
        return (this.sorted == docker);
    }

    /**
     * Reset the mosquito
     */
    public void reset() {
        this.setInvisible();
    }
}