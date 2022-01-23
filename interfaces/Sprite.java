package interfaces;

import java.awt.geom.Rectangle2D;

/**
 * Sprite Interface - defined the draw, update & colliding methods.
 */
public interface Sprite {
    public Rectangle2D calcMyRect();
    public void draw(long frametime);
    public void update(long frametime);
    public boolean isColliding(Sprite sprite);
    public double getCalculatedStep();
}
