/*
    Project:    Modern 2D Java Game Engine
    Purpose:    Provide basics functionalities to write 2D games in Java in a more modern approach
    Author:     Mr. Joao P. B. Faria
    Date:       Octuber 2021
    WTCD:       This class implements the nano-precision gameloop, based on the user parameters.
*/
public class GameEngine implements Runnable {

    private boolean isEngineRunning     = true;
    private long FPS120                 = (long)(1_000_000_000 / 120);
    private long FPS60                  = (long)(1_000_000_000 / 60);
    private long FPS30                  = (long)(1_000_000_000 / 30);
    private long TARGET_FRAMETIME       = FPS60;
    private boolean UNLIMITED_FPS       = true;
    private int counter=0;

    /*
    TODO: provide the user parameters
    */
    public GameEngine() {

    }
    
    /* Método de update, só executa quando a flag permite */
    public void update(long timeElapsed) {
        if (++counter < 200) {
        System.out.println(timeElapsed);
        }
        //this.game.update(timeStamp);
    }

    /* Método de desenho, só executa quando a flag permite */
    public void draw(long timeElapsed) {
        //game.draw();
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
                    this.update(timeElapsed);

                    //reset the accumulator
                    accumulator = 0;

                    //get the timestamp after the update
                    afterUpdate = System.nanoTime();
                    updateDiff = afterUpdate - beforeUpdate;
                    
                    //only draw if there is some (any) enough time
                    if ((TARGET_FRAMETIME - updateDiff) > 0) {
                        
                        //draw
                        this.draw(timeElapsed);
                        
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
                */
                while (totalExecutionTime > TARGET_FRAMETIME) {
                    beforeUpdate = System.nanoTime();
                    this.update(timeElapsed);
                    afterUpdate = System.nanoTime();
                    totalExecutionTime -= (afterUpdate - beforeUpdate);
                }  

                //update the referencial time with the initial time
                timeReference = timeStamp;
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new GameEngine(), "engine").start();
    }
}