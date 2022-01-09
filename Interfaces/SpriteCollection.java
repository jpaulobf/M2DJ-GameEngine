package interfaces;

public abstract class SpriteCollection {

    protected abstract Sprite[] getSpriteCollection();

    public int testColision(Sprite sprite) {
        //gate check
        if (sprite == null) return -1;

        Sprite[] sprites = this.getSpriteCollection();
        
        //important: it's not necessary to test if the vehicle array is null, because it was initialized in the constructor
        for (int cnt = 0; cnt < sprites.length; cnt++) {
            if (sprites[cnt].isColiding(sprite)) {
                return (cnt);
            }
        }

        //if the code reach here, no colision
        return (-1);
    }

    public abstract void update(long frametime);

    public abstract void draw(long frametime);

}