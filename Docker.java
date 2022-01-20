import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Docker sprite class
 */
public class Docker extends SpriteImpl {

    //vehicles tiles
    private BufferedImage docker = null;

    /**
     * Docker constructor
     */
    public Docker(Graphics2D g2d) {
        this.g2d    = g2d;
        this.docker = (BufferedImage)LoadingStuffs.getInstance().getStuff("pixel");
    }

    /**
     * Docker configuration method
     * @param positionX
     * @param positionY
     * @param width
     * @param height
     */
    public void config(double positionX, double positionY, short width, byte height) {
        this.positionX  = positionX;
        this.positionY  = positionY;
        this.width      = width;
        this.height     = height;
    }

    @Override
    public void draw(long frametime) {
        this.g2d.drawImage(this.docker, (int)this.positionX, (int)this.positionY, (int)(this.positionX + this.width), (int)(this.positionY + this.height), //dest w1, h1, w2, h2
                                         0, 0, 1, 1, //source w1, h1, w2, h2
                                         null);
        
    }

    @Override
    public void update(long frametime) {}
}