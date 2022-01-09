import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Tree trunk (individual) class
 */
public class TreeTrunk  extends SpriteImpl {

    //define the g2d and the trunks-images
    private BufferedImage trunksTiles   = null;

    /**
     * constructor
     * @param g2d
     */
    public TreeTrunk(Graphics2D g2d) {
        this.g2d = g2d;
        //Get the already loaded image from loader
        this.trunksTiles   = (BufferedImage)LoadingStuffs.getInstance().getStuff("trunksTiles");
    }

    @Override
    public void draw(long frametime) {
        //draw the selected image
        direction = (direction == LEFT)?0:direction;
        this.g2d.drawImage(this.trunksTiles, this.positionX, this.positionY, this.positionX + 0, this.positionY + this.height, //dest w1, h1, w2, h2
                                             0, 0, 0, this.height, //source w1, h1, w2, h2
                                             null);
    }

    @Override
    public void update(long frametime) {}
}
