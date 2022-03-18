package game;

import java.awt.Graphics2D;
import interfaces.SpriteCollection;
import java.awt.image.BufferedImage;

import interfaces.Stages;

/**
 * Class represents the gator head
 */
public class GatorHead extends SpriteImpl {

    private BufferedImage gatorSprite               = null;
    private volatile SpriteCollection spriteColRef  = null;
    private volatile Dockers dockers                = null;
    private final double [] positionsX              = {71, 347, 623, 899, 1175};
    private volatile boolean finished               = false;
    private volatile long framecounter              = 0;
    private volatile boolean isVisible              = false;
    private volatile byte sorted                    = -1;

    /**
     * Constructor
     */
    public GatorHead(SpriteCollection spriteCol) {
        this.spriteColRef   = spriteCol;
        this.gatorSprite    = (BufferedImage)LoadingStuffs.getInstance().getStuff("gator-head");
        this.dockers        = (Dockers)this.spriteColRef;
        this.width          = (short)this.gatorSprite.getWidth();
        this.height         = (byte)this.gatorSprite.getHeight();
        this.positionY      = 17;
    }

    @Override
    public synchronized void update(long frametime) {
        //increment framecounter
        this.framecounter += frametime;

        //just one per cycle
        if (this.framecounter == frametime) {
            //start the process
            this.finished = false;

            if ((!dockers.getDockersComplete()) && (dockers.getFreeDockersCounter() > 1)) {
                //recover the taked dockers
                boolean [] isInWhichDock = dockers.getIsInDock();

                //sort one free docker
                do {
                    sorted = (byte)(Math.random() * 5);
                } while (isInWhichDock[sorted] != false || sorted == this.dockers.getCurrentMosquito());

                //after sort, set the gator head
                this.dockers.setCurrentGatorHead(sorted);
                
                //update the X & Y position
                this.positionX = this.positionsX[sorted];
            }
        } else {
            if (sorted != -1) {
                //set visible
                this.isVisible = true;

                //duration
                if (this.framecounter >= (Stages.GATOR_HEAD_CONFIG[Stages.CURRENT_STAGE[0]][1] * 1_000_000_000L)) {
                    this.setInvisible();
                    this.dockers.setCurrentGatorHead((byte)-1);
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
            g2d.drawImage(this.gatorSprite, (int)this.positionX, (int)this.positionY + this.scenarioOffsetY, (int)(this.positionX + this.width), (int)(this.positionY + this.height + this.scenarioOffsetY), //dest w1, h1, w2, h2
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
        this.dockers.setCurrentGatorHead((byte)-1);
    }

    /**
     * Verify if the gator is in the Docker
     */
    public synchronized boolean isInTheDocker(int docker) {
        return (this.sorted == docker);
    }

    /**
     * Reset the gator
     */
    public void reset() {
        this.setInvisible();
    }
}