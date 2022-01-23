package interfaces;

public interface Stages extends Directions {
    
    //Set the current stage
    public static int CURRENT_STAGE                       = 1;  
  
    //Stage 1 parameters
    public static final short [][] S1_CARS_VELOCITIES     = {{100}, {180}, {50}, {330}, {300}};
    public static final int [][] S1_CARS_POSITION_X       = {{300_000, 650_000, 1000_000}, {100_000, 400_000, 700_000}, {100_000, 500_000, 900_000}, {100_000}, {100_000, 750_000}};

    public static final short [][] S1_TRUNKS_VELOCITIES   = {{120}, {70}, {230}, {50}, {90}};
    public static final int [][] S1_TRUNKS_POSITION_X     = {{150_000}, {50_000, 350_000, 650_000}, {100_000, 800_000}, {100_000, 700_000}, {0_000, 400_000, 800_000}};

    //cars & trunks in each stage (ignore item 0)
    public static final int [] CURRENT_STAGE_CARS         = {0, 12};
    public static final int [] CURRENT_STAGE_TRUNKS       = {0, 11};

    //for each lane, each car parameter
    //first array is direction of the line, then, for each car:
    //type, start-position-x, velocity
    public static int [][][] S1_CARS        =  { { {LEFT},  {S1_CARS_VELOCITIES[4][0]}, {4}, {S1_CARS_POSITION_X[4][0], S1_CARS_POSITION_X[4][1]}},
                                                 { {RIGHT}, {S1_CARS_VELOCITIES[3][0]}, {3}, {S1_CARS_POSITION_X[3][0]}},
                                                 { {LEFT},  {S1_CARS_VELOCITIES[2][0]}, {2}, {S1_CARS_POSITION_X[2][0], S1_CARS_POSITION_X[2][1], S1_CARS_POSITION_X[2][2]} }, 
                                                 { {RIGHT}, {S1_CARS_VELOCITIES[1][0]}, {1}, {S1_CARS_POSITION_X[1][0], S1_CARS_POSITION_X[1][1], S1_CARS_POSITION_X[1][2]} },
                                                 { {LEFT},  {S1_CARS_VELOCITIES[0][0]}, {0}, {S1_CARS_POSITION_X[0][0], S1_CARS_POSITION_X[0][1], S1_CARS_POSITION_X[0][2]} } 
                                               };


    public static int [][][] S1_TRUNKS      =  { { {RIGHT}, {S1_TRUNKS_VELOCITIES[4][0]}, {1}, {S1_TRUNKS_POSITION_X[4][0], S1_TRUNKS_POSITION_X[4][1], S1_TRUNKS_POSITION_X[4][2]}},
                                                 { {LEFT},  {S1_TRUNKS_VELOCITIES[3][0]}, {1}, {S1_TRUNKS_POSITION_X[3][0], S1_TRUNKS_POSITION_X[3][1]}},
                                                 //{ {LEFT},  {3, S1_TRUNKS_POSITION_X[3][0], S1_TRUNKS_VELOCITIES[3][0]},  {3, S1_TRUNKS_POSITION_X[3][1], S1_TRUNKS_VELOCITIES[3][0]}, {3, S1_TRUNKS_POSITION_X[3][2], S1_TRUNKS_VELOCITIES[3][0]},  {3, S1_TRUNKS_POSITION_X[3][3], S1_TRUNKS_VELOCITIES[3][0]}},
                                                 { {RIGHT}, {S1_TRUNKS_VELOCITIES[2][0]}, {2}, {S1_TRUNKS_POSITION_X[2][0], S1_TRUNKS_POSITION_X[2][1]}}, 
                                                 { {RIGHT}, {S1_TRUNKS_VELOCITIES[1][0]}, {0}, {S1_TRUNKS_POSITION_X[1][0], S1_TRUNKS_POSITION_X[1][1], S1_TRUNKS_POSITION_X[1][2]}},
                                                 { {LEFT},  {S1_TRUNKS_VELOCITIES[0][0]}, {2}, {S1_TRUNKS_POSITION_X[0][0]}} 
                                                 //{ {LEFT},  {4, S1_TRUNKS_POSITION_X[0][0], S1_TRUNKS_VELOCITIES[0][0]},  {4, S1_TRUNKS_POSITION_X[0][1], S1_TRUNKS_VELOCITIES[0][0]}, {4, S1_TRUNKS_POSITION_X[0][2], S1_TRUNKS_VELOCITIES[0][0]}} 
                                               };

}
