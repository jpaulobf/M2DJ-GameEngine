package interfaces;

import java.awt.Graphics2D;

/**
 * Sprite collection abstract class
 */
public abstract class SpriteCollection implements Directions {

    /**
     * Method to test colision of an sprite with the entire collection
     * @param sprite
     * @return
     */
    public int testCollision(Sprite sprite) {
        //gate check
        if (sprite == null) return -1;
        if (this.getSpriteCollection() != null) {
            for (int cnt = 0; cnt < this.getSpriteCollection().length; cnt++) {
                if (this.getSpriteCollection()[cnt].isColliding(sprite)) {
                    return (cnt);
                }
            }
        }

        //if the code reach here, no colision
        return (-1);
    }

    /**
     * Get the last element calculated step
     * @param element
     * @return
     */
    public double getCalculatedStep(int element) {
        return (getSpriteCollection()[element].getCalculatedStep());
    }

    /**
     * Abstract method to recover the sprite collection
     * @return
     */
    protected abstract Sprite[] getSpriteCollection();

    /**
     * Abstract update method
     * @param frametime
     */
    public abstract void update(long frametime);

    /**
     * Abstract draw method
     * @param frametime
     */
    public abstract void draw(long frametime);

    /**
     * Return the G2D
     * @return
     */
    public abstract Graphics2D getG2D();
}