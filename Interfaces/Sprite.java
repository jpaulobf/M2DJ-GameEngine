package interfaces;

import java.awt.geom.Rectangle2D;

public interface Sprite {
    public Rectangle2D calcMyRect();
    public void draw(long frametime);
    public void update(long frametime);
    public boolean isColiding(Sprite sprite);
}
