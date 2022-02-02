import java.awt.Graphics2D;
import java.awt.image.VolatileImage;
import java.awt.image.BufferedImage;
import java.awt.GraphicsEnvironment;
import java.awt.Color;

public class HUD {

    private Graphics2D g2d              = null;
    private Scenario scenarioRef        = null;
    private Frog frogRef                = null;
    private VolatileImage hudBG         = null;
    private BufferedImage livesTile     = null;
    private Graphics2D bg2d             = null;
    private short windowWidth           = 0;
    private short windowHeight          = 0;
    private byte lives                  = 0;
    private byte separator              = 8;
    private byte initialDistance        = 10;
    private byte HUDHeight              = 0;
    
    public HUD(Graphics2D g2d, byte HUDHeight, Scenario scenario, Frog frog) {
        this.g2d            = g2d;
        this.scenarioRef    = scenario;
        this.frogRef        = frog;
        this.windowWidth    = (short)scenarioRef.getWindowWidth();
        this.windowHeight   = (short)scenarioRef.getWindowHeight();
        this.HUDHeight      = HUDHeight;
        this.lives          = this.frogRef.getLives();
        this.hudBG          = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleVolatileImage(this.windowWidth, HUDHeight);
        this.livesTile      = (BufferedImage)LoadingStuffs.getInstance().getStuff("live");
        this.bg2d           = (Graphics2D)this.hudBG.getGraphics();
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

        this.bg2d.setBackground(Color.BLACK);
        this.bg2d.clearRect(0, 0, this.windowWidth, this.windowHeight);

        for (int i = 0; i < this.lives; i++) {
            this.bg2d.drawImage(this.livesTile, initialDistance + ((i * separator) + i * this.livesTile.getWidth()), 0, initialDistance + ((i * this.livesTile.getWidth()) + this.livesTile.getWidth() + (i * separator)), this.livesTile.getHeight(), //dest w1, h1, w2, h2
                                                 0, 0, this.livesTile.getWidth(), this.livesTile.getHeight(),  //source w1, h1, w2, h2
                                                 null);
        }
        //After construct the bg once, copy it to the graphic device
        this.g2d.drawImage(this.hudBG, 0, 0, this.windowWidth, this.HUDHeight,  //dest w1, h1, w2, h2
                                       0, 0, this.hudBG.getWidth(), this.hudBG.getHeight(),           //source w1, h1, w2, h2
                                       null);
    }

    /**
     * 
     */
    public void reset() {

    }
}
