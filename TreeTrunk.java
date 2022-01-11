import java.awt.Graphics2D;
import java.awt.image.VolatileImage;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.AlphaComposite;

/**
 * Tree trunk (individual) class
 */
public class TreeTrunk  extends SpriteImpl {

    //define the g2d and the trunks-images
    private VolatileImage trunksTiles   = null;
    private VolatileImage trunk         = null;
    private VolatileImage trunkSmall    = null;
    private VolatileImage trunkMedium   = null;
    private VolatileImage trunkLarge    = null;
    private Graphics2D bgd2             = null;
    private int trunkHeight             = 32;
    private int trunkLeftSideX          = 1;
    private int trunkRightSideX         = 40;
    private int trunkFirstPartX         = 83;
    private int trunkSecondPartX        = 108;
    private int trunkLeftSideW          = 38;
    private int trunkRightSideW         = 40;
    private int trunkFirstPartW         = 24;
    private int trunkSecondPartW        = 24;
    private final int smallWidth        = trunkLeftSideW + trunkFirstPartW + trunkSecondPartW + trunkRightSideW;
    private final int mediumWidth       = trunkLeftSideW + trunkFirstPartW + trunkSecondPartW + trunkFirstPartW + trunkSecondPartW + trunkRightSideW;
    private final int largeWidth        = trunkLeftSideW + trunkFirstPartW + trunkSecondPartW + trunkFirstPartW + trunkSecondPartW + trunkFirstPartW + trunkSecondPartW + trunkRightSideW;

    /**
     * constructor
     * @param g2d
     */
    public TreeTrunk(Graphics2D g2d) {
        this.g2d = g2d;
        //Get the already loaded image from loader
        this.trunksTiles    = (VolatileImage)LoadingStuffs.getInstance().getStuff("trunksTiles");
        this.trunkSmall     = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleVolatileImage(smallWidth, trunkHeight, Transparency.BITMASK);
        this.trunkMedium    = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleVolatileImage(mediumWidth, trunkHeight, Transparency.BITMASK);
        this.trunkLarge     = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleVolatileImage(largeWidth, trunkHeight, Transparency.BITMASK);

        //create the trunks images...
        this.createImageTrunk();

        this.type = 0;
    }

    @Override
    public void draw(long frametime) {
        //draw the selected image
        direction = (direction == LEFT)?0:direction;
        //this.g2d.drawImage(this.trunksTiles, this.positionX, this.positionY, this.positionX + 0, this.positionY + this.height, //dest w1, h1, w2, h2
        switch(this.type) {
            case 0:
                this.trunk = this.trunkSmall;
                break;
            case 1:
                this.trunk = this.trunkMedium;
                break;
            case 2:
                this.trunk = this.trunkLarge;
                break;
        }

        this.g2d.drawImage(this.trunk, 0, 0, this.trunk.getWidth(), this.trunk.getHeight(), //dest w1, h1, w2, h2
                                       0, 0, this.trunk.getWidth(), this.trunk.getHeight(), //source w1, h1, w2, h2
                                       null);
    }

    @Override
    public void update(long frametime) {}

    private void createImageTrunk() {

        //first, construct the small trunk
        this.bgd2 = (Graphics2D)trunkSmall.getGraphics();
        this.bgd2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT));
        this.bgd2.setColor(new java.awt.Color(255,255,255,0));
        this.bgd2.fillRect(0, 0, this.trunkSmall.getWidth(), this.trunkSmall.getHeight());
        this.bgd2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        //first part 
        this.bgd2.drawImage(this.trunksTiles, 0, 0, trunkLeftSideW, trunkHeight, //dest w1, h1, w2, h2
                                              trunkLeftSideX, 0, trunkLeftSideW, trunkHeight, //source w1, h1, w2, h2
                                              null);
        //second part
        this.bgd2.drawImage(this.trunksTiles, trunkLeftSideW, 0, trunkLeftSideW + trunkFirstPartW, trunkHeight, //dest w1, h1, w2, h2
                                              trunkFirstPartX, 0, trunkFirstPartW + trunkFirstPartX, trunkHeight, //source w1, h1, w2, h2
                                              null);
        //third part
        this.bgd2.drawImage(this.trunksTiles, (trunkLeftSideW + trunkFirstPartW), 0, (trunkLeftSideW + trunkFirstPartW + trunkSecondPartW), trunkHeight, //dest w1, h1, w2, h2
                                               trunkSecondPartX, 0, trunkSecondPartW + trunkSecondPartX, trunkHeight, //source w1, h1, w2, h2
                                               null);

        //forth part
        this.bgd2.drawImage(this.trunksTiles, (trunkLeftSideW + trunkFirstPartW + trunkSecondPartW), 0, (trunkLeftSideW + trunkFirstPartW + trunkSecondPartW + trunkRightSideW), trunkHeight, //dest w1, h1, w2, h2
                                               trunkRightSideX, 0, trunkRightSideW + trunkRightSideX, trunkHeight, //source w1, h1, w2, h2
                                               null);

        //TODO: CREATE THE SECOND IMAGE...
    }
}
