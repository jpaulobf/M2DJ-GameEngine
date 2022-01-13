package interfaces;

public interface Stages extends Directions {
    
    //Stage 1 parameters
    public static final short [][] CARS_VELOCITIES_STG1     = {{100}, {180}, {50}, {330}, {300}};
    public static final int [][] CARS_POSITION_X_STG1       = {{300_000, 650_000, 1000_000}, {100_000, 400_000, 700_000}, {100_000, 500_000, 900_000}, {100_000}, {100_000, 750_000}};

    public static final short [][] TRUNKS_VELOCITIES_STG1   = {{}, {60}, {120}, {}, {90}};
    public static final int [][] TRUNKS_POSITION_X_STG1     = {{}, {50_000, 350_000, 650_000}, {100_000, 800_000}, {}, {0_000, 400_000, 800_000}};

    //for each lane, each car parameter
    //type, direction, start-position-x, velocity
    public static int [][][] STAGE1_CARS        =  { { {4, LEFT,  CARS_POSITION_X_STG1[4][0], CARS_VELOCITIES_STG1[4][0]}, {4, LEFT,  CARS_POSITION_X_STG1[4][1], CARS_VELOCITIES_STG1[4][0]}},
                                                     { {3, RIGHT, CARS_POSITION_X_STG1[3][0], CARS_VELOCITIES_STG1[3][0]}  },
                                                     { {2, LEFT,  CARS_POSITION_X_STG1[2][0], CARS_VELOCITIES_STG1[2][0]}, {2, LEFT,  CARS_POSITION_X_STG1[2][1], CARS_VELOCITIES_STG1[2][0]}, {2, LEFT,  CARS_POSITION_X_STG1[2][2], CARS_VELOCITIES_STG1[2][0]} }, 
                                                     { {1, RIGHT, CARS_POSITION_X_STG1[1][0], CARS_VELOCITIES_STG1[1][0]}, {1, RIGHT, CARS_POSITION_X_STG1[1][1], CARS_VELOCITIES_STG1[1][0]}, {1, RIGHT, CARS_POSITION_X_STG1[1][2], CARS_VELOCITIES_STG1[1][0]} },
                                                     { {0, LEFT,  CARS_POSITION_X_STG1[0][0], CARS_VELOCITIES_STG1[0][0]}, {0, LEFT,  CARS_POSITION_X_STG1[0][1], CARS_VELOCITIES_STG1[0][0]}, {0, LEFT,  CARS_POSITION_X_STG1[0][2], CARS_VELOCITIES_STG1[0][0]} } 
                                                   };

    public static int [][][] STAGE1_TRUNKS      =  { { {1, RIGHT, TRUNKS_POSITION_X_STG1[4][0], TRUNKS_VELOCITIES_STG1[4][0]}, {1, RIGHT, TRUNKS_POSITION_X_STG1[4][1], TRUNKS_VELOCITIES_STG1[4][0]}, {1, RIGHT, TRUNKS_POSITION_X_STG1[4][2], TRUNKS_VELOCITIES_STG1[4][0]}},
                                                     { {} },
                                                     { {2, RIGHT, TRUNKS_POSITION_X_STG1[2][0], TRUNKS_VELOCITIES_STG1[2][0]}, {2, RIGHT, TRUNKS_POSITION_X_STG1[2][1], TRUNKS_VELOCITIES_STG1[2][0]}}, 
                                                     { {0, RIGHT, TRUNKS_POSITION_X_STG1[1][0], TRUNKS_VELOCITIES_STG1[1][0]}, {0, RIGHT, TRUNKS_POSITION_X_STG1[1][1], TRUNKS_VELOCITIES_STG1[1][0]}, {0, RIGHT, TRUNKS_POSITION_X_STG1[1][2], TRUNKS_VELOCITIES_STG1[1][0]} },
                                                     { {} } 
                                                   };

}
