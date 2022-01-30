import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.image.VolatileImage;
import java.awt.image.BufferedImage;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;

public class HUD {

    private Graphics2D g2d              = null;
    private Scenario scenarioRef        = null;
    private Frog frogRef                = null;
    private VolatileImage bgBufferImage = null;
    private int windowWidth             = 0;
    private int windowHeight            = 0;
    private int lives                   = 0;
    
    public HUD(Graphics2D g2d, int HUDHeight, Scenario scenario, Frog frog) {
        this.g2d            = g2d;
        this.scenarioRef    = scenario;
        this.frogRef        = frog;
        this.windowWidth    = scenarioRef.getWindowWidth();
        this.windowHeight   = scenarioRef.getWindowHeight();
        this.lives          = this.frogRef.getLives();
        this.bgBufferImage  = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleVolatileImage(this.windowWidth, this.windowHeight);
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
        //After construct the bg once, copy it to the graphic device
        this.g2d.drawImage(this.bgBufferImage, 0, 0, null);
    }

    /**
     * 
     */
    public void reset() {

    }
}
