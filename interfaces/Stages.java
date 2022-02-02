package interfaces;

public interface Stages extends Directions {
    
    //Set the current stage
    public static int CURRENT_STAGE                       = 1;  
  
    //Stage 1 parameters
    public static final short [][] S1_CARS_VELOCITIES     = {{120}, {180}, {90}, {330}, {300}};
    //public static final int [][] S1_CARS_POSITION_X       = {{300_000, 650_000, 1000_000}, {100_000, 400_000, 700_000}, {100_000, 500_000, 900_000}, {100_000}, {100_000, 750_000}};

    public static final int [][] S1_CARS_POSITION_X       = {{250_000, 500_000, 750_000, 1_000_000, 1_250_000}, {120_000, 340_000, 560_000, 780_000, 1_000_000, 1_220_000, 1_440_000}, {0_000, 280_000, 560_000, 840_000, 1_120_000}, {100_000, 400_000, 700_000, 1_000_000}, {100_000, 500_000, 900_000}};
    //public static final int [][] S1_CARS_POSITION_X       = {{300_000, 650_000, 1000_000}, {100_000, 400_000, 700_000}, {100_000, 500_000, 900_000}, {100_000}, {100_000, 750_000}};

    public static final short [][] S1_TRUNKS_VELOCITIES   = {{120}, {70}, {230}, {50}, {90}};
    public static final short [][] S1_TURTLES_VELOCITIES  = {{100}, {50}};

    public static final int [][] S1_TRUNKS_POSITION_X     = {{150_000}, {50_000, 350_000, 650_000}, {100_000, 800_000}, {100_000, 700_000}, {0_000, 400_000, 800_000}};
    public static final int [][] S1_TURTLES_POSITION_X    = {{50_000, 390_000, 730_000, 1_070_000}, {0_000, 300_000, 600_000, 900_000}};

    //cars & trunks in each stage (ignore item 0)
    public static final int [] CURRENT_STAGE_CARS         = {0, 24};
    //public static final int [] CURRENT_STAGE_CARS         = {0, 12};
    public static final int [] CURRENT_STAGE_TRUNKS       = {0, 11};
    public static final int [] CURRENT_STAGE_TURTLES      = {0, 8};

    //for each lane, each car parameter
    //first array is direction of the line, then, for each car:
    //type, start-position-x, velocity
    public static int [][][] S1_CARS        =  { { {LEFT},  {S1_CARS_VELOCITIES[4][0]}, {4}, {S1_CARS_POSITION_X[4][0], S1_CARS_POSITION_X[4][1], S1_CARS_POSITION_X[4][2] }},
                                                 { {RIGHT}, {S1_CARS_VELOCITIES[3][0]}, {3}, {S1_CARS_POSITION_X[3][0], S1_CARS_POSITION_X[3][1], S1_CARS_POSITION_X[3][2], S1_CARS_POSITION_X[3][3]}},
                                                 { {LEFT},  {S1_CARS_VELOCITIES[2][0]}, {2}, {S1_CARS_POSITION_X[2][0], S1_CARS_POSITION_X[2][1], S1_CARS_POSITION_X[2][2], S1_CARS_POSITION_X[2][3], S1_CARS_POSITION_X[2][4]} }, 
                                                 { {RIGHT}, {S1_CARS_VELOCITIES[1][0]}, {1}, {S1_CARS_POSITION_X[1][0], S1_CARS_POSITION_X[1][1], S1_CARS_POSITION_X[1][2], S1_CARS_POSITION_X[1][3], S1_CARS_POSITION_X[1][4], S1_CARS_POSITION_X[1][5], S1_CARS_POSITION_X[1][6]} },
                                                 { {LEFT},  {S1_CARS_VELOCITIES[0][0]}, {0}, {S1_CARS_POSITION_X[0][0], S1_CARS_POSITION_X[0][1], S1_CARS_POSITION_X[0][2], S1_CARS_POSITION_X[0][3], S1_CARS_POSITION_X[0][4]} } 
                                               };
    
    // public static int [][][] S1_CARS        =  { { {LEFT},  {S1_CARS_VELOCITIES[4][0]}, {4}, {S1_CARS_POSITION_X[4][0], S1_CARS_POSITION_X[4][1]}},
    //                                              { {RIGHT}, {S1_CARS_VELOCITIES[3][0]}, {3}, {S1_CARS_POSITION_X[3][0]}},
    //                                              { {LEFT},  {S1_CARS_VELOCITIES[2][0]}, {2}, {S1_CARS_POSITION_X[2][0], S1_CARS_POSITION_X[2][1], S1_CARS_POSITION_X[2][2]} }, 
    //                                              { {RIGHT}, {S1_CARS_VELOCITIES[1][0]}, {1}, {S1_CARS_POSITION_X[1][0], S1_CARS_POSITION_X[1][1], S1_CARS_POSITION_X[1][2]} },
    //                                              { {LEFT},  {S1_CARS_VELOCITIES[0][0]}, {0}, {S1_CARS_POSITION_X[0][0], S1_CARS_POSITION_X[0][1], S1_CARS_POSITION_X[0][2]} } 
    //                                            };



    public static int [][][] S1_TRUNKS      =  { { {RIGHT}, {S1_TRUNKS_VELOCITIES[4][0]}, {1}, {S1_TRUNKS_POSITION_X[4][0], S1_TRUNKS_POSITION_X[4][1], S1_TRUNKS_POSITION_X[4][2]}},
                                                 {},
                                                 { {RIGHT}, {S1_TRUNKS_VELOCITIES[2][0]}, {2}, {S1_TRUNKS_POSITION_X[2][0], S1_TRUNKS_POSITION_X[2][1]}}, 
                                                 { {RIGHT}, {S1_TRUNKS_VELOCITIES[1][0]}, {0}, {S1_TRUNKS_POSITION_X[1][0], S1_TRUNKS_POSITION_X[1][1], S1_TRUNKS_POSITION_X[1][2]}},
                                                 {}
                                               };

    public static int [][][] S1_TURTLES     =  { {},
                                                 { {LEFT},  {S1_TURTLES_VELOCITIES[1][0]}, {0}, {1, 0, 0, 0}, {S1_TURTLES_POSITION_X[1][0], S1_TURTLES_POSITION_X[1][1], S1_TURTLES_POSITION_X[1][2], S1_TURTLES_POSITION_X[1][3]}},
                                                 {}, 
                                                 {},
                                                 { {LEFT},  {S1_TURTLES_VELOCITIES[0][0]}, {1}, {1, 0, 1, 0}, {S1_TURTLES_POSITION_X[0][0], S1_TURTLES_POSITION_X[0][1], S1_TURTLES_POSITION_X[0][2], S1_TURTLES_POSITION_X[0][3]}} 
                                               };

}