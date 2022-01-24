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
    private volatile byte lives             = 3;
    private final byte INITIAL_T_POS_X      = 10;
    private final byte INITIAL_T_POS_Y      = 12;
    private volatile boolean isDead         = false;
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
    private volatile short distance         = 300; //in pixel
    private volatile short persecond        = 1;
    private final double frogVelocity       = (double)((double)distance / (double)persecond);
    private int animationCounter            = 0;

    //draw image parameters
    private volatile short drawImgX         = 0;
    private volatile short drawImgY         = 0;
    private volatile short drawImgW         = 0;
    private volatile short drawImgH         = 0;
    private volatile short positionInTileX  = 0;
    private volatile short positionInTileY  = 0;
    private volatile double distanceX       = 0;
    private volatile double distanceY       = 0;
    private volatile byte jumpX             = 0;
    private volatile byte jumpY             = 0;
    private volatile byte lastMovement      = UP;

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
        
        //filter key
        this.keyMap = new HashMap<Integer, Byte>();
        keyMap.put(39, RIGHT);
        keyMap.put(37, LEFT);
        keyMap.put(38, UP);
        keyMap.put(40, DOWN);
    }

    /**
     * Initiate the frog status
     */
    public void frogReset() {
        //initial tile status
        this.height             = 25;
        this.width              = 32;

        this.positionInTileX    = INITIAL_T_POS_X;
        this.positionInTileY    = INITIAL_T_POS_Y;
        this.direction          = UP;

        //calc the distance between the entire tile and the sprite
        this.offsetLeft         = (byte)((this.tileX - this.width) / 2);
        this.offsetTop          = (byte)((this.tileY - this.height) / 2);

        //calc the pixel position of the sprite while animating       
        this.positionX          = (short)((this.positionInTileX * this.tileX) + this.offsetLeft);
        this.positionY          = (short)((this.positionInTileY * this.tileY) + this.offsetTop);

        //calc the pixel position of the sprite
        this.destPositionX      = (short)this.positionX;
        this.destPositionY      = (short)this.positionY;

        //initial frog status
        //draw image represent the position X, Y, W, H in the tile.
        this.drawImgX           = 131;
        this.drawImgY           = 3;
        this.drawImgW           = 32;
        this.drawImgH           = 25;

        //in the begining frog can move
        this.canMove            = true;
        this.lastMovement       = UP;
    }

    /**
     * Control the frog lives
     */
    public void resetLives() {
        this.lives = 3;
    }

    /**
     * Move the frog
     * @param keycode
     */
    public synchronized void move(int keycode) {
        //just if the frog can move
        if (this.canMove) {

            //filter the pressed keys
            if (keyMap.get(keycode) != null) {
                byte direction          = keyMap.get(keycode);
                this.jumpY              = 0;
                this.jumpX              = 0;
                byte offsetYCorrection  = 3; //in pixels ((32 - 25) / 2)
                
                if (direction == RIGHT) {
                    this.jumpX = this.tileX;
                    if (this.lastMovement == UP || this.lastMovement == DOWN) {
                        this.positionY -= offsetYCorrection;
                    }
                } else if (direction == LEFT) {
                    this.jumpX = (byte)-this.tileX;
                    if (this.lastMovement == UP || this.lastMovement == DOWN) {
                        this.positionY -= offsetYCorrection;
                    }
                } else if (direction == UP) {
                    this.jumpY = (byte)-this.tileY;
                    if (this.lastMovement == LEFT || this.lastMovement == RIGHT) {
                        this.positionY += offsetYCorrection;
                    }
                } else if (direction == DOWN) {
                    this.jumpY = this.tileY;
                    if (this.lastMovement == LEFT || this.lastMovement == RIGHT) {
                        this.positionY += offsetYCorrection;
                    }
                }

                this.direction      = direction;
                this.destPositionX  = (short)(this.positionX + this.jumpX);
                this.destPositionY  = (short)(this.positionY + this.jumpY);
                this.distanceX      = Math.abs((double)(this.destPositionX - this.positionX));
                this.distanceY      = Math.abs((double)(this.destPositionY - this.positionY));

                if (this.destPositionX < 0 || (this.destPositionX + this.width) > this.scenario.getWindowWidth() || 
                    this.destPositionY < 0 || this.destPositionY > this.scenario.getWindowHeight() ) {
                    this.animating      = false;
                    this.canMove        = false;
                    this.isDead         = true;
                } else {
                    this.canMove        = false;
                    this.animating      = true;
                    this.lastMovement   = direction;
                }
            }
        }
    }
    
    /**
     * Get the remained lives from the frog 
    */
    public byte getLives() {
        return lives;
    }

    /**
     * Update the frog status
     */
    @Override
    public void update(long frametime) {
        
        //while the frog is animating (moving inbetween)
        if (this.animating) {

            //calc frog step for each cicle
            double step = frogVelocity / (1_000_000_000D / (double)frametime);

            switch(this.direction) {
                case UP: //animate foward position
                    
                //calc the new Y adding the new step distance
                    this.positionY     -= step;
                    
                    //update images position static-values
                    this.width          = 32;
                    this.height         = 36;
                    this.drawImgW       = this.width;
                    this.drawImgH       = this.height;
                    this.drawImgX       = 131;
                    this.drawImgY       = ((double)(this.positionY - this.destPositionY) <= (0.35 * this.distanceY))?(short)68:(short)31;

                    //compare to verify if frog reach the target position
                    if (this.positionY <= this.destPositionY) {                      
                        this.positionY  = this.destPositionY;
                        this.positionX  = this.destPositionX;
                        this.animating  = false;
                        this.canMove    = true;
                        this.height     = 25;
                        this.drawImgY   = 3;
                        this.drawImgH   = this.height;
                    }
                    break;
                case DOWN: //animate backward position
                    //calc the new Y
                    this.positionY     += step;
                    
                    //update images position static-values
                    this.width          = 32;
                    this.height         = 36;
                    this.drawImgW       = this.width;
                    this.drawImgH       = this.height;
                    this.drawImgX       = 164;
                    this.drawImgY       = ((double)(this.destPositionY - this.positionY) <= (0.35 * this.distanceY))?(short)68:(short)31;

                    //compare to verify if frog reach the target position
                    if (this.positionY >= this.destPositionY) {
                        this.positionY  = this.destPositionY;
                        this.positionX  = this.destPositionX;
                        this.animating  = false;
                        this.canMove    = true;
                        this.height     = 25;
                        this.drawImgY   = 3;
                        this.drawImgH   = this.height;
                    }
                    break;
                case LEFT: //animate going left position
                    //calc the new X
                    this.positionX     -= step;

                    //update images position static-values
                    this.height         = 32;
                    this.width          = 36;
                    this.drawImgH       = this.height;
                    this.drawImgW       = this.width;
                    this.drawImgX       = 197;
                    this.drawImgY       = ((double)(this.positionX - this.destPositionX) <= (0.35 * this.distanceX))?(short)70:(short)33;

                    //compare to verify if frog reach the target position
                    if (this.positionX <= this.destPositionX) {
                        this.positionX  = this.destPositionX;
                        this.positionY  = this.destPositionY;
                        this.animating  = false;
                        this.canMove    = true;
                        this.width      = 25;
                        this.drawImgY   = 0;
                        this.drawImgW   = this.width;
                    }
                    break;
                case RIGHT: //animate going right position
                    //calc the new X
                    this.positionX     += step;

                    //update images position dynamic-values
                    this.height         = 32;
                    this.width          = 36;
                    this.drawImgH       = this.height;
                    this.drawImgW       = this.width;
                    this.drawImgX       = 234;
                    this.drawImgY       = ((double)(this.destPositionX - this.positionX) <= (0.35 * this.distanceX))?(short)70:(short)33;
                    
                    //compare to verify if frog reach the target position
                    if (this.positionX >= this.destPositionX) {
                        this.positionX  = this.destPositionX;
                        this.positionY  = this.destPositionY;
                        this.animating  = false;
                        this.canMove    = true;
                        this.width      = 25;
                        this.drawImgX   = 245;
                        this.drawImgY   = 0;
                        this.drawImgW   = this.width;
                    }
                    break;
            }
        }

        if ((this.positionX + this.width) > this.scenario.getWindowWidth() || this.positionX < 0) {
            this.canMove    = false;
            this.isDead     = true;
            this.animating  = false;
        }

        //colision detection or dead animation
        int colliding = -1;
        if (!this.isDead) {
            //this line test the colisions only with the cars, in the lanes.
            if (this.positionY > Lanes.streetLanes[0]) {
                colliding = this.scenario.getVehicles().testColision(this);
                if (colliding != -1) {
                    this.canMove    = false;
                    this.isDead     = true;
                    this.animating  = false;
                }
            } else if (this.positionY >= Lanes.riverLanes[0] && this.positionY < (Lanes.riverLanes[4] + this.tileY)) {
                colliding = this.scenario.getTrunks().testColision(this);
                if (colliding != -1) {
                    if (!this.animating) {
                        this.positionX += (this.scenario.getTrunks().getCalculatedStep(colliding) / 1_000);
                    }
                } else {
                    if (!animating) {
                        this.canMove    = false;
                        this.isDead     = true;
                        this.animating  = false;
                    }
                }
            } else if (this.positionY >= Lanes.docksLanes[0] && this.positionY < Lanes.docksLanes[1]) {
                colliding = this.scenario.getDockers().testColision(this);
                if (colliding != -1) {
                    if (!this.scenario.getDockers().getIsInDock()[colliding]) {
                        this.scenario.getDockers().setIsInDock(colliding);
                        this.frogReset();    
                    } else {
                        this.canMove    = false;
                        this.isDead     = true;
                        this.animating  = false;
                    }
                } else {
                    this.canMove    = false;
                    this.isDead     = true;
                    this.animating  = false;
                }
            }
            this.animationCounter = 0;
        } else { 
            //The frog is dead... Define the dead animation parameters...
            this.animationCounter += frametime;
            this.setDeadAnimationFrame();
        }
    }

    /**
     * Draw the frog in screen
     */
    @Override
    public void draw(long frametime) {     

        if (this.isDead) {
            short dx1 = (short)(this.positionX - ((this.drawImgW - this.width) / 2));
            short dy1 = (short)(this.positionY - ((this.drawImgH - this.height) / 2));
            short dx2 = (short)(dx1 + this.drawImgW);
            short dy2 = (short)(dy1 + this.drawImgH);
            this.g2d.drawImage(this.froggerDeadTiles, dx1, dy1, dx2, dy2, //dest w1, h1, w2, h2
                                                      drawImgX, drawImgY, (drawImgX + drawImgW), (drawImgY + drawImgH), //source w1, h1, w2, h2
                                                      null);
        } else {
            short dx1 = (short)(this.positionX);
            short dy1 = (short)(this.positionY);
            short dx2 = (short)(dx1 + this.width);
            short dy2 = (short)(dy1 + this.height);
            this.g2d.drawImage(this.animalTiles, dx1, dy1, dx2, dy2,                                                //dest w1, h1, w2, h2
                                                 drawImgX, drawImgY, (drawImgX + drawImgW), (drawImgY + drawImgH),  //source w1, h1, w2, h2
                                                 null);
        }
    }

    /**
     * Control frog dead animation
     */
    private void setDeadAnimationFrame() {
        if (this.animationCounter < 250_000_000) {
            //get the dead frame 1
            this.drawImgX   = 1;
            this.drawImgY   = 5;
            this.drawImgW   = 45;
            this.drawImgH   = 48;
        } else if (this.animationCounter < 500_000_000) {
            //get the dead frame 2
            this.drawImgX   = 72;
            this.drawImgY   = 2;
            this.drawImgW   = 54;
            this.drawImgH   = 56;
        } else if (this.animationCounter < 750_000_000) {
            //get the dead frame 3
            this.drawImgX   = 148;
            this.drawImgY   = 0;
            this.drawImgW   = 61;
            this.drawImgH   = 58;
        } else if (this.animationCounter < 1_000_000_000) {
            //get the dead frame 4
            this.drawImgX   = 228;
            this.drawImgY   = 0;
            this.drawImgW   = 56;
            this.drawImgH   = 58;
        } else {
            //reset the animation
            this.animationCounter = 0;
            this.isDead     = false;
            this.lives--;
            this.frogReset();
        }
    }
}