import interfaces.Sprite;
import interfaces.SpriteCollection;
import java.awt.Graphics2D;

/**
 * Collection of Docker class
 */
public class Dockers extends SpriteCollection {

    private Docker[] dockers            = new Docker[5];
    private boolean [] isInDock         = new boolean[dockers.length];
    private Scenario scenarioRef        = null;
    private Mosquito mosquito           = null;
    private volatile long framecounter  = 0;

    /**
     * Dockers constructor
     */
    public Dockers(Scenario scenarioRef) {
        this.scenarioRef        = scenarioRef;
        this.mosquito           = new Mosquito(this);
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

        isInDock[0] = true;
        isInDock[1] = true;
        isInDock[2] = true;
        isInDock[4] = true;
    }

    /**
     * Getter
     * @return
     */
    public boolean [] getIsInDock() {
        return (this.isInDock);
    }

    /**
     * Setter
     * @param position
     */
    public void setIsInDock(int position) {
        this.isInDock[position] = true;
    }

    @Override
    protected Sprite[] getSpriteCollection() {
         return (this.dockers);
    }

    @Override
    public void update(long frametime) {
        this.framecounter += frametime;

        if (this.framecounter >= 5_000_000_000L) {
            if (!this.mosquito.isAnimating()) {
                this.framecounter = 0;
            }
            this.mosquito.update(frametime);
        }
    }

    @Override
    public void draw(long frametime) {
        for (byte i = 0; i < dockers.length; i++) {
            this.dockers[i].draw(frametime);
        }
        this.mosquito.draw(frametime);
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
     * Get the mosquito object
     * @return
     */
    public Mosquito getMosquito() {
        return (this.mosquito);
    }

    /**
     * Reset dockers
     */
    public void reset() {
        //initialize the dockers
        for (int i = 0; i < this.isInDock.length; i++) {
            isInDock[i] = false;
        }
    }

    @Override
    public Graphics2D getG2D() {
        return (this.scenarioRef.getGameRef().getG2D());
    }
}
