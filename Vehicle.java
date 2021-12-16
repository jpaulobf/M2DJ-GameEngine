import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Vehicle extends Sprite {
        
    //The tile image, and its elements (positions)
    protected int type                      = 0;
    protected int index                     = 0;

    /**
     * Load the tile image
     * @param g2d
     */
    public Vehicle(Graphics2D g2d) {
        //recupera o G2D
        this.g2d        = g2d;
        this.height     = 38;
    }
    
    public void draw(long frametime) {}
    public void update(long frametime) {}

    /**
     * Draw the vehicules in the estipulated lane:
     * veicules types { 0 - car1 | 1 - car2 | 2 - car3 | 3 - tractor | 4 - truck }
     * lanes { 0 - 4 }
    */ 
    protected void draw(BufferedImage vehiclesTile, int [][]vehiclesImgX, int []vehiclesW) {
        //draw the selected image
        direction = (direction == LEFT)?0:direction;
        this.g2d.drawImage(vehiclesTile, this.positionX, this.positionY, this.positionX + vehiclesW[this.type], this.positionY + this.height, //dest w1, h1, w2, h2
                                         vehiclesImgX[this.type][this.direction], 0, vehiclesImgX[this.type][this.direction] + vehiclesW[this.type], this.height, //source w1, h1, w2, h2
                                         null);
    }
}
