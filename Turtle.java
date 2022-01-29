import interfaces.Stages;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import interfaces.Sprite;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;

/**
 * Class representing the turtle sprite
 */
public class Turtle extends SpriteImpl {

    //vehicles tiles
    private BufferedImage turtlesTiles      = null;
    private BufferedImage turtlesSmall[]    = null;
    private BufferedImage turtlesMedium[]   = null;
    private BufferedImage turtle            = null;
    private Graphics2D bgd2                 = null;

    //for the vehicles tiles
    private final byte turtleH          = 36;
    private final byte turtleW          = 43;
    private final byte separator        = 6;
    private final int turtleFrames      = 6;
    private long framecounter           = 0;
    private int currentFrame            = 0;
    private boolean isSubmersed         = false;
    private final short smallWidth      = turtleW + separator + turtleW;
    private final short mediumWidth     = turtleW + separator + turtleW + separator + turtleW;
    protected byte dive                 = 0;
    private final int [][] frameList    = {{0}, {0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 3, 3, 4, 4, 5, 5, 4, 4, 3, 3, 0, 1, 2}};
    byte index = 0;
    
    /**
     * Turtle constructor
     */
    public Turtle(Graphics2D g2d) {
        this.g2d    = g2d;
        this.height = turtleH;
        this.type   = 0;

        //Get the already loaded image from loader
        this.turtlesTiles   = (BufferedImage)LoadingStuffs.getInstance().getStuff("turtles");
        this.turtlesSmall   = new BufferedImage[6];
        this.turtlesMedium  = new BufferedImage[this.turtlesSmall.length];

        for (int i = 0; i < this.turtlesSmall.length; i++) {
            this.turtlesSmall[i]   = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(smallWidth, this.height, Transparency.BITMASK);
            this.turtlesMedium[i]  = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(mediumWidth, this.height, Transparency.BITMASK);
        }
        
        //create the trunks images...
        this.createTurtlesImage();
    }
    
    @Override
    public void draw(long frametime) {
        this.g2d.drawImage(this.turtle, (int)this.positionX, (int)this.positionY, (int)(this.positionX + this.turtle.getWidth()), (int)(this.positionY + this.height), //dest w1, h1, w2, h2
                                        0, 0, this.turtle.getWidth(), this.turtle.getHeight(), //source w1, h1, w2, h2
                                        null);
    }

    @Override
    public void update(long frametime) {

        this.framecounter += frametime;
        if (dive == 0) {
            if (this.framecounter > 300_000_000) {
                this.currentFrame++;
                this.framecounter = 0;
                if (this.currentFrame > 2) {
                    this.currentFrame = 0;
                }
            }
        } else {
            if (this.framecounter > (300_000_000 + ((100 - this.velocity) * 1_000_000))) {
                this.currentFrame = frameList[Stages.CURRENT_STAGE][index++];
                this.isSubmersed = (this.currentFrame == 5);
                this.framecounter = 0;
                if (index >= frameList[Stages.CURRENT_STAGE].length) {
                    index = 0;
                }
            }
        }

        //draw the selected image
        switch(this.type) {
            case 0:
                this.turtle = this.turtlesSmall[currentFrame];
                this.width  = this.smallWidth;
                break;
            case 1:
                this.turtle = this.turtlesMedium[currentFrame];
                this.width  = this.mediumWidth;
                break;
        }
    }

    /**
     * Return if the turtle is submersed or not
     * @return
     */
    public boolean isSubmersed() {
        return (this.isSubmersed);
    }

    /**
     * Accessor method
     * @param currentFrame
     */
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Accessor method
     * @return
     */
    public int getTurtleFrames() {
        return (this.turtleFrames);
    }

    /**
     * Verify if this sprite is coliding with other
     * @param sprite
     * @return
     */
    @Override
    public boolean isColliding(Sprite sprite) {
        if (!this.isSubmersed) {
            return (calcMyRect().intersects(sprite.calcMyRect()));
        } else {
            return (false);
        }
    }

    /**
     * Create the 3 images for the diferent trunks
     */
    private void createTurtlesImage() {

        for (int i = 0; i < this.turtlesSmall.length - 1; i++) {

            //first turtles type
            this.bgd2 = (Graphics2D)turtlesSmall[i].getGraphics();

            //first part 
            this.bgd2.drawImage(this.turtlesTiles, 0, 0, turtleW, this.height, //dest w1, h1, w2, h2
                                                    (int)(i * turtleW), 0, (int)((i * turtleW) + turtleW), this.height,
                                                    null);

            //second part
            this.bgd2.drawImage(this.turtlesTiles, (separator + turtleW), 0, (separator + turtleW + turtleW), this.height, //dest w1, h1, w2, h2
                                                    (int)(i * turtleW), 0, (int)((i * turtleW) + turtleW), this.height,
                                                    null);

            //second turtles type
            this.bgd2 = (Graphics2D)turtlesMedium[i].getGraphics();

            //first part 
            this.bgd2.drawImage(this.turtlesTiles, 0, 0, turtleW, this.height, //dest w1, h1, w2, h2
                                                    (int)(i * turtleW), 0, (int)((i * turtleW) + turtleW), this.height,
                                                    null);

            //second part
            this.bgd2.drawImage(this.turtlesTiles, (separator + turtleW), 0, (separator + turtleW + turtleW), this.height, //dest w1, h1, w2, h2
                                                    (int)(i * turtleW), 0, (int)((i * turtleW) + turtleW), this.height,
                                                    null);

            //third part
            this.bgd2.drawImage(this.turtlesTiles, (separator + turtleW + separator + turtleW), 0, (separator + turtleW + separator + turtleW + turtleW), this.height, //dest w1, h1, w2, h2
                                                    (int)(i * turtleW), 0, (int)((i * turtleW) + turtleW), this.height,
                                                    null);
        }
    }
}