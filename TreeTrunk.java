import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;

/**
 * Tree trunk (individual) class
 */
public class TreeTrunk  extends SpriteImpl {

    //define the g2d and the trunks-images
    private BufferedImage trunksTiles   = null;
    private BufferedImage trunk         = null;
    private BufferedImage trunkSmall    = null;
    private BufferedImage trunkMedium   = null;
    private BufferedImage trunkLarge    = null;
    private Graphics2D bgd2             = null;
    private final byte trunkLeftSideX   = 1;
    private final byte trunkRightSideX  = 40;
    private final byte trunkFirstPartX  = 83;
    private final byte trunkSecondPartX = 108;
    private final byte trunkLeftSideW   = 38;
    private final byte trunkRightSideW  = 40;
    private final byte trunkFirstPartW  = 24;
    private final byte trunkSecondPartW = 24;
    private final short smallWidth      = trunkLeftSideW + trunkFirstPartW + trunkSecondPartW + trunkRightSideW;
    private final short mediumWidth     = trunkLeftSideW + trunkFirstPartW + trunkSecondPartW + trunkFirstPartW + trunkSecondPartW + trunkRightSideW;
    private final short largeWidth      = trunkLeftSideW + trunkFirstPartW + trunkSecondPartW + trunkFirstPartW + trunkSecondPartW + trunkFirstPartW + trunkSecondPartW + trunkFirstPartW + trunkSecondPartW + trunkRightSideW;

    /**
     * constructor
     * @param g2d
     */
    public TreeTrunk(Graphics2D g2d) {
        this.g2d            = g2d;
        this.height         = 32;
        //Get the already loaded image from loader
        this.trunksTiles    = (BufferedImage)LoadingStuffs.getInstance().getStuff("trunksTiles");
        this.trunkSmall     = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(smallWidth, this.height, Transparency.BITMASK);
        this.trunkMedium    = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(mediumWidth, this.height, Transparency.BITMASK);
        this.trunkLarge     = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(largeWidth, this.height, Transparency.BITMASK);

        //create the trunks images...
        this.createImageTrunk();
    }

    @Override
    public void update(long frametime) {
        //draw the selected image
        switch(this.type) {
            case 0:
                this.trunk = this.trunkSmall;
                this.width = this.smallWidth;
                break;
            case 1:
                this.trunk = this.trunkMedium;
                this.width = this.mediumWidth;
                break;
            case 2:
                this.trunk = this.trunkLarge;
                this.width = this.largeWidth;
                break;
        }
    }

    @Override
    public void draw(long frametime) {
        this.g2d.drawImage(this.trunk, (int)this.positionX, (int)this.positionY, (int)(this.positionX + this.trunk.getWidth()), (int)(this.positionY + this.height), //dest w1, h1, w2, h2
                                       0, 0, this.trunk.getWidth(), this.trunk.getHeight(), //source w1, h1, w2, h2
                                       null);
    }

    /**
     * Create the 3 images for the diferent trunks
     */
    private void createImageTrunk() {
        //first, construct the small trunk
        this.bgd2 = (Graphics2D)trunkSmall.getGraphics();

        int leftFirstW                  = trunkLeftSideW + trunkFirstPartW;
        int leftFirstSecondW            = leftFirstW + trunkSecondPartW;
        int leftFirstSecondFirstW       = leftFirstSecondW + trunkFirstPartW;
        int leftFirstSecondFirstSecondW = leftFirstSecondFirstW + trunkSecondPartW;
        
        //first part 
        this.bgd2.drawImage(this.trunksTiles, 0, 0, trunkLeftSideW, this.height, //dest w1, h1, w2, h2
                                              trunkLeftSideX, 0, trunkLeftSideW, this.height, //source w1, h1, w2, h2
                                              null);

        //second part
        this.bgd2.drawImage(this.trunksTiles, trunkLeftSideW, 0, leftFirstW, this.height, //dest w1, h1, w2, h2
                                              trunkFirstPartX, 0, trunkFirstPartX + trunkFirstPartW, this.height, //source w1, h1, w2, h2
                                              null);

        //third part
        this.bgd2.drawImage(this.trunksTiles, leftFirstW, 0, leftFirstSecondW, this.height, //dest w1, h1, w2, h2
                                               trunkSecondPartX, 0, trunkSecondPartW + trunkSecondPartX, this.height, //source w1, h1, w2, h2
                                               null);

        //forth part
        this.bgd2.drawImage(this.trunksTiles, leftFirstSecondW, 0, (leftFirstSecondW + trunkRightSideW), this.height, //dest w1, h1, w2, h2
                                               trunkRightSideX, 0, trunkRightSideW + trunkRightSideX, this.height, //source w1, h1, w2, h2
                                               null);

        //second, construct the medium trunk
        this.bgd2 = (Graphics2D)trunkMedium.getGraphics();

        //first part 
        this.bgd2.drawImage(this.trunksTiles, 0, 0, trunkLeftSideW, this.height, //dest w1, h1, w2, h2
                                              trunkLeftSideX, 0, trunkLeftSideW, this.height, //source w1, h1, w2, h2
                                              null);
        //second part
        this.bgd2.drawImage(this.trunksTiles, trunkLeftSideW, 0, leftFirstW, this.height, //dest w1, h1, w2, h2
                                              trunkFirstPartX, 0, trunkFirstPartW + trunkFirstPartX, this.height, //source w1, h1, w2, h2
                                              null);
        //third part
        this.bgd2.drawImage(this.trunksTiles, leftFirstW, 0, leftFirstSecondW, this.height, //dest w1, h1, w2, h2
                                               trunkSecondPartX, 0, trunkSecondPartW + trunkSecondPartX, this.height, //source w1, h1, w2, h2
                                               null);

        //forth part
        this.bgd2.drawImage(this.trunksTiles, leftFirstSecondW, 0, leftFirstSecondFirstW, this.height, //dest w1, h1, w2, h2
                                              trunkFirstPartX, 0, trunkFirstPartW + trunkFirstPartX, this.height, //source w1, h1, w2, h2
                                              null);
        //fifth part
        this.bgd2.drawImage(this.trunksTiles, leftFirstSecondFirstW, 0, leftFirstSecondFirstSecondW, this.height, //dest w1, h1, w2, h2
                                               trunkSecondPartX, 0, trunkSecondPartW + trunkSecondPartX, this.height, //source w1, h1, w2, h2
                                               null);

        //sixty part
        this.bgd2.drawImage(this.trunksTiles, leftFirstSecondFirstSecondW, 0, (leftFirstSecondFirstSecondW + trunkRightSideW), this.height, //dest w1, h1, w2, h2
                                               trunkRightSideX, 0, trunkRightSideW + trunkRightSideX, this.height, //source w1, h1, w2, h2
                                               null);

        int leftFirstSecondFirstSecondFirstW                    = leftFirstSecondFirstSecondW + trunkFirstPartW;
        int leftFirstSecondFirstSecondFirstSecondW              = leftFirstSecondFirstSecondFirstW + trunkSecondPartW;
        int leftFirstSecondFirstSecondFirstSecondFirstW         = leftFirstSecondFirstSecondFirstSecondW + trunkFirstPartW;
        int leftFirstSecondFirstSecondFirstSecondFirstSecondW   = leftFirstSecondFirstSecondFirstSecondFirstW + trunkSecondPartW;

        //third, construct the large trunk
        this.bgd2 = (Graphics2D)trunkLarge.getGraphics();

        //first part 
        this.bgd2.drawImage(this.trunksTiles, 0, 0, trunkLeftSideW, this.height, //dest w1, h1, w2, h2
                                              trunkLeftSideX, 0, trunkLeftSideW, this.height, //source w1, h1, w2, h2
                                              null);
        //second part
        this.bgd2.drawImage(this.trunksTiles, trunkLeftSideW, 0, leftFirstW, this.height, //dest w1, h1, w2, h2
                                              trunkFirstPartX, 0, trunkFirstPartW + trunkFirstPartX, this.height, //source w1, h1, w2, h2
                                              null);
        //third part
        this.bgd2.drawImage(this.trunksTiles, leftFirstW, 0, leftFirstSecondW, this.height, //dest w1, h1, w2, h2
                                               trunkSecondPartX, 0, trunkSecondPartW + trunkSecondPartX, this.height, //source w1, h1, w2, h2
                                               null);

        //forth part
        this.bgd2.drawImage(this.trunksTiles, leftFirstSecondW, 0, leftFirstSecondFirstW, this.height, //dest w1, h1, w2, h2
                                              trunkFirstPartX, 0, trunkFirstPartW + trunkFirstPartX, this.height, //source w1, h1, w2, h2
                                              null);
        //fifth part
        this.bgd2.drawImage(this.trunksTiles, leftFirstSecondFirstW, 0, leftFirstSecondFirstSecondW, this.height, //dest w1, h1, w2, h2
                                               trunkSecondPartX, 0, trunkSecondPartW + trunkSecondPartX, this.height, //source w1, h1, w2, h2
                                               null);

        //sixty part
        this.bgd2.drawImage(this.trunksTiles, leftFirstSecondFirstSecondW, 0, leftFirstSecondFirstSecondFirstW, this.height, //dest w1, h1, w2, h2
                                              trunkFirstPartX, 0, trunkFirstPartW + trunkFirstPartX, this.height, //source w1, h1, w2, h2
                                              null);
        //seventy part
        this.bgd2.drawImage(this.trunksTiles, leftFirstSecondFirstSecondFirstW, 0, leftFirstSecondFirstSecondFirstSecondW, this.height, //dest w1, h1, w2, h2
                                               trunkSecondPartX, 0, trunkSecondPartW + trunkSecondPartX, this.height, //source w1, h1, w2, h2
                                               null);

        //eighty part
        this.bgd2.drawImage(this.trunksTiles, leftFirstSecondFirstSecondFirstSecondW, 0, leftFirstSecondFirstSecondFirstSecondFirstW, this.height, //dest w1, h1, w2, h2
                                              trunkFirstPartX, 0, trunkFirstPartW + trunkFirstPartX, this.height, //source w1, h1, w2, h2
                                              null);
        //ninety part
        this.bgd2.drawImage(this.trunksTiles, leftFirstSecondFirstSecondFirstSecondFirstW, 0, leftFirstSecondFirstSecondFirstSecondFirstSecondW, this.height, //dest w1, h1, w2, h2
                                               trunkSecondPartX, 0, trunkSecondPartW + trunkSecondPartX, this.height, //source w1, h1, w2, h2
                                               null);

                                               //tenth part
        this.bgd2.drawImage(this.trunksTiles, leftFirstSecondFirstSecondFirstSecondFirstSecondW, 0, (leftFirstSecondFirstSecondFirstSecondFirstSecondW + trunkRightSideW), this.height, //dest w1, h1, w2, h2
                                               trunkRightSideX, 0, trunkRightSideW + trunkRightSideX, this.height, //source w1, h1, w2, h2
                                               null);
    }
}
