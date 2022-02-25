import java.awt.Graphics2D;
import java.awt.image.VolatileImage;
import java.awt.image.BufferedImage;
import java.awt.GraphicsEnvironment;
import java.awt.Color;

/**
 * Class responsible for score control.
 */
public class Score {
    
    private Game gameRef                = null;
    private VolatileImage scoreBG       = null;
    private BufferedImage oneupTile     = null;
    private BufferedImage hiscoreTile   = null;
    private BufferedImage [] numbers    = null;
    private Graphics2D bg2d             = null;
    private int score                   = 0;
    private int hiscore                 = 0;
    private int wwm                     = 0;
    private int whm                     = 0;
    private byte scoreHeight            = 0;
    private final byte separator        = 8;
    private final byte initialDistance  = 10;
    private final byte initialHeight    = 8;


    /**
     * Score constructor
     * @param game
     * @param wwm
     * @param whm
     * @param scoreHeight
     */
    public Score(Game game, int wwm, int whm, byte scoreHeight) {
        this.scoreHeight      = scoreHeight;
        this.wwm            = wwm;
        this.whm            = whm;
        this.gameRef        = game;
        this.score          = 0;
        this.hiscore        = 0;
        this.scoreBG        = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleVolatileImage(wwm, scoreHeight);
        this.bg2d           = (Graphics2D)this.scoreBG.getGraphics();
        this.oneupTile      = (BufferedImage)LoadingStuffs.getInstance().getStuff("oneupTile");
        this.hiscoreTile    = (BufferedImage)LoadingStuffs.getInstance().getStuff("hiscoreTile");
        this.numbers        = new BufferedImage[10];
        this.numbers[0]     = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-0");
        this.numbers[1]     = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-1");
        this.numbers[2]     = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-2");
        this.numbers[3]     = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-3");
        this.numbers[4]     = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-4");
        this.numbers[5]     = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-5");
        this.numbers[6]     = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-6");
        this.numbers[7]     = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-7");
        this.numbers[8]     = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-8");
        this.numbers[9]     = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-9");
    }

    /**
     * Update the score and its elements
     * @param frametime
     */
    public void update(long frametime) {
        //todo
    }

    /**
     * Draw the score
     * @param frametime
     */
    public void draw(long frametime) {
        //clear the backbuffer
        this.bg2d.setBackground(Color.BLACK);
        this.bg2d.clearRect(0, 0, wwm, scoreHeight);

        //After HUD rendered, copy to G2D
        this.gameRef.getG2D().drawImage(this.scoreBG, 0, 0, this.whm, this.scoreHeight,   //dest w1, h1, w2, h2
                                                      0, 0, this.scoreBG.getWidth(), this.scoreBG.getHeight(),  //source w1, h1, w2, h2
                                                      null);
    }

    /**
     * 
     */
    public void reset() {
    }
}