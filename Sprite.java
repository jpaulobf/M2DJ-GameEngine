import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/*
    WTCD: This abstract class store the common characteristics and methods of a sprite
*/
public abstract class Sprite {

    //constants
    protected final byte LEFT           = -1;
    protected final byte RIGHT          = 1;
    protected final byte UP             = 2;
    protected final byte DOWN           = 3;

    //variable member values 
    protected byte type                 = 0;
    protected byte direction            = LEFT;
    protected byte velocity             = 0;
    protected byte positionX            = 0;
    protected byte positionY            = 0;
    protected short pixelPosX           = 0;
    protected short pixelPosY           = 0;
    protected short inBetweenX          = 0;
    protected short inBetweenY          = 0;
    protected byte width                = 0;
    protected byte height               = 0;
    protected byte offsetTop            = 0;
    protected byte offsetLeft           = 0;
    protected Rectangle2D rectangle     = null;
    protected Graphics2D g2d            = null;

    /**
     * WTMD: Return a rectangle with the current XY position
     */
    private Rectangle2D calcMyRect() {
        this.rectangle = new Rectangle2D.Double(this.positionX, this.positionY, this.width, this.height);
        return (this.rectangle);
    }

    public abstract void draw(long frametime);
    public abstract void update(long frametime);
    public boolean isColiding(Sprite sprite) {
        return (calcMyRect().intersects(sprite.calcMyRect()));
    }
}
