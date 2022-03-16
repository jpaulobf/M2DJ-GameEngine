import interfaces.Sprite;
import interfaces.SpriteCollection;
import java.awt.Graphics2D;
import interfaces.Stages;

/**
 * Collection of Docker class
 */
public class Dockers extends SpriteCollection {

    private Docker[] dockers                = null;
    private boolean [] isInDock             = null;
    private Scenario scenarioRef            = null;
    private Mosquito mosquito               = null;
    private GatorHead gatorHead             = null;
    private volatile long framecounter      = 0;
    private volatile long framecounterG     = 0;
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

        //update scenario offset
        this.mosquito.setScenarioOffsetY(this.scenarioRef.getScoreHeight());
        this.gatorHead.setScenarioOffsetY(this.scenarioRef.getScoreHeight());

        //set the stage
        this.nextStage();

         this.dummy();
    }

    @SuppressWarnings("unused")
    private void dummy() {
        isInDock[0] = true;
        isInDock[1] = true;
        isInDock[2] = true;
        isInDock[4] = true;
    }
    
    @Override
    public void update(long frametime) {
        if (!this.stopped) {
            this.framecounter   += frametime;
            this.framecounterG  += frametime;

            //appearence
            if (this.framecounter >= (Stages.MOSQUITO_CONFIG[Stages.CURRENT_STAGE[0]][0] * 1_000_000_000L)) {
                this.mosquito.update(frametime);
                if (this.mosquito.appearenceFinished()) {
                    this.framecounter = 0;
                }
            } 
            
            if (Stages.GATOR_HEAD_CONFIG[Stages.CURRENT_STAGE[0]][0] != -1 &&
                this.framecounterG >= (Stages.GATOR_HEAD_CONFIG[Stages.CURRENT_STAGE[0]][0] * 1_000_000_000L)) {
                this.gatorHead.update(frametime);
                if (this.gatorHead.appearenceFinished()) {
                    this.framecounterG = 0;
                }
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

    @Override
    public synchronized void nextStage() {
        //stop update
        this.stopped = true;

        //dockers parameters
        double [] positionX     = {102, 378, 654, 930, 1206};
        double [] positionY     = {14, 14, 14, 14, 14};
        short  [] width         = {36, 36, 36, 36, 36};
        byte   [] height        = {50, 50, 50, 50, 50};
        
        //clean the current turtles array
        for (int i = 0; this.dockers != null && i < this.dockers.length; i++) {
            this.dockers[i] = null;
        }

        //create new array with the turtles of this stage
        this.dockers    = new Docker[5];
        this.isInDock   = new boolean[dockers.length];

        //instantiate the dockers objects
        for (int i = 0; i < dockers.length; i++) {
            dockers[i] = new Docker(this);
            dockers[i].config(positionX[i], positionY[i], width[i], height[i]);
            dockers[i].setScenarioOffsetY(this.scenarioRef.getScoreHeight());
        } for (int i = 0; i < this.isInDock.length; i++) {
            isInDock[i] = false;
        }
        
        //start the update
        this.stopped = false;
    }

    /**
     * Reset dockers
     */
    public void reset() {
        //initialize the dockers
        for (int i = 0; i < this.isInDock.length; i++) {
            isInDock[i] = false;
        }

        this.framecounter   = 0;
        this.framecounterG  = 0;
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
    public void setCurrentGatorHead(byte pos)   {   this.currentGatorHead = pos;    }
    public byte getCurrentMosquito()            {   return (this.currentMosquito);  }
    public byte getCurrentGatorHead()           {   return (this.currentGatorHead); }
    public Mosquito getMosquito()               {   return (this.mosquito);         }
    public GatorHead getGatorHead()             {   return (this.gatorHead);        }
    public boolean [] getIsInDock()             {   return (this.isInDock);         }
    public void setIsInDock(int position)       {   this.isInDock[position] = true; }
}