import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class to show messages on screen
 */
@SuppressWarnings("unused")
public class Message {
    private Graphics2D g2d              = null;
    private BufferedImage messageTile   = null;
    private Game gameReference          = null;
    private int wwm                     = 0;
    private int whm                     = 0;
    private volatile boolean showing    = false;

    /**
     * HUD Constructor
     * @param g2d
     * @param wwm
     * @param whm
     * @param HUDHeight
     * @param scenario
     * @param frog
     */
    public Message(Graphics2D g2d, int wwm, int whm, Game game) {
        this.wwm            = wwm;
        this.whm            = whm;
        this.g2d            = g2d;
        this.gameReference  = game;
        this.messageTile    = (BufferedImage)LoadingStuffs.getInstance().getStuff("stage-clear");
    }

    /**
     * 
     * @param frametime
     */
    public void draw(long frametime) {
        if (this.showing) {
            this.g2d.drawImage(this.messageTile, (int)((wwm - this.messageTile.getWidth()) / 2), (int)((whm - this.messageTile.getHeight()) / 2) - 20,   
                                                 (int)((wwm - this.messageTile.getWidth()) / 2) + this.messageTile.getWidth(), (int)((whm - this.messageTile.getHeight()) / 2) + this.messageTile.getHeight() - 20, //dest w1, h1, w2, h2
                                                 0, 0, this.messageTile.getWidth(), this.messageTile.getHeight(),  //source w1, h1, w2, h2
                                                 null);
        }
    }

    /**
     * Show/hide message image
     */
    public void tooglePause() {
        this.showing = !this.showing;
    }
}
