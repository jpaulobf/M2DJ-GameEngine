package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import interfaces.SpriteCollection;

/**
 * Tree trunk (individual) class
 */
public class Trunk  extends SpriteImpl {

    //define the g2d and the trunks-images
    private BufferedImage trunksTiles       = null;
    private BufferedImage placeHolder       = null;
    private BufferedImage trunkSmall        = null;
    private BufferedImage trunkMedium       = null;
    private BufferedImage trunkLarge        = null;
    private BufferedImage gatorSprite       = null;
    private BufferedImage gatorA            = null;
    private BufferedImage gatorB            = null;
    private Graphics2D bgd2                 = null;
    private final byte trunkLeftSideX       = 1;
    private final byte trunkRightSideX      = 40;
    private final byte trunkFirstPartX      = 83;
    private final byte trunkSecondPartX     = 108;
    private final byte trunkLeftSideW       = 38;
    private final byte trunkRightSideW      = 40;
    private final byte trunkFirstPartW      = 24;
    private final byte trunkSecondPartW     = 24;
    private final byte gatorBodyW           = 88;
    private final byte gatorHeadW           = 41;
    private final byte gatorH               = 44;
    private final short smallWidth          = trunkLeftSideW + trunkFirstPartW + trunkSecondPartW + trunkRightSideW;
    private final short mediumWidth         = trunkLeftSideW + trunkFirstPartW + trunkSecondPartW + trunkFirstPartW + trunkSecondPartW + trunkRightSideW;
    private final short largeWidth          = trunkLeftSideW + trunkFirstPartW + trunkSecondPartW + trunkFirstPartW + trunkSecondPartW + trunkFirstPartW + trunkSecondPartW + trunkFirstPartW + trunkSecondPartW + trunkRightSideW;
    private SpriteCollection spriteColRef   = null;
    private volatile long framecounter      = 0;

    /**
     * constructor
     * @param g2d
     */
    public Trunk(SpriteCollection spriteCol) {
        this.spriteColRef   = spriteCol;

        if (this.type != 3) {
            this.height = 32;
        } else {
            this.height = this.gatorH;
        }
        
        //Get the already loaded image from loader
        this.trunksTiles    = (BufferedImage)LoadingStuffs.getInstance().getStuff("trunksTiles");
        this.gatorSprite    = (BufferedImage)LoadingStuffs.getInstance().getStuff("gator");
        this.trunkSmall     = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(smallWidth, this.height, Transparency.BITMASK);
        this.trunkMedium    = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(mediumWidth, this.height, Transparency.BITMASK);
        this.trunkLarge     = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(largeWidth, this.height, Transparency.BITMASK);
        this.gatorA         = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage((this.gatorBodyW + this.gatorHeadW), this.gatorH, Transparency.BITMASK);
        this.gatorB         = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage((this.gatorBodyW + this.gatorHeadW), this.gatorH, Transparency.BITMASK);

        //create the trunks images...
        this.createImages();
    }

    @Override
    public void update(long frametime) {
        this.framecounter += frametime;
        //draw the selected image
        this.height = 32;
        switch(this.type) {
            case 0:
                this.placeHolder = this.trunkSmall;
                this.width = this.smallWidth;
                break;
            case 1:
                this.placeHolder = this.trunkMedium;
                this.width = this.mediumWidth;
                break;
            case 2:
                this.placeHolder = this.trunkLarge;
                this.width = this.largeWidth;
                break;
            case 3:
                if (this.framecounter < 1_000_000_000L) {
                    this.placeHolder = this.gatorA;
                } else {
                    this.placeHolder = this.gatorB;
                    if (this.framecounter + frametime > 2_000_000_000L) {
                        this.framecounter = 0;
                    }
                }
                this.width = (short)(this.gatorBodyW + this.gatorHeadW);
                this.height = this.gatorH;
                break;
        }
    }

    @Override
    public void draw(long frametime) {
        this.spriteColRef.getG2D().drawImage(this.placeHolder, (int)this.positionX, (int)this.positionY + this.scenarioOffsetY, (int)(this.positionX + this.placeHolder.getWidth()), (int)(this.positionY + this.height + this.scenarioOffsetY), //dest w1, h1, w2, h2
                                                                0, 0, this.placeHolder.getWidth(), this.placeHolder.getHeight(), //source w1, h1, w2, h2
                                                                null);
    }

    /**
     * Create the 3 images for the diferent trunks
     */
    private void createImages() {

        //first, construct the gator A
        this.bgd2 = (Graphics2D)gatorA.getGraphics();

        //first part 
        this.bgd2.drawImage(this.gatorSprite, 0, 0, this.gatorBodyW, this.height, //dest w1, h1, w2, h2
                                              0, 0, this.gatorBodyW, this.gatorH, //source w1, h1, w2, h2
                                              null);

        //second part
        this.bgd2.drawImage(this.gatorSprite, this.gatorBodyW, 0, this.gatorBodyW + this.gatorHeadW, this.height, //dest w1, h1, w2, h2
                                              this.gatorBodyW, 0, (this.gatorBodyW + this.gatorHeadW), this.gatorH, //source w1, h1, w2, h2
                                              null);


        //second, construct the gator B
        this.bgd2 = (Graphics2D)gatorB.getGraphics();

        //first part 
        this.bgd2.drawImage(this.gatorSprite, 0, 0, this.gatorBodyW, this.height, //dest w1, h1, w2, h2
                                              0, 0, this.gatorBodyW, this.gatorH, //source w1, h1, w2, h2
                                              null);

        //second part
        this.bgd2.drawImage(this.gatorSprite, this.gatorBodyW, 0, this.gatorBodyW + this.gatorHeadW, this.height, //dest w1, h1, w2, h2
                                              this.gatorBodyW + this.gatorHeadW, 0, (this.gatorBodyW + this.gatorHeadW + this.gatorHeadW), this.gatorH, //source w1, h1, w2, h2
                                              null);
                                
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