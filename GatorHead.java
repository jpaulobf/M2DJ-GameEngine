import java.awt.Graphics2D;
import interfaces.SpriteCollection;
import java.awt.image.BufferedImage;

/**
 * Class represents the gator head
 */
public class GatorHead extends SpriteImpl {

    private BufferedImage gatorSprite       = null;
    private SpriteCollection spriteColRef   = null;
    private Dockers dockers                 = null;
    private final double [] positionsX      = {71, 347, 623, 899, 1175};
    private volatile boolean sorting        = true;
    private volatile long framecounter      = 0;
    private volatile boolean isVisible      = false;
    private volatile byte sorted            = -1;

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
    public void update(long frametime) {
        if (this.sorting) {
            if ((!dockers.getDockersComplete()) && (dockers.getFreeDockersCounter() > 1)) {
                //recover the taked dockers
                boolean [] isInWhichDock = dockers.getIsInDock();

                //sort one free docker
                //TODO: TEST AGAINST MOSQUITO
                do {
                    sorted = (byte)(Math.random() * 5);
                } while (isInWhichDock[sorted] != false);
                
                //update the X & Y position
                this.positionX = this.positionsX[sorted];

                //set animating true
                this.sorting = false;
            }
        } else {
            //set visible
            this.isVisible = true;
            this.framecounter += frametime;

            if (this.framecounter >= 5_000_000_000L) {
                this.setInvisible();
            }
        }
    }

    @Override
    public void draw(long frametime) {
        if (this.isVisible) {
            Graphics2D g2d = this.spriteColRef.getG2D();
            g2d.drawImage(this.gatorSprite, (int)this.positionX, (int)this.positionY + this.scenarioOffsetY, (int)(this.positionX + this.width), (int)(this.positionY + this.height + this.scenarioOffsetY), //dest w1, h1, w2, h2
                                            0, 0, this.width, this.height, //source w1, h1, w2, h2
                                            null);
        }
    }

    /**
     * Makes the mosquito invisible
     */
    public void setInvisible() {
        this.sorting        = true;
        this.framecounter   = 0;
        this.isVisible      = false;
        this.positionX      = -1000;
        this.sorted         = -1;
    }

    /**
     * Return if gator is animating (?)
     * @return
     */
    public boolean isSorting() {
        return (this.sorting);
    }

    /**
     * Verify if the gator is in the Docker
     */
    public boolean isInTheDocker(int docker) {
        return (this.sorted == docker);
    }

    /**
     * Reset the gator
     */
    public void reset() {
        this.setInvisible();
    }

}