package game;

import java.awt.image.BufferedImage;

import interfaces.SpriteCollection;

/**
 * Docker sprite class
 */
public class Docker extends SpriteImpl {

    //vehicles tiles
    private BufferedImage docker            = null;
    private SpriteCollection spriteColRef   = null;

    /**
     * Docker constructor
     */
    public Docker(SpriteCollection spriteCol) {
        this.spriteColRef   = spriteCol;
        this.docker         = (BufferedImage)LoadingStuffs.getInstance().getStuff("pixel");
    }

    /**
     * Docker configuration method
     * @param positionX
     * @param positionY
     * @param width
     * @param height
     */
    public void config(double positionX, double positionY, short width, byte height) {
        this.positionX  = positionX;
        this.positionY  = positionY;
        this.width      = width;
        this.height     = height;
    }

    @Override
    public void draw(long frametime) {
        this.spriteColRef.getG2D().drawImage(this.docker, (int)this.positionX, (int)this.positionY + this.scenarioOffsetY, (int)(this.positionX + this.width), (int)(this.positionY + this.height + this.scenarioOffsetY), //dest w1, h1, w2, h2
                                                           0, 0, 1, 1, //source w1, h1, w2, h2
                                                           null);
        
    }

    @Override
    public void update(long frametime) {}
}