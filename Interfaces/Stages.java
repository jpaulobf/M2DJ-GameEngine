package Interfaces;

public interface Stages extends Directions {
    
    //Stage 1 parameters
    public static final short [][] velocities   = {{100}, {180}, {50}, {330}, {300}};
    public static final int [][] positionsX     = {{300_000, 650_000, 1000_000}, {100_000, 400_000, 700_000}, {100_000, 500_000, 900_000}, {100_000}, {100_000, 750_000}};

    //for each lane, each car parameter
    //type, direction, start-position-x, velocity
    public static int [][][] stg1               =  { { {4, LEFT,  positionsX[4][0], velocities[4][0]}, {4, LEFT,  positionsX[4][1], velocities[4][0]}},
                                                     { {1, RIGHT, positionsX[3][0], velocities[3][0]}                                                 },
                                                     { {2, LEFT,  positionsX[2][0], velocities[2][0]}, {3, LEFT,  positionsX[2][1], velocities[2][0]}, {1, LEFT,  positionsX[2][2], velocities[2][0]} }, 
                                                     { {3, RIGHT, positionsX[1][0], velocities[1][0]}, {1, RIGHT, positionsX[1][1], velocities[1][0]}, {4, RIGHT, positionsX[1][2], velocities[1][0]} },
                                                     { {0, LEFT,  positionsX[0][0], velocities[0][0]}, {0, LEFT,  positionsX[0][1], velocities[0][0]}, {3, LEFT,  positionsX[0][2], velocities[0][0]} } };

}
