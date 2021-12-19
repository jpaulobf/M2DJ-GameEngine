import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import Interfaces.Directions;
import Interfaces.Sprite;

/*
    WTCD: This abstract class store the common characteristics and methods of a sprite
*/
public abstract class SpriteImpl implements Sprite, Directions {

    //variable member values 
    protected byte type                     = 0;
    protected volatile byte direction       = LEFT;
    protected byte velocity                 = 0;
    protected volatile short positionX      = 0;
    protected volatile short positionY      = 0;
    protected volatile double inBetweenX    = 0;
    protected volatile double inBetweenY    = 0;
    protected byte width                    = 0;
    protected byte height                   = 0;
    protected byte offsetTop                = 0;
    protected byte offsetLeft               = 0;
    protected Rectangle2D rectangle         = null;
    protected Graphics2D g2d                = null;

    /**
     * Abstract methods
     */
    public abstract void draw(long frametime);
    public abstract void update(long frametime);

    /**
     * WTMD: Return a rectangle with the current XY position
     */
    public Rectangle2D calcMyRect() {
        this.rectangle = new Rectangle2D.Double(this.positionX, this.positionY, this.width, this.height);
        return (this.rectangle);
    }

    /**
     * Verify if this sprite is coliding with other
     * @param sprite
     * @return
     */
    public boolean isColiding(Sprite sprite) {
        return (calcMyRect().intersects(sprite.calcMyRect()));
    }
}
