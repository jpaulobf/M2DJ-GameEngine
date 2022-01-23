import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import interfaces.Directions;
import interfaces.Sprite;

/*
    WTCD: This abstract class store the common characteristics and methods of a sprite
*/
public abstract class SpriteImpl implements Sprite, Directions {

    //variable member values 
    protected volatile byte type                = 0;
    protected volatile byte direction           = LEFT;
    protected volatile byte velocity            = 0;
    protected volatile double positionX         = 0;
    protected volatile double positionY         = 0;
    protected volatile double destPositionX     = 0;
    protected volatile double destPositionY     = 0;
    protected volatile double calculatedStep    = 0D;
    protected volatile short width              = 0;
    protected volatile byte height              = 0;
    protected volatile byte offsetTop           = 0;
    protected volatile byte offsetLeft          = 0;
    protected Rectangle2D rectangle             = null;
    protected Graphics2D g2d                    = null;
    
    /**
     * Accessor Method
     * @return
     */
    public short getWidth() {
        return (this.width);
    }

    /**
     * Accessor Method
     */
    public double getCalculatedStep() {
        return (this.calculatedStep);
    }
    
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
    public boolean isColliding(Sprite sprite) {
        return (calcMyRect().intersects(sprite.calcMyRect()));
    }
}
