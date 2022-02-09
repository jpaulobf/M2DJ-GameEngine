import java.awt.Graphics2D;
import java.awt.image.VolatileImage;
import java.awt.image.BufferedImage;
import java.awt.GraphicsEnvironment;
import java.awt.Color;

public class HUD {

    private Graphics2D g2d              = null;
    private Frog frogRef                = null;
    private VolatileImage hudBG         = null;
    private BufferedImage livesTile     = null;
    private Graphics2D bg2d             = null;
    private byte lives                  = 0;
    private int wwm                     = 0;
    private int whm                     = 0;
    private byte HUDHeight              = 0;
    private final byte separator        = 8;
    private final byte initialDistance  = 10;
    private final byte initialHeight    = 8;
    
    /**
     * HUD Constructor
     * @param g2d
     * @param wwm
     * @param whm
     * @param HUDHeight
     * @param scenario
     * @param frog
     */
    public HUD(Graphics2D g2d, int wwm, int whm, byte HUDHeight, Game game) {
        this.HUDHeight      = HUDHeight;
        this.wwm            = wwm;
        this.whm            = whm;
        this.g2d            = g2d;
        this.frogRef        = game.getFrog();
        this.lives          = this.frogRef.getLives();
        this.hudBG          = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleVolatileImage(wwm, HUDHeight);
        this.bg2d           = (Graphics2D)this.hudBG.getGraphics();
        this.livesTile      = (BufferedImage)LoadingStuffs.getInstance().getStuff("live");
    }

    /**
     * Update the scenario and its elements
     * @param frametime
     */
    public void update(long frametime) {
        this.lives = this.frogRef.getLives();
    }

    /**
     * 
     * @param frametime
     */
    public void draw(long frametime) {

        //clear the backbuffer
        this.bg2d.setBackground(Color.BLACK);
        this.bg2d.clearRect(0, 0, wwm, HUDHeight);

        //render the HUD
        for (int i = 0; i < this.lives; i++) {
            this.bg2d.drawImage(this.livesTile, initialDistance + ((i * separator) + (i * this.livesTile.getWidth())), 
                                                initialHeight, 
                                                initialDistance + ((i * separator) + (i * this.livesTile.getWidth()) + this.livesTile.getWidth()), 
                                                initialHeight + this.livesTile.getHeight(), //dest w1, h1, w2, h2
                                                0, 0, this.livesTile.getWidth(), this.livesTile.getHeight(),  //source w1, h1, w2, h2
                                                null);
        }

        //After HUD rendered, copy to G2D
        this.g2d.drawImage(this.hudBG, 0, this.whm, this.wwm, (this.whm + this.HUDHeight),   //dest w1, h1, w2, h2
                                       0, 0, this.hudBG.getWidth(), this.hudBG.getHeight(),  //source w1, h1, w2, h2
                                       null);
    }

    /**
     * 
     */
    public void reset() {
    }
}