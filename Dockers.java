import interfaces.Sprite;
import interfaces.SpriteCollection;
import java.awt.Graphics2D;

/**
 * Collection of Docker class
 */
public class Dockers extends SpriteCollection {

    private Docker[] dockers    = new Docker[5];
    protected Graphics2D g2d    = null;

    /**
     * Dockers constructor
     */
    public Dockers(Graphics2D g2d) {

        double [] positionX = {72, 348, 624, 900, 1176};
        double [] positionY = {14, 14, 14, 14, 14};
        short  [] width     = {96, 96, 96, 96, 96};
        byte   [] height    = {50, 50, 50, 50, 50};
        this.g2d            = g2d;

        for (int i = 0; i < dockers.length; i++) {
            dockers[i] = new Docker(this.g2d);
            dockers[i].config(positionX[i], positionY[i], width[i], height[i]);
        }
    }

    @Override
    protected Sprite[] getSpriteCollection() {
         return (this.dockers);
    }

    @Override
    public void update(long frametime) {
    }

    @Override
    public void draw(long frametime) {
        for (byte i = 0; i < dockers.length; i++) {
            this.dockers[i].draw(frametime);
        }
    }
}