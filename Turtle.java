import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class representing the turtle sprite
 */
public class Turtle extends SpriteImpl {

    //vehicles tiles
    private BufferedImage turtlesTiles  = null;

    //for the vehicles tiles
    private final byte turtleH          = 36;
    private final byte turtleW          = 43;
    private final int turtleFrames      = 5;
    private int currentFrame            = 0;
    private boolean isSubmersed         = false;
    
    /**
     * Turtle constructor
     */
    public Turtle(Graphics2D g2d) {
        this.g2d    = g2d;
        this.width  = turtleW;
        this.height = turtleH;

        //Get the already loaded image from loader
        this.turtlesTiles   = (BufferedImage)LoadingStuffs.getInstance().getStuff("turtles");
    }
    
    @Override
    public void draw(long frametime) {
        this.g2d.drawImage(this.turtlesTiles, (int)this.positionX, (int)this.positionY, (int)(this.positionX + this.width), (int)(this.positionY + this.height), //dest w1, h1, w2, h2
                                              (int)(currentFrame * turtleW), 0, (int)((currentFrame * turtleW) + turtleW), this.height, //source w1, h1, w2, h2
                                              null);
    }

    @Override
    public void update(long frametime) {}

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
}