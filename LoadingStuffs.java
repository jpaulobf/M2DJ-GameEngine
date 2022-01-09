import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import util.Logger;

/**
 * This class is responsible for load the game stuffs
 */
public class LoadingStuffs {
    
    //private instance of loader
    private static LoadingStuffs instance   = null;
    private int chargeStatus                = 0;

    //Stuffs Map
    private Map<String, Object> stuffs      = new HashMap<String, Object>();

    /**
     * Constructor... load the game stuffs...
     */
    private LoadingStuffs() {
        //load the tiles and sprites
        try {
            BufferedImage image;
            
            image = ImageIO.read(new File("images\\animals2.png"));
            stuffs.put("animalTiles", image);
            Logger.INFO("read animal tiles", this);

            image = ImageIO.read(new File("images\\froggerdead.png"));
            stuffs.put("froggerDeadTiles", image);
            Logger.INFO("read frogger dead animation tiles", this);

            image = ImageIO.read(new File("images\\gameover.png"));
            stuffs.put("gameover", image);
            Logger.INFO("read g.o. tiles", this);

            image = ImageIO.read(new File("images\\sidewalk.png"));
            stuffs.put("sidewalk", image);
            Logger.INFO("read sidewalk tiles", this);

            image = ImageIO.read(new File("images\\splash.png"));
            stuffs.put("splashImage", image);
            Logger.INFO("read s.s. tiles", this);

            image = ImageIO.read(new File("images\\vehicules.png"));
            stuffs.put("vehiclesTile", image);
            Logger.INFO("read vehicles tiles", this);

            image = ImageIO.read(new File("images\\grass.png"));
            stuffs.put("grass", image);
            Logger.INFO("read grass tiles", this);

            image = ImageIO.read(new File("images\\subgrass.png"));
            stuffs.put("subgrass", image);
            Logger.INFO("read subgrass tiles", this);

            image = ImageIO.read(new File("images\\trunks.png"));
            stuffs.put("trunksTiles", image);
            Logger.INFO("read trunks tiles", this);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recover the stored object
     * @param objectName
     * @return
     */
    public Object getStuff(String objectName) {
        return (this.stuffs.get(objectName));
    }

    /**
     * Recover the singleton instance  
     * @return
     */
    public static LoadingStuffs getInstance() {
        if (instance == null) {
            instance = new LoadingStuffs();
        }
        return instance;
    }

    /**
     * Returns the charge counter status (0 ... 100%)
     * @return
     */
    public int getChargeStatus() {
        return (this.chargeStatus);
    }
}