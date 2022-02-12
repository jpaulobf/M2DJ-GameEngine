package interfaces;

import java.awt.Graphics2D;

/**
 * All games need to implement the IGame interface
 */
public interface IGame {
    /**
     * Game update
     * @param frametime
     */
    public void update(long frametime);

    /**
     * Game draw
     * @param frametime
     */
    public void draw(long frametime);
    
    /**
     * Mute the music
     */
    public void toogleMuteTheme();

    /**
     * Stop the music
     */
    public void stopTheme();

    /**
     * Pause the game
     */
    public void tooglePause();

    /**
     * Soft reset
     */
    public void softReset();

    /**
     * Key pressed
     * @param keyCode
     */
    public void keyPressed(int keyCode);

    /**
     * Key released
     * @param keyCode
     */
    public void keyReleased(int keyCode);

    /**
     * Update Graphics
     * @param g2d
     */
    public void updateGraphics2D(Graphics2D g2d);
}