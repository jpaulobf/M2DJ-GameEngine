import java.awt.Graphics2D;
import java.awt.image.VolatileImage;
import java.awt.image.BufferedImage;
import java.awt.GraphicsEnvironment;
import java.awt.Color;
import java.io.File;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
    private volatile int score          = 0;
    private volatile int hiscore        = 0;
    private volatile String sHiscore    = null;
    private volatile String sDate       = "";
    private Date dateHiscore            = null;
    private int wwm                     = 0;
    private int wwmdiv4                 = 0;
    private int wwmdiv4time3            = 0;
    private int halfOneUpSize           = 0;
    private int halfHiscoreSize         = 0;
    private int whm                     = 0;
    private byte scoreHeight            = 0;
    public static final byte ROAD       = 0;
    public static final byte RIVER      = 1;
    public static final byte DOCKER     = 2;
    public static final byte FULLDOCKER = 3;
    public static final byte MOSQUITO   = 4;
    private int skipPoint               = 0;

    /**
     * Score constructor
     * @param game
     * @param wwm
     * @param whm
     * @param scoreHeight
     */
    public Score(Game game, int wwm, int whm, byte scoreHeight) {
        this.scoreHeight        = scoreHeight;
        this.wwm                = wwm;
        this.whm                = whm;
        this.wwmdiv4            = (int)(this.wwm / 4);
        this.wwmdiv4time3       = this.wwmdiv4 * 3;
        this.gameRef            = game;
        this.score              = 0;
        this.hiscore            = 0;
        this.numbers            = new BufferedImage[10];
        this.scoreBG            = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleVolatileImage(wwm, scoreHeight);
        this.bg2d               = (Graphics2D)this.scoreBG.getGraphics();
        this.oneupTile          = (BufferedImage)LoadingStuffs.getInstance().getStuff("oneupTile");
        this.hiscoreTile        = (BufferedImage)LoadingStuffs.getInstance().getStuff("hiscoreTile");
        this.numbers[0]         = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-0");
        this.numbers[1]         = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-1");
        this.numbers[2]         = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-2");
        this.numbers[3]         = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-3");
        this.numbers[4]         = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-4");
        this.numbers[5]         = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-5");
        this.numbers[6]         = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-6");
        this.numbers[7]         = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-7");
        this.numbers[8]         = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-8");
        this.numbers[9]         = (BufferedImage)LoadingStuffs.getInstance().getStuff("number-9");
        this.halfOneUpSize      = (this.oneupTile.getWidth() / 2);
        this.halfHiscoreSize    = (this.hiscoreTile.getWidth() / 2);

        //load the file containing the hi score
        this.loadHighScore();
    }

    /**
     * 
     */
    public void skipPoint() {
        this.skipPoint++;
    }

    public void resetSkipPoint() {
        this.skipPoint = 0;
    }

    private void drawScoreBG() {
        //clear the backbuffer
        this.bg2d.setBackground(new Color(0, 0, 100));
        this.bg2d.clearRect(0, 0, this.wwm, scoreHeight);
        
        //draw the oneup tile
        this.bg2d.drawImage(this.oneupTile,     wwmdiv4 - halfOneUpSize, 10, wwmdiv4 - halfOneUpSize + this.oneupTile.getWidth(), 10 + this.oneupTile.getHeight(),
                                                0, 0, this.oneupTile.getWidth(), this.oneupTile.getHeight(),
                                                null);

        //draw the hiscore tile
        this.bg2d.drawImage(this.hiscoreTile,   wwmdiv4time3 - halfOneUpSize - halfHiscoreSize, 10, wwmdiv4time3 - halfOneUpSize - halfHiscoreSize + this.hiscoreTile.getWidth(), 10 + this.hiscoreTile.getHeight(),
                                                0, 0, this.hiscoreTile.getWidth(), this.hiscoreTile.getHeight(),
                                                null);

        //convert the score into image
        //convert hiscore into image
    }

    private synchronized void loadHighScore() {
        //1 - try load hiscore.p file
        //2 - if file not exist, do nothing!
        //3 - else load current hiscore date (yyyy-mm-dd) & score
        //4 - set to the variables
        File hiscorep = new File("hiscore.p");
        if (hiscorep.exists()) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(hiscorep);
                String line, value = "";
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    line.trim();
                    if (line.length() > 0 && line.charAt(0) != '#') { //ignore comments
                        value = line.split(":")[1].trim();
                        if (value != null && value.length() > 0) {
                            sDate    = value.split(",")[0].trim();
                            sHiscore = value.split(",")[1].trim();
                        }
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        this.hiscore = Integer.parseInt(this.sHiscore);
                        this.dateHiscore = formatter.parse(sDate);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error opening 'hiscore.p' file!");
            } finally {
                if (scanner != null) {
                    scanner.close();
                }
            }
        }   
    }

    /**
     * Store new Highscore
     */
    private synchronized void storeNewHighScore() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.sHiscore = String.valueOf(this.hiscore);
        this.sDate = formatter.format(new Date());
        File hiscorep = new File("hiscore.p");

        //if file exists, delete it
        if (hiscorep.exists()) {
            hiscorep.delete();
        }

        //than, create a clean new file
        try {
            hiscorep.createNewFile();    
        } catch (Exception e) {
            System.err.println("Impossible to create 'hiscore.p' file! " + "\n" + e.getMessage());
        }

        //if everything is ok, store the high score
        if (hiscorep.canWrite()) {
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(hiscorep, true));
                writer.append("hiscore:");
                writer.append(sDate);
                writer.append(",");
                writer.append(sHiscore);
                writer.append("\n");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Save the Higher Score
     */
    public void saveHiScore() {
        this.storeNewHighScore();
    }

    public void addRoadStepScore() {
        this.score += 10;
    }

    public void addRiverStepScore() {
        this.score += 20;
    }

    public void addDockerScore() {
        this.score += 50;
    }

    public void addAllDockersScore() {
        this.score += 100;
    }

    public void addMosquitoScore() {
        this.score += 200;
    }

    public void addScore(byte type) {
        if (this.skipPoint > 0) {
            this.skipPoint--;
        } else {
            System.out.println(type);
        }
    }

    /**
     * Update the score
     * @param frametime
     */
    public void update(long frametime) {
        if (this.score > this.hiscore) {
            this.hiscore = this.score;
        }
    }

    /**
     * Draw the score
     * @param frametime
     */
    public void draw(long frametime) {

        this.drawScoreBG();

        //After HUD rendered, copy to G2D
        this.gameRef.getG2D().drawImage(this.scoreBG, 0, 0, this.wwm, this.scoreHeight,   //dest w1, h1, w2, h2
                                                      0, 0, this.scoreBG.getWidth(), this.scoreBG.getHeight(),  //source w1, h1, w2, h2
                                                      null);
    }

    /**
     * Reset method
     */
    public void reset() {
        this.score = 0;
        if (this.sHiscore != null && !"".equals(this.sHiscore)) {
            this.hiscore = Integer.parseInt(this.sHiscore);
        } else {
            this.hiscore = 0;
        }
        this.skipPoint = 0;
    }
}