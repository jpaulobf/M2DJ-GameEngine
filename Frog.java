import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
    private boolean animating           = false;
    private Map<Integer, Byte> keyMap   = null;

    /**
     * Frog constructor
     * @param g2d
     * @param scenario
     */
    public Frog(Graphics2D g2d, Scenario scenario) {

        this.g2d        = g2d;
        this.height     = 32;
        this.width      = 32;

        this.positionX = INITIAL_POS_X;
        this.positionY = INITIAL_POS_Y;
        this.direction = UP;

        //retrieve the tile size
        this.tileX      = scenario.getTileX();
        this.tileY      = scenario.getTileY();

        //calc the distance between the entire tile and the sprite
        this.offsetLeft = (byte)((this.tileX - this.width) / 2);
        this.offsetTop  = (byte)((this.tileY - this.height) / 2);

        //calc the pixel position of the sprite while animating       
        this.inBetweenX = (short)((this.positionX * this.tileX) + this.offsetLeft);
        this.inBetweenY = (short)((this.positionY * this.tileY) + this.offsetTop);

        //calc the pixel position of the sprite
        this.pixelPosX = (short)this.inBetweenX;
        this.pixelPosY = (short)this.inBetweenY;

        try {
            this.animalTiles = ImageIO.read(new File("images\\animals.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        this.keyMap = new HashMap<Integer, Byte>();
        keyMap.put(39, RIGHT);
        keyMap.put(37, LEFT);
        keyMap.put(38, UP);
        keyMap.put(40, DOWN);
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
            this.canMove    = false;
            this.animating  = true;
            if (keyMap.get(keycode) != null) {
                execute(keyMap.get(keycode));
            }
        }
    }

    /**
     * Perform the moviment of the frog
     * @param direction
     */
    public void execute(byte direction) {
        if (direction == RIGHT) {
            if (this.positionX < 20) {
                this.positionX++;
            }
        } else if (direction == LEFT) {
            if (this.positionX > 0) {
                this.positionX--;
            }
        } else if (direction == UP) {
            if (this.positionY > 0) {
                this.positionY--;
            }
        } else if (direction == DOWN) {
            if (this.positionY < 12) {
                this.positionY++;
            }
        }

        this.pixelPosX = (short)((this.positionX * this.tileX) + this.offsetLeft);
        this.pixelPosY = (short)((this.positionY * this.tileY) + this.offsetTop);
        this.direction = direction;
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

        short dx1 = (short)(this.inBetweenX);
        short dy1 = (short)(this.inBetweenY);
        short dx2 = (short)(dx1 + this.width);
        short dy2 = (short)(dy1 + this.height);

        this.g2d.drawImage(this.animalTiles, dx1, dy1, dx2, dy2, //dest w1, h1, w2, h2
                                             imgX, imgY, imgX + imgW, imgY + imgH, //source w1, h1, w2, h2
                                             null);
    
    }

    @Override
    public void update(long frametime) {
        if (this.animating) {
            short distance  = 200; //in pixel
            short persecond = 1;
            short velocity  = (short)(distance / persecond);
            double step     = (double)velocity / (double)(1_000_000_000D / (double)frametime);

            switch(this.direction) {
                case UP:
                    this.inBetweenY -= step;
                    if ((short)(this.inBetweenY) <= this.pixelPosY) {
                        this.animating = false;
                        this.canMove = true;
                    }
                    break;
                case DOWN:
                    this.inBetweenY += step;
                    if ((short)(this.inBetweenY) >= this.pixelPosY) {
                        this.animating = false;
                        this.canMove = true;
                    }
                    break;
                case LEFT:
                    this.inBetweenX -= step;
                    if ((short)(this.inBetweenX) <= this.pixelPosX) {
                        this.animating = false;
                        this.canMove = true;
                    }
                    break;
                case RIGHT:
                    this.inBetweenX += step;
                    if ((short)(this.inBetweenX) >= this.pixelPosX) {
                        this.animating = false;
                        this.canMove = true;
                    }
                    break;
            }
        }
    }
}