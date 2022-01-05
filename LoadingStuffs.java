import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for load the game stuffs
 */
public class LoadingStuffs {
    
    //private instance of loader
    private static LoadingStuffs instance   = null;
    private int chargeStatus                = 0;

    //gameover tiles
    private BufferedImage gameover          = null;

    //scenario tiles
    private BufferedImage sidewalk          = null;

    //splash screen
    private BufferedImage splashImage       = null;

    //vehicles tiles
    private BufferedImage vehiclesTile      = null;

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

            image = ImageIO.read(new File("images\\froggerdead.png"));
            stuffs.put("froggerDeadTiles", image);

            image = ImageIO.read(new File("images\\gameover.png"));
            stuffs.put("gameover", image);

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