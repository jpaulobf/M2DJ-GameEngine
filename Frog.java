import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

/*
    WTCD: This class represents the frog sprite
*/
public class Frog extends Sprite {
    
    private byte lives                  = 3;
    private byte tileX                  = 0;
    private byte tileY                  = 0;
    private boolean canMove             = true;
    private final byte INITIAL_POS_X    = 10;
    private final byte INITIAL_POS_Y    = 12;
    private BufferedImage animalTiles   = null;

    /**
     * Frog constructor
     * @param g2d
     * @param scenario
     */
    public Frog(Graphics2D g2d, Scenario scenario) {

        this.g2d        = g2d;
        this.height     = 32;
        this.width      = 32;

        this.tileX      = scenario.getTileX();
        this.tileY      = scenario.getTileY();

        this.offsetLeft = (byte)((this.tileX - this.width) / 2);
        this.offsetTop  = (byte)((this.tileY - this.height) / 2);

        this.positionX = INITIAL_POS_X;
        this.positionY = INITIAL_POS_Y;

        this.direction = UP;

        try {
            this.animalTiles = ImageIO.read(new File("images\\animals.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    
    public byte getLives() {
        return lives;
    }

    /**
     * 
     * @param keycode
     */
    public void move(int keycode) {
        if (this.canMove) {
            if (keycode == 39) {
                if (positionX < 20) {
                    this.positionX++;
                    this.direction = RIGHT;
                }
            } else if (keycode == 37) {
                if (positionX > 0) {
                    this.positionX--;
                    this.direction = LEFT;
                }
            } else if (keycode == 38) {
                if (positionY > 0) {
                    this.positionY--;
                    this.direction = UP;
                }
            } else if (keycode == 40) {
                if (positionY < 12) {
                    this.positionY++;
                    this.direction = DOWN;
                }
            }
        }
    }

    @Override
    public void draw(long frametime) {
    
        short imgX = 0;
        short imgY = 0;
        byte imgW = 32;
        byte imgH = 25;

        switch (this.direction) {
            case UP:
                imgX = 132;
                imgY = 4;
                break;
            case DOWN:
                imgX = 172;
                imgY = 4;
                break;
            case LEFT:
                imgX = 212;
                imgY = 0;
                imgW = 25;
                imgH = 32;
                break;
            case RIGHT:
                imgX = 244;
                imgY = 0;
                imgW = 25;
                imgH = 32;
                break;
        }

        short dx1 = (short) ((this.positionX * this.tileX) + this.offsetLeft);
        short dy1 = (short)((this.positionY * this.tileY) + this.offsetTop);
        short dx2 = (short)(dx1 + this.width);
        short dy2 = (short)(dy1 + this.height);

        this.g2d.drawImage(this.animalTiles, dx1, dy1, dx2, dy2, //dest w1, h1, w2, h2
                                             imgX, imgY, imgX + imgW, imgY + imgH, //source w1, h1, w2, h2
                                             null);
    
    }


    @Override
    public void update(long frametime) {
        // TODO Auto-generated method stub
    }
}