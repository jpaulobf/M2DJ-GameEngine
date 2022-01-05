/**
 * This class is responsible for load the game stuffs
 */
public class LoadingStuffs {
    
    //private instance of loader
    private LoadingStuffs instance  = null;
    private int chargeStatus        = 0;

    /**
     * Constructor... load the game stuffs...
     */
    private LoadingStuffs() {
        //TODO:
    }

    /**
     * Recover the singleton instance  
     * @return
     */
    public LoadingStuffs getInstance() {
        if (this.instance == null) {
            this.instance = new LoadingStuffs();
        }
        return this.instance;
    }

    /**
     * Returns the charge counter status (0 ... 100%)
     * @return
     */
    public int getChargeStatus() {
        return (this.chargeStatus);
    }
}