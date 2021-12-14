/*
    Project:    Modern 2D Java Game Engine
    Purpose:    Provide basics functionalities to write 2D games in Java in a more modern approach
    Author:     Mr. Joao P. B. Faria
    Date:       Octuber 2021
    WTCD:       This class implements the nano-precision gameloop, based on the user parameters.
*/
public class GameEngine implements Runnable {

    private boolean isEngineRunning     = true;
    private long FPS240                 = (long)(1_000_000_000 / 240);
    private long FPS120                 = (long)(1_000_000_000 / 120);
    private long FPS60                  = (long)(1_000_000_000 / 60);
    private long FPS30                  = (long)(1_000_000_000 / 30);
    private long TARGET_FRAMETIME       = FPS60;
    private boolean UNLIMITED_FPS       = false;
    private Game game                   = null;
    //private long counter                 = 0;

    /*
        WTMD: constructor
                receives the target FPS (0, 30, 60, 120, 240) and starts the engine
    */
    public GameEngine(int targetFPF) {
        this.UNLIMITED_FPS = false;
        switch(targetFPF) {
            case 30:
                this.TARGET_FRAMETIME = FPS30;
                break;
            case 60:
                this.TARGET_FRAMETIME = FPS60;
                break;
            case 120:
                this.TARGET_FRAMETIME = FPS120;
                break;
            case 240:
                this.TARGET_FRAMETIME = FPS240;
                break;
            case 0:
                this.UNLIMITED_FPS = true;
                break;
            default:
                this.TARGET_FRAMETIME = FPS30;
                break;
        }

        game = new Game();
    }
    
    /* Método de update, só executa quando a flag permite */
    public void update(long frametime) {
        this.game.update(frametime);
    }

    /* Método de desenho, só executa quando a flag permite */
    public void draw(long frametime) {
        this.game.draw(frametime);
    }

    /* Método de execução da thread */
    public void run() {
        //this.game = new Game(FPS);
        long timeReference      = System.nanoTime();
        long beforeUpdate       = 0;
        long afterUpdate        = 0;
        long afterDraw          = 0;
        long accumulator        = 0;
        long timeElapsed        = 0;
        long updateDiff         = 0;
        long totalExecutionTime = 0;
        long timeStamp          = 0;

        if (UNLIMITED_FPS) {
            while (isEngineRunning) {

                //mark the time before the iteration
                timeStamp = System.nanoTime();

                //compute the time from previous iteration and the current
                timeElapsed = (timeStamp - timeReference);

                //save the difference in an accumulator to control the pacing
                accumulator += timeElapsed;

                //update the game (gathering input from user, and processing the necessary games updates)
                this.update(timeElapsed);

                //draw
                this.draw(timeElapsed);

                //update the referencial time with the initial time
                timeReference = timeStamp;
            }
        } else {
            
            while (isEngineRunning) {

                //mark the time before the iteration
                timeStamp = System.nanoTime();

                //compute the time from previous iteration and the current
                timeElapsed = (timeStamp - timeReference);

                //save the difference in an accumulator to control the pacing
                accumulator += timeElapsed;
 
                //reset total execution time                
                totalExecutionTime = 0;

                //if the accumulator surpass the desired FPS, allow new update
                if (accumulator >= TARGET_FRAMETIME) {

                    //calc the update time
                    beforeUpdate = System.nanoTime();

                    //update the game (gathering input from user, and processing the necessary games updates)
                    this.update(TARGET_FRAMETIME);

                    //reset the accumulator
                    accumulator = 0;

                    //get the timestamp after the update
                    afterUpdate = System.nanoTime();
                    updateDiff = afterUpdate - beforeUpdate;
                    
                    //only draw if there is some (any) enough time
                    if ((TARGET_FRAMETIME - updateDiff) > 0) {
                        
                        //draw
                        this.draw(TARGET_FRAMETIME);
                        
                        //and than, store the time spent
                        afterDraw = System.nanoTime() - afterUpdate;
                    }

                    //sum the time to update + the time to draw
                    totalExecutionTime = updateDiff + afterDraw;
                }
                
                /*  
                    Explanation:
                        if the total time to execute, consumes more miliseconds than the target-frame's amount, 
                        is necessary to keep updating without render, to recover the pace.
                    Important: Something here isn't working with very slow machines. 
                               So, this compensation have to be re-tested with this new approuch (exiting beforeUpdate).
                               Please test this code with your scenario.
                */
                beforeUpdate = System.nanoTime();
                while (totalExecutionTime > TARGET_FRAMETIME) {
                    this.update(TARGET_FRAMETIME);
                    afterUpdate = System.nanoTime();
                    totalExecutionTime -= (afterUpdate - beforeUpdate);
                    beforeUpdate = System.nanoTime();
                }  

                //update the referencial time with the initial time
                timeReference = timeStamp;
            }
        }
    }
}