import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import interfaces.Lanes;

/*
    WTCD: This class represents the frog sprite
*/
public class Frog extends SpriteImpl {
    
    //game variable
    private byte lives                      = 3;
    private final byte INITIAL_T_POS_X      = 10;
    private final byte INITIAL_T_POS_Y      = 12;
    private boolean isDead                  = false;
    private Scenario scenario               = null;

    //render variables
    private byte tileX                      = 0;
    private byte tileY                      = 0;
    private BufferedImage animalTiles       = null;
    private BufferedImage froggerDeadTiles  = null;
    private Map<Integer, Byte> keyMap       = null;

    //animation parameters
    private volatile boolean canMove        = true;
    private volatile boolean animating      = false;
    private short distance                  = 300; //in pixel
    private short persecond                 = 1;
    private final double frogVelocity       = (double)((double)distance / (double)persecond);
    private int animationCounter            = 0;

    //draw image parameters
    private volatile short drawImgX         = 0;
    private volatile short drawImgY         = 0;
    private volatile byte drawImgW          = 0;
    private volatile byte drawImgH          = 0;
    private volatile short positionInTileX  = 0;
    private volatile short positionInTileY  = 0;
    private volatile double distanceX       = 0;
    private volatile double distanceY       = 0;

    /**
     * Frog constructor
     * @param g2d
     * @param scenario
     */
    public Frog(Graphics2D g2d, Scenario scenario) {

        //store the G2D
        this.g2d        = g2d;
        
        //retrieve the tile size
        this.scenario   = scenario;
        this.tileX      = scenario.getTileX();
        this.tileY      = scenario.getTileY();

        //start the frog parameters
        this.frogReset();

        //load the tiles and sprites
        this.animalTiles        = (BufferedImage)LoadingStuffs.getInstance().getStuff("animalTiles");
        this.froggerDeadTiles   = (BufferedImage)LoadingStuffs.getInstance().getStuff("froggerDeadTiles");
        
        /*try {
            this.animalTiles = ImageIO.read(new File("images\\animals2.png"));
            this.froggerDeadTiles = ImageIO.read(new File("images\\froggerdead.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }*/

        this.keyMap = new HashMap<Integer, Byte>();
        keyMap.put(39, RIGHT);
        keyMap.put(37, LEFT);
        keyMap.put(38, UP);
        keyMap.put(40, DOWN);
    }

    /**
     * Get the remained lives from the frog 
    */
    public byte getLives() {
        return lives;
    }

    /**
     * 
     * @param keycode
     */
    public synchronized void move(int keycode) {
        if (this.canMove) {
            if (keyMap.get(keycode) != null) {
                execute(keyMap.get(keycode));
            }
        }
    }

    /**
     * Perform the moviment of the frog
     * @param direction
     */
    public synchronized void execute(byte direction) {
        if (direction == RIGHT) {
            if (this.positionInTileX < 20) {
                this.positionInTileX++;
            }
        } else if (direction == LEFT) {
            if (this.positionInTileX > 0) {
                this.positionInTileX--;
            }
        } else if (direction == UP) {
            if (this.positionInTileY > 0) {
                this.positionInTileY--;
            }
        } else if (direction == DOWN) {
            if (this.positionInTileY < 12) {
                this.positionInTileY++;
            }
        }

        this.positionX  = (short)((this.positionInTileX * this.tileX) + this.offsetLeft);
        this.positionY  = (short)((this.positionInTileY * this.tileY) + this.offsetTop);
        this.direction  = direction;
        this.distanceX = Math.abs((double)(this.inBetweenX - this.positionX));
        this.distanceY = Math.abs((double)(this.inBetweenY - this.positionY));
        this.canMove    = false;
        this.animating  = true;
    }

    @Override
    public void draw(long frametime) {     

        if (this.isDead) {
            short dx1 = (short)(this.positionX - ((this.drawImgW - this.width) / 2));
            short dy1 = (short)(this.positionY - ((this.drawImgH - this.height) / 2));
            short dx2 = (short)(dx1 + this.drawImgW);
            short dy2 = (short)(dy1 + this.drawImgH);

            this.g2d.drawImage(this.froggerDeadTiles, dx1, dy1, dx2, dy2, //dest w1, h1, w2, h2
                                                      drawImgX, drawImgY, drawImgX + drawImgW, drawImgY + drawImgH, //source w1, h1, w2, h2
                                                      null);
        } else {
            short dx1 = (short)(this.inBetweenX);
            short dy1 = (short)(this.inBetweenY);
            short dx2 = (short)(dx1 + this.width);
            short dy2 = (short)(dy1 + this.height);
            this.g2d.drawImage(this.animalTiles, dx1, dy1, dx2, dy2, //dest w1, h1, w2, h2
                                                 drawImgX, drawImgY, drawImgX + drawImgW, drawImgY + drawImgH, //source w1, h1, w2, h2
                                                 null);
        }
    }

    @Override
    /**
     * Update the frog
     */
    public void update(long frametime) {
        //just if the frog is animating
        if (this.animating) {

            //calc frog step for each cicle
            double step = frogVelocity / (1_000_000_000D / (double)frametime);

            //switch the directin
            switch(this.direction) {
                case UP:
                    //calc the new Y
                    this.inBetweenY    -= step;

                    //update images position static-values
                    this.drawImgX       = 131;
                    this.drawImgW       = 32;
                    this.width          = 32;
                    this.drawImgH       = 36;
                    this.height         = 36;
                    this.drawImgY       = ((double)(this.inBetweenY - this.positionY) <= (0.25 * distanceY))?(short)68:(short)31;
                    
                    //compare to verify if frog reach the target position
                    if ((short)(this.inBetweenY - step) <= this.positionY) {                      
                        this.inBetweenY = this.positionY;
                        this.animating  = false;
                        this.canMove    = true;
                        this.drawImgY   = 3;
                        this.drawImgH   = 25;
                        this.height     = 25;
                    }
                    break;
                case DOWN:
                    //calc the new Y
                    this.inBetweenY += step;
                    
                    //update images position static-values
                    this.drawImgX       = 164;
                    this.drawImgW       = 32;
                    this.width          = 32;
                    this.drawImgH       = 36;
                    this.height         = 36;
                    this.drawImgY       = ((double)(this.positionY - this.inBetweenY) <= (0.25 * distanceY))?(short)68:(short)31;

                    //compare to verify if frog reach the target position
                    if ((short)(this.inBetweenY + step) >= this.positionY) {
                        this.inBetweenY = this.positionY;
                        this.animating  = false;
                        this.canMove    = true;
                        this.drawImgY   = 3;
                        this.drawImgH   = 25;
                        this.height     = 25;
                    }
                    break;
                case LEFT:
                    //calc the new X
                    this.inBetweenX    -= step;

                    //update images position static-values
                    this.drawImgX       = 197;
                    this.drawImgH       = 32;
                    this.height         = 32;
                    this.drawImgW       = 36;
                    this.width          = 36;
                    this.drawImgY       = ((double)(this.inBetweenX - this.positionX) <= (0.25 * distanceX))?(short)70:(short)33;

                    //compare to verify if frog reach the target position
                    if ((short)(this.inBetweenX - step) <= this.positionX) {
                        this.inBetweenX = this.positionX;
                        this.animating  = false;
                        this.canMove    = true;
                        this.drawImgY   = 0;
                        this.drawImgW   = 25;
                        this.width      = 25;
                    }
                    break;
                case RIGHT:
                    //calc the new X
                    this.inBetweenX += step;

                    //update images position dynamic-values
                    this.drawImgH       = 32;
                    this.height         = 32;
                    this.drawImgX       = 234;
                    this.drawImgW       = 36;
                    this.width          = 36;
                    this.drawImgY       = ((double)(this.positionX - this.inBetweenX) <= (0.25 * distanceX))?(short)70:(short)33;

                    //compare to verify if frog reach the target position
                    if ((short)(this.inBetweenX + step) >= this.positionX) {
                        this.inBetweenX = this.positionX;
                        this.animating  = false;
                        this.canMove    = true;
                        this.drawImgX   = 245;
                        this.drawImgY   = 0;
                        this.drawImgW   = 25;
                        this.width      = 25;
                    }
                    break;
            }
        }

        int coliding = -1;
        if (!this.isDead) {
            //this line test the colisions only with the cars, in the lanes.
            if (this.positionY > Lanes.streetLanes[0]) {
                coliding = this.scenario.getVehicles().testColision(this);
            }
            if (coliding != -1) {
                this.canMove    = false;
                this.isDead     = true;
                this.animating  = false;
            }
            this.animationCounter = 0;
        } else {
            this.animationCounter += frametime;
            if (this.animationCounter < 250_000_000) {
                this.drawImgX   = 1;
                this.drawImgY   = 5;
                this.drawImgW   = 45;
                this.drawImgH   = 48;
            } else if (this.animationCounter < 500_000_000) {
                this.drawImgX   = 72;
                this.drawImgY   = 2;
                this.drawImgW   = 54;
                this.drawImgH   = 56;
            } else if (this.animationCounter < 750_000_000) {
                this.drawImgX   = 148;
                this.drawImgY   = 0;
                this.drawImgW   = 61;
                this.drawImgH   = 58;
            } else if (this.animationCounter < 1_000_000_000) {
                this.drawImgX   = 228;
                this.drawImgY   = 0;
                this.drawImgW   = 56;
                this.drawImgH   = 58;
            } else {
                this.animationCounter = 0;
                this.isDead = false;
                this.lives--; //TODO: CONTROL THE LIVES
                this.frogReset();
            }
        }
    }

    public void resetLives() {
        this.lives = 3;
    }

    public void frogReset() {
        //initial tile status
        this.height     = 25;
        this.width      = 32;

        this.positionInTileX = INITIAL_T_POS_X;
        this.positionInTileY = INITIAL_T_POS_Y;
        this.direction = UP;

        //calc the distance between the entire tile and the sprite
        this.offsetLeft = (byte)((this.tileX - this.width) / 2);
        this.offsetTop  = (byte)((this.tileY - this.height) / 2);

        //calc the pixel position of the sprite while animating       
        this.inBetweenX = (short)((this.positionInTileX * this.tileX) + this.offsetLeft);
        this.inBetweenY = (short)((this.positionInTileY * this.tileY) + this.offsetTop);

        //calc the pixel position of the sprite
        this.positionX = (short)this.inBetweenX;
        this.positionY = (short)this.inBetweenY;

        //initial frog status
        this.drawImgX   = 131;
        this.drawImgY   = 3;
        this.drawImgW   = 32;
        this.drawImgH   = 25;

        this.canMove = true;
    }
}