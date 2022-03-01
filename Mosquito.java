import java.awt.Graphics2D;
import interfaces.SpriteCollection;
import java.awt.image.BufferedImage;

/**
 * Mosquito class
 */
public class Mosquito extends SpriteImpl {

    private BufferedImage mosquitoSprite    = null;
    private SpriteCollection spriteColRef   = null;
    private Dockers dockers                 = null;
    private final double [] positionsX      = {102, 378, 654, 930, 1206};
    private volatile boolean sorting        = true;
    private volatile long framecounter      = 0;
    private volatile boolean isVisible      = false;
    private volatile byte sorted            = -1;

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
    public void update(long frametime) {
        if (this.sorting) {
            if (!dockers.getDockersComplete()) {
                //recover the taked dockers
                boolean [] isInWhichDock = dockers.getIsInDock();

                //sort one free docker
                //TODO: TEST AGAINST GATOR-HEAD
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
            g2d.drawImage(this.mosquitoSprite, (int)this.positionX, (int)this.positionY + this.scenarioOffsetY, (int)(this.positionX + this.width), (int)(this.positionY + this.height + this.scenarioOffsetY), //dest w1, h1, w2, h2
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
     * Return if mosquito is animating (?)
     * @return
     */
    public boolean isSorting() {
        return (this.sorting);
    }

    /**
     * Verify if the mosquito is in the Docker
     */
    public boolean isInTheDocker(int docker) {
        return (this.sorted == docker);
    }

    /**
     * Reset the mosquito
     */
    public void reset() {
        this.setInvisible();
    }
}