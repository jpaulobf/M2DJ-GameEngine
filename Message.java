import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import util.Audio;
import java.awt.Color;

/**
 * Class to show messages on screen
 */
@SuppressWarnings("unused")
public class Message {
    private BufferedImage stageTile                 = null;
    private BufferedImage goTile                    = null;
    private BufferedImage messageTile               = null;
    private BufferedImage temp                      = null;
    private Game gameReference                      = null;
    private int wwm                                 = 0;
    private int whm                                 = 0;
    private volatile boolean showing                = false;
    private volatile boolean showStage              = false;
    private volatile long framecounter              = 0;
    private volatile long animationDuration         = 3_500_000_000L;
    private volatile long quarterAnimationDuration  = animationDuration / 4;
    private volatile long thirthAnimationDuration   = quarterAnimationDuration * 3;
    private volatile Audio beepstart                = null;
    private volatile int w1                         = 0;
    private volatile int h1                         = 0;
    private volatile int w2                         = 0;
    private volatile int h2                         = 0;
    private volatile double startOgW1               = 0D;
    private volatile int ogW1                       = 0;
    private volatile int ogW2                       = 0;
    private volatile int ogH1                       = 0;
    private volatile int ogH2                       = 0;
    private volatile double diff                    = 0;
    private volatile double steps                   = 0D;

    /**
     * HUD Constructor
     * @param g2d
     * @param wwm
     * @param whm
     * @param HUDHeight
     * @param scenario
     * @param frog
     */
    public Message(Game game, int wwm, int whm) {
        this.wwm            = wwm;
        this.whm            = whm;
        this.gameReference  = game;
        this.messageTile    = (BufferedImage)LoadingStuffs.getInstance().getStuff("stage-clear");
        this.stageTile      = (BufferedImage)LoadingStuffs.getInstance().getStuff("stage-1");
        this.goTile         = (BufferedImage)LoadingStuffs.getInstance().getStuff("go!");
        this.beepstart      = (Audio)LoadingStuffs.getInstance().getStuff("beepstart");
    }

    /**
     * Update the scenario and its elements
     * @param frametime
     */
    public void update(long frametime) {
        if (this.showStage) {
            this.framecounter += frametime;
            if (this.framecounter > thirthAnimationDuration) {
                this.temp = this.goTile;
                //runs once
                if (this.framecounter > thirthAnimationDuration && this.framecounter <= (thirthAnimationDuration + frametime)) {
                    this.calculateOriginalPositions(1);
                }
            } else {
                this.temp = this.stageTile;
                //runs once
                if (this.framecounter == frametime) {
                    this.calculateOriginalPositions(3);
                    this.beepstart.play();
                }
            }

            //calculate the animation steps
            this.steps = diff * frametime;
            this.startOgW1 += this.steps;
            this.w1 = (int)this.startOgW1;
            this.w2 = this.w1 + this.temp.getWidth();
            this.h1 = ogH1;
            this.h2 = ogH2;
        } else {
            this.temp = messageTile;
            this.w1 = (int)((wwm - this.temp.getWidth()) / 2);
            this.w2 = this.w1 + this.temp.getWidth();
            this.h1 = (int)((whm - this.temp.getHeight()) / 2) - 20;
            this.h2 = this.ogH1 + this.temp.getHeight();
        }
    }

    /**
     * Recalculate
     */
    private void calculateOriginalPositions(int times) {
        this.startOgW1  = -this.temp.getWidth();
        this.ogW1       = (int)((wwm + this.temp.getWidth()));
        this.ogW2       = this.ogW1 + this.temp.getWidth();
        this.ogH1       = (int)((whm - this.temp.getHeight()) / 2) - 20;
        this.ogH2       = this.ogH1 + this.temp.getHeight();
        if (times > 0) {
            this.diff = (ogW1 - startOgW1) / (quarterAnimationDuration * times);
        }
    }

    /**
     * 
     * @param frametime
     */
    public void draw(long frametime) {
        if (this.showing) {
            //draw
            this.gameReference.getG2D().drawImage(this.temp, w1, h1, w2, h2, //dest w1, h1, w2, h2
                                                             0, 0, this.temp.getWidth(), this.temp.getHeight(),  //source w1, h1, w2, h2
                                                             null);
        }
    }

    /**
     * Verify if the message has finished
     * @return
     */
    public boolean finished() {
        if (this.framecounter > animationDuration) {
            this.framecounter = 0;
            return true;
        }
        return false;
    }

    /**
     * Show/hide message image
     */
    public void toogleShowing() {
        this.showing = !this.showing;
    }

    /**
     * Show message image
     */
    public void showing(boolean showing) {
        this.showing = showing;
    }

    /**
     * Show stage message
     */
    public void toogleStageAnnouncement() {
        this.showStage = !this.showStage;
    }

    /**
     * Show stage message
     */
    public void showStageAnnouncement() {
        this.showing    = true;
        this.showStage  = true;
    }

    public void showStates() {
        System.out.println(this.showing);
        System.out.println(this.showStage);
    }
}