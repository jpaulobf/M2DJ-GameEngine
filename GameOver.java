import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.VolatileImage;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

/*
    WTCD: This class represents the gameover scene
*/
public class GameOver {
    //Scenario variables
    private Graphics2D g2d              = null;
    private Graphics2D bgd2             = null;
    private int windowWidth             = 0;
    private int windowHeight            = 0;
    private VolatileImage bgBufferImage = null;
    private BufferedImage gameover      = null;

    /**
     * Constructor
     * @param g2d
     * @param windowWidth
     * @param windowHeight
     */
    public GameOver(Graphics2D g2d, int windowWidth, int windowHeight) {
        this.g2d            = g2d;
        this.windowHeight   = windowHeight;
        this.windowWidth    = windowWidth;
        this.drawGameOverInBuffer();
    }

    /**
     * This private method construct the BG just once.
     * Than, when necessary it is ploted in the backbuffer.
     */
    private void drawGameOverInBuffer() {
        if (this.bgd2 == null) {

            try {
                this.gameover = ImageIO.read(new File("images\\gameover.png"));
            } catch (java.io.IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            //create a backbuffer image for doublebuffer
            this.bgBufferImage  = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleVolatileImage(this.windowWidth, this.windowHeight);
            this.bgd2           = (Graphics2D)bgBufferImage.getGraphics();

            //paint all bg in black
            this.bgd2.setBackground(Color.BLACK);
            this.bgd2.clearRect(0, 0, this.windowWidth, this.windowHeight);
            
            int imgW = this.gameover.getWidth();
            int imgH = this.gameover.getHeight();
            int imgX = ((this.windowWidth - imgW)/2);
            int imgY = ((this.windowHeight - imgH)/2);

            this.bgd2.drawImage(this.gameover, imgX, imgY, imgW + imgX, imgH + imgY, 
                                               0, 0, imgW, imgH, null);
        }
    }

    /**
     * Update the gameover scene and its elements
     * @param frametime
     */
    public void update(long frametime) {

    }

    /**
     * 
     * @param frametime
     */
    public void draw(long frametime) {
        //After construct the bg once, copy it to the graphic device
        this.g2d.drawImage(this.bgBufferImage, 0, 0, null);
    }
}
