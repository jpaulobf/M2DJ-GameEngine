package interfaces;

public abstract class SpriteCollection {
    /**
     * Method to test colision of an sprite with the entire collection
     * @param sprite
     * @return
     */
    public int testColision(Sprite sprite) {
        //gate check
        if (sprite == null) return -1;
        if (this.getSpriteCollection() != null) {
            for (int cnt = 0; cnt < this.getSpriteCollection().length; cnt++) {
                if (this.getSpriteCollection()[cnt].isColiding(sprite)) {
                    return (cnt);
                }
            }
        }

        //if the code reach here, no colision
        return (-1);
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
}