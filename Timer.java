import java.awt.Graphics2D;
import java.awt.Color;
import interfaces.IGame;
import java.awt.image.BufferedImage;

public class Timer {
    private Graphics2D g2d              = null;
    private IGame gameRef               = null;
    private int wwm                     = 0;
    private int whm                     = 0;
    private volatile long framecounter  = 0;
    private final short maxTimer        = 60;
    private short timer                 = maxTimer;
    private final byte timerBarW        = 5;
    private final byte timerBarH        = 20;
    private BufferedImage timeTile      = null;
    private volatile boolean stopped    = false;

    /**
     * HUD Constructor
     * @param g2d
     * @param wwm
     * @param whm
     * @param HUDHeight
     * @param scenario
     * @param frog
     */
    public Timer(Graphics2D g2d, int wwm, int whm, IGame game) {
        this.wwm            = wwm;
        this.whm            = whm;
        this.g2d            = g2d;
        this.gameRef        = game;
        this.timeTile       = (BufferedImage)LoadingStuffs.getInstance().getStuff("time-tile");
    }

    /**
     * Update the scenario and its elements
     * @param frametime
     */
    public void update(long frametime) {
        if (!this.stopped) {
            this.framecounter += frametime;
            if (this.framecounter >= 500_000_000L) {
                this.framecounter = 0;
                this.timer = (this.timer <= 0)?0:--this.timer;
            }
        }
    }

    /**
     * Verify if the time is over
     * @return
     */
    public boolean getTimeOver() {
        return (this.timer == 0);
    }

    /**
     * 
     * @param frametime
     */
    public void draw(long frametime) {
        
        //save the original color and set new color to green
        Color ogColor = this.g2d.getColor();
        this.g2d.setColor(Color.GREEN);

        for (int i = 0; i < (timer * timerBarW); i += timerBarW) {
            this.g2d.fillRect(this.wwm - 100 - i, 
                              (this.whm + 10), 
                              timerBarW, 
                              timerBarH);
        }

        this.g2d.drawImage(this.timeTile, this.wwm - this.timeTile.getWidth() - 6, this.whm + 10, this.wwm - 6, (this.whm + this.timeTile.getHeight() + 10),   //dest w1, h1, w2, h2
                                          0, 0, this.timeTile.getWidth(), this.timeTile.getHeight(),  //source w1, h1, w2, h2
                                          null);

        //return original color
        this.g2d.setColor(ogColor);
    }

    /**
     * Pause the timer
     */
    public void tooglePause() {
        this.stopped = !this.stopped;
    }    

    /**
     * 
     */
    public void reset() {
        this.framecounter = 0;
        this.timer = maxTimer;
    }
}
