import interfaces.Sprite;
import interfaces.SpriteCollection;
import java.awt.Graphics2D;

/**
 * Collection of Docker class
 */
public class Dockers extends SpriteCollection {

    private Docker[] dockers                = new Docker[5];
    private boolean [] isInDock             = new boolean[dockers.length];
    private Scenario scenarioRef            = null;
    private Mosquito mosquito               = null;
    private GatorHead gatorHead             = null;
    private volatile long framecounter      = 0;
    private volatile boolean stopped        = false;
    public volatile byte currentMosquito    = -1;
    public volatile byte currentGatorHead   = -1;

    /**
     * Dockers constructor
     */
    public Dockers(Scenario scenarioRef) {
        this.scenarioRef        = scenarioRef;
        this.mosquito           = new Mosquito(this);
        this.gatorHead          = new GatorHead(this);
        double [] positionX     = {102, 378, 654, 930, 1206};
        double [] positionY     = {14, 14, 14, 14, 14};
        short  [] width         = {36, 36, 36, 36, 36};
        byte   [] height        = {50, 50, 50, 50, 50};

        for (int i = 0; i < dockers.length; i++) {
            dockers[i] = new Docker(this);
            dockers[i].config(positionX[i], positionY[i], width[i], height[i]);
            dockers[i].setScenarioOffsetY(this.scenarioRef.getScoreHeight());
        }

        //initialize the dockers
        for (int i = 0; i < this.isInDock.length; i++) {
            isInDock[i] = false;
        }

        //update scenario offset
        this.mosquito.setScenarioOffsetY(this.scenarioRef.getScoreHeight());
        this.gatorHead.setScenarioOffsetY(this.scenarioRef.getScoreHeight());

        // isInDock[0] = true;
        // isInDock[1] = true;
        // isInDock[2] = true;
        // isInDock[4] = true;
    }
    
    @Override
    public void update(long frametime) {
        if (!this.stopped) {
            this.framecounter += frametime;

            //TODO: SEPARE MOSQUITO & GATOR-HEAD
            if (this.framecounter >= 5_000_000_000L) {
                if (this.mosquito.isSorting()) {
                    this.framecounter = 0;
                }
                
                if (this.gatorHead.isSorting()) {
                    this.framecounter = 0;
                }

                this.mosquito.update(frametime);
                this.gatorHead.update(frametime);
            }
        }
    }

    @Override
    public void draw(long frametime) {
        for (byte i = 0; i < dockers.length; i++) {
            this.dockers[i].draw(frametime);
        }
        this.mosquito.draw(frametime);
        this.gatorHead.draw(frametime);
    }

    @Override
    protected Sprite[] getSpriteCollection() {
         return (this.dockers);
    }

    @Override
    public Graphics2D getG2D() {
        return (this.scenarioRef.getGameRef().getG2D());
    }

    /**
     * Return the number of free dockers
     * @return
     */
    public byte getFreeDockersCounter() {
        byte free = 0;
        for (int i = 0; i < this.isInDock.length; i++) {
            free += (this.isInDock[i]?0:1);
        }
        return (free);
    }

    /**
     * Verify if all dockers are filled
     * @return
     */
    public boolean getDockersComplete() {
        boolean complete = true;
        for (int cnt = 0; cnt < this.isInDock.length; cnt++) {
            complete &= this.isInDock[cnt];
        }
        return (complete);
    }

    /**
     * Reset dockers
     */
    public void reset() {
        //initialize the dockers
        for (int i = 0; i < this.isInDock.length; i++) {
            isInDock[i] = false;
        }
        this.mosquito.reset();
        this.gatorHead.reset();
    }

    /**
     * Toogle the stop control
     */
    public void toogleStop() {
        this.stopped = !this.stopped;
    }

    //Accessors
    public void setCurrentMosquito(byte pos)    {   this.currentMosquito = pos;     }
    public void setCurrentGatorHead(byte pos)   {   this.currentGatorHead   = pos;  }
    public byte getCurrentMosquito()            {   return(this.currentMosquito);   }
    public byte getCurrentGatorHead()           {   return(this.currentGatorHead);  }
    public Mosquito getMosquito()               {   return (this.mosquito);         }
    public GatorHead getGatorHead()             {   return (this.gatorHead);        }
    public boolean [] getIsInDock()             {   return (this.isInDock);         }
    public void setIsInDock(int position)       {   this.isInDock[position] = true; }
}
