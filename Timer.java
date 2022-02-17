import java.awt.Color;
import java.awt.image.BufferedImage;
import util.Audio;

/**
 * Class responsible for game timer
 */
public class Timer {
    private Game gameRef                = null;
    private int wwm                     = 0;
    private int whm                     = 0;
    private volatile long framecounter  = 0;
    private final short maxTimer        = 60;
    private short timer                 = maxTimer;
    private final byte timerBarW        = 5;
    private final byte timerBarH        = 20;
    private BufferedImage timeTile      = null;
    private volatile boolean stopped    = false;
    private volatile Audio lasttime     = null;
    private final short timer3          = (short)(this.timer * 3);

    /**
     * HUD Constructor
     * @param g2d
     * @param wwm
     * @param whm
     * @param HUDHeight
     * @param scenario
     * @param frog
     */
    public Timer(Game game, int wwm, int whm) {
        this.wwm            = wwm;
        this.whm            = whm;
        this.gameRef        = game;
        this.timeTile       = (BufferedImage)LoadingStuffs.getInstance().getStuff("time-tile");
        this.lasttime       = (Audio)LoadingStuffs.getInstance().getStuff("lasttime");
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

                if ((this.timer * 10) == timer3) {
                    this.lasttime.play();
                }
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
        Color ogColor = this.gameRef.getG2D().getColor();

        if ((this.timer * 10) <= timer3) {
            this.gameRef.getG2D().setColor(Color.RED);
        } else {
            this.gameRef.getG2D().setColor(Color.GREEN);
        }

        for (int i = 0; i < (timer * timerBarW); i += timerBarW) {
            this.gameRef.getG2D().fillRect(this.wwm - 100 - i, 
                                          (this.whm + 10), 
                                          timerBarW, 
                                          timerBarH);
        }

        this.gameRef.getG2D().drawImage(this.timeTile, this.wwm - this.timeTile.getWidth() - 6, this.whm + 10, this.wwm - 6, (this.whm + this.timeTile.getHeight() + 10),   //dest w1, h1, w2, h2
                                                       0, 0, this.timeTile.getWidth(), this.timeTile.getHeight(),  //source w1, h1, w2, h2
                                                       null);

        //return original color
        this.gameRef.getG2D().setColor(ogColor);
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
