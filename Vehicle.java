import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Vehicle extends SpriteImpl {
        
    //vehicles tiles
    private BufferedImage vehiclesTile          = null;

    //for the vehicles tiles
    public static final int vehiclesImgX[][]    = { {0,54}, {155,107}, {204,249}, {337,294}, {461,382} };
    public static final int vehiclesW[]         = { 45, 45, 43, 41, 76 };
    public static final int largerVehicule      = 76_000;
    
    /**
     * Load the tile image
     */
    public Vehicle(Graphics2D g2d) {
        //recupera o G2D
        this.height     = 38;
        this.g2d        = g2d;

        //Get the already loaded image from loader
        this.vehiclesTile   = (BufferedImage)LoadingStuffs.getInstance().getStuff("vehiclesTile");
    }
    
    /**
     * Draw the vehicules in the estipulated lane:
     * veicules types { 0 - car1 | 1 - car2 | 2 - car3 | 3 - tractor | 4 - truck }
     * lanes { 0 - 4 }
    */
    @Override
    public void draw(long frametime) {
        //draw the selected image
        direction = (direction == LEFT)?0:direction;
        this.g2d.drawImage(this.vehiclesTile, (int)this.positionX, (int)this.positionY, (int)(this.positionX + vehiclesW[this.type]), (int)(this.positionY + this.height), //dest w1, h1, w2, h2
                                              vehiclesImgX[this.type][this.direction], 0, vehiclesImgX[this.type][this.direction] + vehiclesW[this.type], this.height, //source w1, h1, w2, h2
                                              null);
    }

    @Override
    public void update(long frametime) {}
}
