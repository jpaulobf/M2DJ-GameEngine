import java.awt.Color;
import java.awt.Graphics2D;

/*
    WTCD: This class represents the frog sprite
*/
public class Frog extends Sprite {
    
    private byte lives                  = 3;
    private byte tileX                  = 0;
    private byte tileY                  = 0;
    private boolean canMove             = true;
    private final byte INITIAL_POS_X    = 10;
    private final byte INITIAL_POS_Y    = 12;
    


    public Frog(Graphics2D g2d, Scenario scenario) {

        this.g2d        = g2d;
        this.height     = 32;
        this.width      = 32;

        this.tileX      = scenario.getTileX();
        this.tileY      = scenario.getTileY();

        this.offsetLeft = (byte)((this.tileX - this.width) / 2);
        this.offsetTop  = (byte)((this.tileY - this.height) / 2);

        this.positionX = INITIAL_POS_X;
        this.positionY = INITIAL_POS_Y;
    }

    

    public void move(int keycode) {
        if (this.canMove) {
            if (keycode == 39) {
                if (positionX < 20) this.positionX++;
            } else if (keycode == 37) {
                if (positionX > 0) this.positionX--;
            } else if (keycode == 38) {
                if (positionY > 0) this.positionY--;
            } else if (keycode == 40) {
                if (positionY < 12) this.positionY++;
            }
        }
    }

    @Override
    public void draw(long frametime) {
        
        this.g2d.setColor(new Color(255, 0, 0));
        this.g2d.drawRect((this.positionX * this.tileX) + this.offsetLeft, (this.positionY * this.tileY) + this.offsetTop, this.width, this.height);
    
    }


    @Override
    public void update(long frametime) {
        // TODO Auto-generated method stub
    }
}