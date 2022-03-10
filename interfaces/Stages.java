package interfaces;

public interface Stages extends Directions {
    
    //Set the current stage
    public static int [] CURRENT_STAGE                    = {1};

    //------------------------------------CARS CONFIG------------------------------------------//
    public static final short [][] S1_CARS_VELOCITIES     = {{80},  {120}, {50},  {120}, {140}}; //Stage 1 parameters, cars in line 1, cars in line 2...
    public static final short [][] S2_CARS_VELOCITIES     = {{100}, {140}, {80},  {180}, {160}}; //Stage 2 parameters
    public static final short [][] S3_CARS_VELOCITIES     = {{120}, {160}, {110}, {200}, {180}}; //Stage 3 parameters
    public static final short [][] S4_CARS_VELOCITIES     = {{140}, {180}, {130}, {240}, {200}}; //Stage 4 parameters
    public static final short [][] S5_CARS_VELOCITIES     = {{160}, {200}, {170}, {250}, {220}}; //Stage 5 parameters
    public static final short [][] S6_CARS_VELOCITIES     = {{180}, {240}, {180}, {300}, {280}}; //Stage 6 parameters
    public static final short [][] S7_CARS_VELOCITIES     = {{180}, {240}, {200}, {300}, {280}}; //Stage 7 parameters
    public static final short [][] S8_CARS_VELOCITIES     = {{180}, {250}, {200}, {300}, {280}}; //Stage 8 parameters
    public static final short [][] S9_CARS_VELOCITIES     = {{190}, {250}, {200}, {300}, {280}}; //Stage 9 parameters
    public static final short [][] S10_CARS_VELOCITIES    = {{200}, {250}, {220}, {300}, {280}}; //Stage 10 parameters

    public static final int [] CURRENT_STAGE_CARS         = {0, 12, 13, 13, 14, 17, 21, 24, 24, 24, 26}; //Stages 0, 1, 2, 3, 4, 5, 6...
    public static final int [][] S1_CARS_POSITION_X       = {{300_000, 650_000, 1_000_000}, {100_000, 400_000, 700_000}, {-100_000, 400_000, 900_000}, {100_000}, {100_000, 750_000}};
    public static final int [][] S2_CARS_POSITION_X       = {{300_000, 700_000, 1_100_000}, {0_000, 400_000, 800_000}, {0_000, 400_000, 800_000}, {000_000, 750_000}, {300_000, 1_100_000}};
    public static final int [][] S3_CARS_POSITION_X       = {{300_000, 700_000, 1_100_000}, {0_000, 400_000, 800_000}, {0_000, 400_000, 800_000}, {000_000, 750_000}, {300_000, 1_100_000}};
    public static final int [][] S4_CARS_POSITION_X       = {{300_000, 700_000, 1_100_000}, {0_000, 400_000, 800_000}, {0_000, 400_000, 800_000}, {000_000, 500_000, 1_000_000}, {300_000, 1_100_000}};
    public static final int [][] S5_CARS_POSITION_X       = {{200_000, 450_000, 700_000, 950_000}, {0_000, 300_000, 600_000, 900_000}, {0_000, 400_000, 800_000}, {0_000, 500_000, 1_000_000}, {200_000, 650_000, 1_100_000}};
    public static final int [][] S6_CARS_POSITION_X       = {{250_000, 500_000, 750_000, 1_000_000}, {120_000, 340_000, 560_000, 780_000, 1_000_000, 1_220_000}, {0_000, 280_000, 560_000, 840_000}, {100_000, 400_000, 700_000, 1_000_000}, {100_000, 500_000, 900_000}};
    public static final int [][] S7_CARS_POSITION_X       = {{250_000, 500_000, 750_000, 1_000_000, 1_250_000}, {120_000, 340_000, 560_000, 780_000, 1_000_000, 1_220_000, 1_440_000}, {0_000, 280_000, 560_000, 840_000, 1_120_000}, {100_000, 400_000, 700_000, 1_000_000}, {100_000, 500_000, 900_000}};
    public static final int [][] S8_CARS_POSITION_X       = {{250_000, 500_000, 750_000, 1_000_000, 1_250_000}, {120_000, 340_000, 560_000, 780_000, 1_000_000, 1_220_000, 1_440_000}, {0_000, 280_000, 560_000, 840_000, 1_120_000}, {100_000, 400_000, 700_000, 1_000_000}, {100_000, 500_000, 900_000}};
    public static final int [][] S9_CARS_POSITION_X       = {{250_000, 500_000, 750_000, 1_000_000, 1_250_000}, {120_000, 340_000, 560_000, 780_000, 1_000_000, 1_220_000, 1_440_000}, {0_000, 280_000, 560_000, 840_000, 1_120_000}, {100_000, 400_000, 700_000, 1_000_000}, {100_000, 500_000, 900_000}};
    public static final int [][] S10_CARS_POSITION_X      = {{0_000, 200_000, 400_000, 600_000, 800_000, 1_000_000}, {120_000, 340_000, 560_000, 780_000, 1_000_000, 1_220_000, 1_440_000}, {0_000, 280_000, 560_000, 840_000, 1_120_000}, {100_000, 300_000, 500_000, 700_000, 900_000}, {100_000, 500_000, 900_000}};

    //for each lane, each car parameter:
    //direction, velocity, type, then for each car: start-position-x
    public static int [][][][] CARS                       =  { 
                                                              {
                                                              }, //stage 0
                                                              { { {LEFT},  {S1_CARS_VELOCITIES[4][0]}, {4}, { S1_CARS_POSITION_X[4][0], S1_CARS_POSITION_X[4][1] }},
                                                                { {RIGHT}, {S1_CARS_VELOCITIES[3][0]}, {3}, { S1_CARS_POSITION_X[3][0] }},
                                                                { {LEFT},  {S1_CARS_VELOCITIES[2][0]}, {2}, { S1_CARS_POSITION_X[2][0], S1_CARS_POSITION_X[2][1], S1_CARS_POSITION_X[2][2] } }, 
                                                                { {RIGHT}, {S1_CARS_VELOCITIES[1][0]}, {1}, { S1_CARS_POSITION_X[1][0], S1_CARS_POSITION_X[1][1], S1_CARS_POSITION_X[1][2] } },
                                                                { {LEFT},  {S1_CARS_VELOCITIES[0][0]}, {0}, { S1_CARS_POSITION_X[0][0], S1_CARS_POSITION_X[0][1], S1_CARS_POSITION_X[0][2] } } 
                                                              }, //stage 1
                                                              { { {LEFT},  {S2_CARS_VELOCITIES[4][0]}, {4}, { S2_CARS_POSITION_X[4][0], S2_CARS_POSITION_X[4][1] } },
                                                                { {RIGHT}, {S2_CARS_VELOCITIES[3][0]}, {3}, { S2_CARS_POSITION_X[3][0], S2_CARS_POSITION_X[3][1] } },
                                                                { {LEFT},  {S2_CARS_VELOCITIES[2][0]}, {2}, { S2_CARS_POSITION_X[2][0], S2_CARS_POSITION_X[2][1], S2_CARS_POSITION_X[2][2] } }, 
                                                                { {RIGHT}, {S2_CARS_VELOCITIES[1][0]}, {1}, { S2_CARS_POSITION_X[1][0], S2_CARS_POSITION_X[1][1], S2_CARS_POSITION_X[1][2] } },
                                                                { {LEFT},  {S2_CARS_VELOCITIES[0][0]}, {0}, { S2_CARS_POSITION_X[0][0], S2_CARS_POSITION_X[0][1], S2_CARS_POSITION_X[0][2] } } 
                                                              }, //stage 2
                                                              {
                                                                { {LEFT},  {S3_CARS_VELOCITIES[4][0]}, {4}, { S3_CARS_POSITION_X[4][0], S3_CARS_POSITION_X[4][1] } },
                                                                { {RIGHT}, {S3_CARS_VELOCITIES[3][0]}, {3}, { S3_CARS_POSITION_X[3][0], S3_CARS_POSITION_X[3][1] } },
                                                                { {LEFT},  {S3_CARS_VELOCITIES[2][0]}, {2}, { S3_CARS_POSITION_X[2][0], S3_CARS_POSITION_X[2][1], S3_CARS_POSITION_X[2][2] } }, 
                                                                { {RIGHT}, {S3_CARS_VELOCITIES[1][0]}, {1}, { S3_CARS_POSITION_X[1][0], S3_CARS_POSITION_X[1][1], S3_CARS_POSITION_X[1][2] } },
                                                                { {LEFT},  {S3_CARS_VELOCITIES[0][0]}, {0}, { S3_CARS_POSITION_X[0][0], S3_CARS_POSITION_X[0][1], S3_CARS_POSITION_X[0][2] } } 
                                                              }, //stage 3
                                                              {
                                                                { {LEFT},  {S4_CARS_VELOCITIES[4][0]}, {4}, { S4_CARS_POSITION_X[4][0], S4_CARS_POSITION_X[4][1] } },
                                                                { {RIGHT}, {S4_CARS_VELOCITIES[3][0]}, {3}, { S4_CARS_POSITION_X[3][0], S4_CARS_POSITION_X[3][1], S4_CARS_POSITION_X[3][2] } },
                                                                { {LEFT},  {S4_CARS_VELOCITIES[2][0]}, {2}, { S4_CARS_POSITION_X[2][0], S4_CARS_POSITION_X[2][1], S4_CARS_POSITION_X[2][2] } }, 
                                                                { {RIGHT}, {S4_CARS_VELOCITIES[1][0]}, {1}, { S4_CARS_POSITION_X[1][0], S4_CARS_POSITION_X[1][1], S4_CARS_POSITION_X[1][2] } },
                                                                { {LEFT},  {S4_CARS_VELOCITIES[0][0]}, {0}, { S4_CARS_POSITION_X[0][0], S4_CARS_POSITION_X[0][1], S4_CARS_POSITION_X[0][2] } } 
                                                              }, //stage 4
                                                              {
                                                                { {LEFT},  {S5_CARS_VELOCITIES[4][0]}, {4}, { S5_CARS_POSITION_X[4][0], S5_CARS_POSITION_X[4][1], S5_CARS_POSITION_X[4][2] } },
                                                                { {RIGHT}, {S5_CARS_VELOCITIES[3][0]}, {3}, { S5_CARS_POSITION_X[3][0], S5_CARS_POSITION_X[3][1], S5_CARS_POSITION_X[3][2] } },
                                                                { {LEFT},  {S5_CARS_VELOCITIES[2][0]}, {2}, { S5_CARS_POSITION_X[2][0], S5_CARS_POSITION_X[2][1], S5_CARS_POSITION_X[2][2] } }, 
                                                                { {RIGHT}, {S5_CARS_VELOCITIES[1][0]}, {1}, { S5_CARS_POSITION_X[1][0], S5_CARS_POSITION_X[1][1], S5_CARS_POSITION_X[1][2], S5_CARS_POSITION_X[1][3] } },
                                                                { {LEFT},  {S5_CARS_VELOCITIES[0][0]}, {0}, { S5_CARS_POSITION_X[0][0], S5_CARS_POSITION_X[0][1], S5_CARS_POSITION_X[0][2], S5_CARS_POSITION_X[0][3] } } 
                                                              }, //stage 5
                                                              { { {LEFT},  {S6_CARS_VELOCITIES[4][0]}, {4}, {S6_CARS_POSITION_X[4][0], S6_CARS_POSITION_X[4][1], S6_CARS_POSITION_X[4][2] }},
                                                                { {RIGHT}, {S6_CARS_VELOCITIES[3][0]}, {3}, {S6_CARS_POSITION_X[3][0], S6_CARS_POSITION_X[3][1], S6_CARS_POSITION_X[3][2], S6_CARS_POSITION_X[3][3]}},
                                                                { {LEFT},  {S6_CARS_VELOCITIES[2][0]}, {2}, {S6_CARS_POSITION_X[2][0], S6_CARS_POSITION_X[2][1], S6_CARS_POSITION_X[2][2], S6_CARS_POSITION_X[2][3]} }, 
                                                                { {RIGHT}, {S6_CARS_VELOCITIES[1][0]}, {1}, {S6_CARS_POSITION_X[1][0], S6_CARS_POSITION_X[1][1], S6_CARS_POSITION_X[1][2], S6_CARS_POSITION_X[1][3], S6_CARS_POSITION_X[1][4], S6_CARS_POSITION_X[1][5]} },
                                                                { {LEFT},  {S6_CARS_VELOCITIES[0][0]}, {0}, {S6_CARS_POSITION_X[0][0], S6_CARS_POSITION_X[0][1], S6_CARS_POSITION_X[0][2], S6_CARS_POSITION_X[0][3]} } 
                                                              }, //stage 6
                                                              { { {LEFT},  {S7_CARS_VELOCITIES[4][0]}, {4}, {S7_CARS_POSITION_X[4][0], S7_CARS_POSITION_X[4][1], S7_CARS_POSITION_X[4][2] }},
                                                                { {RIGHT}, {S7_CARS_VELOCITIES[3][0]}, {3}, {S7_CARS_POSITION_X[3][0], S7_CARS_POSITION_X[3][1], S7_CARS_POSITION_X[3][2], S7_CARS_POSITION_X[3][3]}},
                                                                { {LEFT},  {S7_CARS_VELOCITIES[2][0]}, {2}, {S7_CARS_POSITION_X[2][0], S7_CARS_POSITION_X[2][1], S7_CARS_POSITION_X[2][2], S7_CARS_POSITION_X[2][3], S7_CARS_POSITION_X[2][4]} }, 
                                                                { {RIGHT}, {S7_CARS_VELOCITIES[1][0]}, {1}, {S7_CARS_POSITION_X[1][0], S7_CARS_POSITION_X[1][1], S7_CARS_POSITION_X[1][2], S7_CARS_POSITION_X[1][3], S7_CARS_POSITION_X[1][4], S7_CARS_POSITION_X[1][5], S7_CARS_POSITION_X[1][6]} },
                                                                { {LEFT},  {S7_CARS_VELOCITIES[0][0]}, {0}, {S7_CARS_POSITION_X[0][0], S7_CARS_POSITION_X[0][1], S7_CARS_POSITION_X[0][2], S7_CARS_POSITION_X[0][3], S7_CARS_POSITION_X[0][4]} } 
                                                              }, //stage 7
                                                              { { {LEFT},  {S8_CARS_VELOCITIES[4][0]}, {4}, {S8_CARS_POSITION_X[4][0], S8_CARS_POSITION_X[4][1], S8_CARS_POSITION_X[4][2] }},
                                                                { {RIGHT}, {S8_CARS_VELOCITIES[3][0]}, {3}, {S8_CARS_POSITION_X[3][0], S8_CARS_POSITION_X[3][1], S8_CARS_POSITION_X[3][2], S8_CARS_POSITION_X[3][3]}},
                                                                { {LEFT},  {S8_CARS_VELOCITIES[2][0]}, {2}, {S8_CARS_POSITION_X[2][0], S8_CARS_POSITION_X[2][1], S8_CARS_POSITION_X[2][2], S8_CARS_POSITION_X[2][3], S8_CARS_POSITION_X[2][4]} }, 
                                                                { {RIGHT}, {S8_CARS_VELOCITIES[1][0]}, {1}, {S8_CARS_POSITION_X[1][0], S8_CARS_POSITION_X[1][1], S8_CARS_POSITION_X[1][2], S8_CARS_POSITION_X[1][3], S8_CARS_POSITION_X[1][4], S8_CARS_POSITION_X[1][5], S8_CARS_POSITION_X[1][6]} },
                                                                { {LEFT},  {S8_CARS_VELOCITIES[0][0]}, {0}, {S8_CARS_POSITION_X[0][0], S8_CARS_POSITION_X[0][1], S8_CARS_POSITION_X[0][2], S8_CARS_POSITION_X[0][3], S8_CARS_POSITION_X[0][4]} } 
                                                              }, //stage 8
                                                              { { {LEFT},  {S9_CARS_VELOCITIES[4][0]}, {4}, {S9_CARS_POSITION_X[4][0], S9_CARS_POSITION_X[4][1], S9_CARS_POSITION_X[4][2] }},
                                                                { {RIGHT}, {S9_CARS_VELOCITIES[3][0]}, {3}, {S9_CARS_POSITION_X[3][0], S9_CARS_POSITION_X[3][1], S9_CARS_POSITION_X[3][2], S9_CARS_POSITION_X[3][3]}},
                                                                { {LEFT},  {S9_CARS_VELOCITIES[2][0]}, {2}, {S9_CARS_POSITION_X[2][0], S9_CARS_POSITION_X[2][1], S9_CARS_POSITION_X[2][2], S9_CARS_POSITION_X[2][3], S9_CARS_POSITION_X[2][4]} }, 
                                                                { {RIGHT}, {S9_CARS_VELOCITIES[1][0]}, {1}, {S9_CARS_POSITION_X[1][0], S9_CARS_POSITION_X[1][1], S9_CARS_POSITION_X[1][2], S9_CARS_POSITION_X[1][3], S9_CARS_POSITION_X[1][4], S9_CARS_POSITION_X[1][5], S9_CARS_POSITION_X[1][6]} },
                                                                { {LEFT},  {S9_CARS_VELOCITIES[0][0]}, {0}, {S9_CARS_POSITION_X[0][0], S9_CARS_POSITION_X[0][1], S9_CARS_POSITION_X[0][2], S9_CARS_POSITION_X[0][3], S9_CARS_POSITION_X[0][4]} } 
                                                              }, //stage 9
                                                              { { {LEFT},  {S10_CARS_VELOCITIES[4][0]}, {4}, {S10_CARS_POSITION_X[4][0], S10_CARS_POSITION_X[4][1], S10_CARS_POSITION_X[4][2] }},
                                                                { {RIGHT}, {S10_CARS_VELOCITIES[3][0]}, {3}, {S10_CARS_POSITION_X[3][0], S10_CARS_POSITION_X[3][1], S10_CARS_POSITION_X[3][2], S10_CARS_POSITION_X[3][3], S10_CARS_POSITION_X[3][4]}},
                                                                { {LEFT},  {S10_CARS_VELOCITIES[2][0]}, {2}, {S10_CARS_POSITION_X[2][0], S10_CARS_POSITION_X[2][1], S10_CARS_POSITION_X[2][2], S10_CARS_POSITION_X[2][3], S10_CARS_POSITION_X[2][4]} }, 
                                                                { {RIGHT}, {S10_CARS_VELOCITIES[1][0]}, {1}, {S10_CARS_POSITION_X[1][0], S10_CARS_POSITION_X[1][1], S10_CARS_POSITION_X[1][2], S10_CARS_POSITION_X[1][3], S10_CARS_POSITION_X[1][4], S10_CARS_POSITION_X[1][5], S10_CARS_POSITION_X[1][6]} },
                                                                { {LEFT},  {S10_CARS_VELOCITIES[0][0]}, {0}, {S10_CARS_POSITION_X[0][0], S10_CARS_POSITION_X[0][1], S10_CARS_POSITION_X[0][2], S10_CARS_POSITION_X[0][3], S10_CARS_POSITION_X[0][4], S10_CARS_POSITION_X[0][5]} } 
                                                              }, //stage 10
                                                            };


    //------------------------------------TRUNKS CONFIG------------------------------------------//
    public static final int [] CURRENT_STAGE_TRUNKS       = {0, 9, 8, 8, 7, 7, 7, 6, 6, 6, 6};
    public static final int [][] S1_TRUNKS_POSITION_X     = {{50_000, 350_000, 650_000}, {0_000, 450_000, 900_000}, {0_000, 400_000, 800_000}};
    public static final int [][] S2_TRUNKS_POSITION_X     = {{100_000, 450_000, 800_000}, {-200_000, 500_000}, {0_000, 400_000, 800_000}};
    public static final int [][] S3_TRUNKS_POSITION_X     = {{0_000, 350_000, 700_000}, {-100_000, 500_000}, {0_000, 400_000, 800_000}};
    public static final int [][] S4_TRUNKS_POSITION_X     = {{200_000, 800_000}, {-100_000, 500_000}, {0_000, 400_000, 800_000}};
    public static final int [][] S5_TRUNKS_POSITION_X     = {{200_000, 800_000}, {-100_000, 500_000}, {0_000, 400_000, 800_000}};
    public static final int [][] S6_TRUNKS_POSITION_X     = {{200_000, 800_000}, {-100_000, 500_000}, {0_000, 300_000, 700_000}};
    public static final int [][] S7_TRUNKS_POSITION_X     = {{200_000, 800_000}, {-100_000, 500_000}, {0_000, 700_000}};
    public static final int [][] S8_TRUNKS_POSITION_X     = {{200_000, 800_000}, {-100_000, 500_000}, {0_000, 700_000}};
    public static final int [][] S9_TRUNKS_POSITION_X     = {{200_000, 800_000}, {-100_000, 500_000}, {0_000, 700_000}};
    public static final int [][] S10_TRUNKS_POSITION_X    = {{200_000, 800_000}, {-100_000, 500_000}, {0_000, 700_000}};
    
    public static final short [][] S1_TRUNKS_VELOCITIES   = {{50}, {140}, {100}};
    public static final short [][] S2_TRUNKS_VELOCITIES   = {{70}, {180}, {120}};
    public static final short [][] S3_TRUNKS_VELOCITIES   = {{70}, {180}, {150}};
    public static final short [][] S4_TRUNKS_VELOCITIES   = {{80}, {200}, {180}};
    public static final short [][] S5_TRUNKS_VELOCITIES   = {{110}, {210}, {180}};
    public static final short [][] S6_TRUNKS_VELOCITIES   = {{130}, {220}, {190}};
    public static final short [][] S7_TRUNKS_VELOCITIES   = {{140}, {230}, {190}};
    public static final short [][] S8_TRUNKS_VELOCITIES   = {{150}, {240}, {200}};
    public static final short [][] S9_TRUNKS_VELOCITIES   = {{160}, {250}, {210}};
    public static final short [][] S10_TRUNKS_VELOCITIES   = {{180}, {260}, {210}};

    public static int [][][][] TRUNKS                     =  { 
                                                              {
                                                              }, //stage 0
                                                              { { {RIGHT}, {S1_TRUNKS_VELOCITIES[2][0]}, {1, 1, 1}, { S1_TRUNKS_POSITION_X[2][0], S1_TRUNKS_POSITION_X[2][1], S1_TRUNKS_POSITION_X[2][2] }},
                                                                {},
                                                                { {RIGHT}, {S1_TRUNKS_VELOCITIES[1][0]}, {2, 2, 2}, { S1_TRUNKS_POSITION_X[1][0], S1_TRUNKS_POSITION_X[1][1], S1_TRUNKS_POSITION_X[1][2] }}, 
                                                                { {RIGHT}, {S1_TRUNKS_VELOCITIES[0][0]}, {0, 0, 0}, { S1_TRUNKS_POSITION_X[0][0], S1_TRUNKS_POSITION_X[0][1], S1_TRUNKS_POSITION_X[0][2] }},
                                                                {}
                                                              }, //stage 1
                                                              { { {RIGHT}, {S2_TRUNKS_VELOCITIES[2][0]}, {1, 1, 1}, { S2_TRUNKS_POSITION_X[2][0], S2_TRUNKS_POSITION_X[2][1], S2_TRUNKS_POSITION_X[2][2] }},
                                                                {},
                                                                { {RIGHT}, {S2_TRUNKS_VELOCITIES[1][0]}, {2, 2}, { S2_TRUNKS_POSITION_X[1][0], S2_TRUNKS_POSITION_X[1][1] }}, 
                                                                { {RIGHT}, {S2_TRUNKS_VELOCITIES[0][0]}, {0, 0, 0}, { S2_TRUNKS_POSITION_X[0][0], S2_TRUNKS_POSITION_X[0][1], S2_TRUNKS_POSITION_X[0][2] }},
                                                                {}
                                                              }, //stage 2 
                                                              {
                                                                { {RIGHT}, {S3_TRUNKS_VELOCITIES[2][0]}, {1, 1, 1}, { S3_TRUNKS_POSITION_X[2][0], S3_TRUNKS_POSITION_X[2][1], S3_TRUNKS_POSITION_X[2][2] }},
                                                                {},
                                                                { {RIGHT}, {S3_TRUNKS_VELOCITIES[1][0]}, {2, 2},    { S3_TRUNKS_POSITION_X[1][0], S3_TRUNKS_POSITION_X[1][1] }}, 
                                                                { {RIGHT}, {S3_TRUNKS_VELOCITIES[0][0]}, {0, 0, 0}, { S3_TRUNKS_POSITION_X[0][0], S3_TRUNKS_POSITION_X[0][1], S3_TRUNKS_POSITION_X[0][2] }},
                                                                {}
                                                              }, //stage 3
                                                              {
                                                                { {RIGHT}, {S4_TRUNKS_VELOCITIES[2][0]}, {1, 1, 1}, { S4_TRUNKS_POSITION_X[2][0], S4_TRUNKS_POSITION_X[2][1], S4_TRUNKS_POSITION_X[2][2] }},
                                                                {},
                                                                { {RIGHT}, {S4_TRUNKS_VELOCITIES[1][0]}, {2, 2},    { S4_TRUNKS_POSITION_X[1][0], S4_TRUNKS_POSITION_X[1][1] }}, 
                                                                { {RIGHT}, {S4_TRUNKS_VELOCITIES[0][0]}, {0, 0},    { S4_TRUNKS_POSITION_X[0][0], S4_TRUNKS_POSITION_X[0][1] }},
                                                                {}
                                                              }, //stage 4 
                                                              {
                                                                { {RIGHT}, {S5_TRUNKS_VELOCITIES[2][0]}, {1, 1, 1}, { S5_TRUNKS_POSITION_X[2][0], S5_TRUNKS_POSITION_X[2][1], S5_TRUNKS_POSITION_X[2][2] }},
                                                                {},
                                                                { {RIGHT}, {S5_TRUNKS_VELOCITIES[1][0]}, {2, 2},    { S5_TRUNKS_POSITION_X[1][0], S5_TRUNKS_POSITION_X[1][1] }}, 
                                                                { {RIGHT}, {S5_TRUNKS_VELOCITIES[0][0]}, {0, 0},    { S5_TRUNKS_POSITION_X[0][0], S5_TRUNKS_POSITION_X[0][1] }},
                                                                {}
                                                              }, //stage 5
                                                              {
                                                                { {RIGHT}, {S6_TRUNKS_VELOCITIES[2][0]}, {1, 1, 3}, { S6_TRUNKS_POSITION_X[2][0], S6_TRUNKS_POSITION_X[2][1], S6_TRUNKS_POSITION_X[2][2] }},
                                                                {},
                                                                { {RIGHT}, {S6_TRUNKS_VELOCITIES[1][0]}, {2, 2},    { S6_TRUNKS_POSITION_X[1][0], S6_TRUNKS_POSITION_X[1][1] }}, 
                                                                { {RIGHT}, {S6_TRUNKS_VELOCITIES[0][0]}, {0, 0},    { S6_TRUNKS_POSITION_X[0][0], S6_TRUNKS_POSITION_X[0][1] }},
                                                                {}
                                                              }, //stage 6
                                                              {
                                                                { {RIGHT}, {S7_TRUNKS_VELOCITIES[2][0]}, {1, 3},    { S7_TRUNKS_POSITION_X[2][0], S7_TRUNKS_POSITION_X[2][1] }},
                                                                {},
                                                                { {RIGHT}, {S7_TRUNKS_VELOCITIES[1][0]}, {2, 2},    { S7_TRUNKS_POSITION_X[1][0], S7_TRUNKS_POSITION_X[1][1] }}, 
                                                                { {RIGHT}, {S7_TRUNKS_VELOCITIES[0][0]}, {0, 0},    { S7_TRUNKS_POSITION_X[0][0], S7_TRUNKS_POSITION_X[0][1] }},
                                                                {}
                                                              }, //stage 7
                                                              {
                                                                { {RIGHT}, {S8_TRUNKS_VELOCITIES[2][0]}, {1, 3},    { S8_TRUNKS_POSITION_X[2][0], S8_TRUNKS_POSITION_X[2][1] }},
                                                                {},
                                                                { {RIGHT}, {S8_TRUNKS_VELOCITIES[1][0]}, {1, 1},    { S8_TRUNKS_POSITION_X[1][0], S8_TRUNKS_POSITION_X[1][1] }}, 
                                                                { {RIGHT}, {S8_TRUNKS_VELOCITIES[0][0]}, {2, 2},    { S8_TRUNKS_POSITION_X[0][0], S8_TRUNKS_POSITION_X[0][1] }},
                                                                {}
                                                              }, //stage 8
                                                              {
                                                                { {RIGHT}, {S9_TRUNKS_VELOCITIES[2][0]}, {1, 3},    { S9_TRUNKS_POSITION_X[2][0], S9_TRUNKS_POSITION_X[2][1] }},
                                                                {},
                                                                { {RIGHT}, {S9_TRUNKS_VELOCITIES[1][0]}, {1, 1},    { S9_TRUNKS_POSITION_X[1][0], S9_TRUNKS_POSITION_X[1][1] }}, 
                                                                { {RIGHT}, {S9_TRUNKS_VELOCITIES[0][0]}, {2, 2},    { S9_TRUNKS_POSITION_X[0][0], S9_TRUNKS_POSITION_X[0][1] }},
                                                                {}
                                                              }, //stage 9
                                                              {
                                                                { {RIGHT}, {S10_TRUNKS_VELOCITIES[2][0]}, {3, 3},    { S10_TRUNKS_POSITION_X[2][0], S10_TRUNKS_POSITION_X[2][1] }},
                                                                {},
                                                                { {RIGHT}, {S10_TRUNKS_VELOCITIES[1][0]}, {1, 1},    { S10_TRUNKS_POSITION_X[1][0], S10_TRUNKS_POSITION_X[1][1] }}, 
                                                                { {RIGHT}, {S10_TRUNKS_VELOCITIES[0][0]}, {0, 0},    { S10_TRUNKS_POSITION_X[0][0], S10_TRUNKS_POSITION_X[0][1] }},
                                                                {}
                                                              } //stage 10
                                                            };

    //------------------------------------TURTLES CONFIG------------------------------------------//
    public static final int [] CURRENT_STAGE_TURTLES      = {0, 8, 8, 7, 7, 7, 6, 6, 6, 6, 6};
    public static final int [][] S1_TURTLES_POSITION_X    = {{50_000, 390_000, 730_000, 1_070_000}, {0_000, 340_000, 680_000, 1_020_000}};
    public static final int [][] S2_TURTLES_POSITION_X    = {{50_000, 390_000, 730_000, 1_070_000}, {0_000, 320_000, 640_000, 960_000}};
    public static final int [][] S3_TURTLES_POSITION_X    = {{150_000, 550_000, 950_000}, {0_000, 320_000, 640_000, 960_000}};
    public static final int [][] S4_TURTLES_POSITION_X    = {{150_000, 550_000, 950_000}, {0_000, 320_000, 640_000, 960_000}};
    public static final int [][] S5_TURTLES_POSITION_X    = {{0_000, 450_000, 900_000}, {100_000, 400_000, 700_000, 1_000_000}};
    public static final int [][] S6_TURTLES_POSITION_X    = {{0_000, 450_000, 900_000}, {200_000, 600_000, 1_000_000}};
    public static final int [][] S7_TURTLES_POSITION_X    = {{0_000, 450_000, 900_000}, {200_000, 600_000, 1_000_000}};
    public static final int [][] S8_TURTLES_POSITION_X    = {{0_000, 450_000, 900_000}, {200_000, 600_000, 1_000_000}};
    public static final int [][] S9_TURTLES_POSITION_X    = {{0_000, 450_000, 900_000}, {200_000, 600_000, 1_000_000}};
    public static final int [][] S10_TURTLES_POSITION_X    = {{0_000, 450_000, 900_000}, {200_000, 600_000, 1_000_000}};
    
    public static final short [][] S1_TURTLES_VELOCITIES  = {{80}, {50}};
    public static final short [][] S2_TURTLES_VELOCITIES  = {{120}, {70}};
    public static final short [][] S3_TURTLES_VELOCITIES  = {{120}, {80}};
    public static final short [][] S4_TURTLES_VELOCITIES  = {{140}, {100}};
    public static final short [][] S5_TURTLES_VELOCITIES  = {{140}, {120}};
    public static final short [][] S6_TURTLES_VELOCITIES  = {{150}, {130}};
    public static final short [][] S7_TURTLES_VELOCITIES  = {{160}, {130}};
    public static final short [][] S8_TURTLES_VELOCITIES  = {{160}, {140}};
    public static final short [][] S9_TURTLES_VELOCITIES  = {{160}, {150}};
    public static final short [][] S10_TURTLES_VELOCITIES = {{160}, {160}};

    //for each lane, each turtle (set) parameter:
    //direction, velocity, type, diver, then for each trunk: start-position-x
    public static int [][][][] TURTLES                    =  { {
                                                               }, //stage 0
                                                               { {},
                                                                { {LEFT}, {S1_TURTLES_VELOCITIES[1][0]}, {1}, {0, 0, 0, 0}, { S1_TURTLES_POSITION_X[1][0], S1_TURTLES_POSITION_X[1][1], S1_TURTLES_POSITION_X[1][2], S1_TURTLES_POSITION_X[1][3] }},
                                                                {}, 
                                                                {},
                                                                { {LEFT}, {S1_TURTLES_VELOCITIES[0][0]}, {1}, {0, 0, 0, 0}, { S1_TURTLES_POSITION_X[0][0], S1_TURTLES_POSITION_X[0][1], S1_TURTLES_POSITION_X[0][2], S1_TURTLES_POSITION_X[0][3] }} 
                                                               }, //stage 1
                                                               { {},
                                                                { {LEFT}, {S2_TURTLES_VELOCITIES[1][0]}, {0}, {1, 0, 0, 0}, { S2_TURTLES_POSITION_X[1][0], S2_TURTLES_POSITION_X[1][1], S2_TURTLES_POSITION_X[1][2], S2_TURTLES_POSITION_X[1][3] }},
                                                                {}, 
                                                                {},
                                                                { {LEFT}, {S2_TURTLES_VELOCITIES[0][0]}, {1}, {1, 0, 0, 0}, { S2_TURTLES_POSITION_X[0][0], S2_TURTLES_POSITION_X[0][1], S2_TURTLES_POSITION_X[0][2], S2_TURTLES_POSITION_X[0][3] }} 
                                                               }, //stage 2
                                                               {
                                                                {},
                                                                { {LEFT}, {S3_TURTLES_VELOCITIES[1][0]}, {0}, {1, 0, 0, 1}, { S3_TURTLES_POSITION_X[1][0], S3_TURTLES_POSITION_X[1][1], S3_TURTLES_POSITION_X[1][2], S3_TURTLES_POSITION_X[1][3] }},
                                                                {}, 
                                                                {},
                                                                { {LEFT}, {S3_TURTLES_VELOCITIES[0][0]}, {1}, {1, 0, 0},    { S3_TURTLES_POSITION_X[0][0], S3_TURTLES_POSITION_X[0][1], S3_TURTLES_POSITION_X[0][2] }} 
                                                               }, //stage 3
                                                               {
                                                                {},
                                                                { {LEFT}, {S4_TURTLES_VELOCITIES[1][0]}, {0}, {1, 0, 0, 1}, { S4_TURTLES_POSITION_X[1][0], S4_TURTLES_POSITION_X[1][1], S4_TURTLES_POSITION_X[1][2], S4_TURTLES_POSITION_X[1][3] }},
                                                                {}, 
                                                                {},
                                                                { {LEFT}, {S4_TURTLES_VELOCITIES[0][0]}, {1}, {0, 1, 0},    { S4_TURTLES_POSITION_X[0][0], S4_TURTLES_POSITION_X[0][1], S4_TURTLES_POSITION_X[0][2] }} 
                                                               }, //stage 4
                                                               {
                                                                {},
                                                                { {LEFT}, {S5_TURTLES_VELOCITIES[1][0]}, {0}, {1, 0, 0, 1}, { S5_TURTLES_POSITION_X[1][0], S5_TURTLES_POSITION_X[1][1], S5_TURTLES_POSITION_X[1][2], S5_TURTLES_POSITION_X[1][3] }},
                                                                {}, 
                                                                {},
                                                                { {LEFT}, {S5_TURTLES_VELOCITIES[0][0]}, {0}, {0, 1, 1},    { S5_TURTLES_POSITION_X[0][0], S5_TURTLES_POSITION_X[0][1], S5_TURTLES_POSITION_X[0][2] }} 
                                                               }, //stage 5
                                                               {
                                                                {},
                                                                { {LEFT}, {S6_TURTLES_VELOCITIES[1][0]}, {0}, {1, 0, 1},    { S6_TURTLES_POSITION_X[1][0], S6_TURTLES_POSITION_X[1][1], S6_TURTLES_POSITION_X[1][2] }},
                                                                {}, 
                                                                {},
                                                                { {LEFT}, {S6_TURTLES_VELOCITIES[0][0]}, {0}, {0, 1, 1},    { S6_TURTLES_POSITION_X[0][0], S6_TURTLES_POSITION_X[0][1], S6_TURTLES_POSITION_X[0][2] }} 
                                                               }, //stage 6
                                                               {
                                                                {},
                                                                { {LEFT}, {S7_TURTLES_VELOCITIES[1][0]}, {0}, {1, 0, 1},    { S7_TURTLES_POSITION_X[1][0], S7_TURTLES_POSITION_X[1][1], S7_TURTLES_POSITION_X[1][2] }},
                                                                {}, 
                                                                {},
                                                                { {LEFT}, {S7_TURTLES_VELOCITIES[0][0]}, {0}, {0, 1, 1},    { S7_TURTLES_POSITION_X[0][0], S7_TURTLES_POSITION_X[0][1], S7_TURTLES_POSITION_X[0][2] }} 
                                                               }, //stage 7
                                                               {
                                                                {},
                                                                { {LEFT}, {S8_TURTLES_VELOCITIES[1][0]}, {0}, {1, 0, 1},    { S8_TURTLES_POSITION_X[1][0], S8_TURTLES_POSITION_X[1][1], S8_TURTLES_POSITION_X[1][2] }},
                                                                {}, 
                                                                {},
                                                                { {LEFT}, {S8_TURTLES_VELOCITIES[0][0]}, {0}, {0, 1, 1},    { S8_TURTLES_POSITION_X[0][0], S8_TURTLES_POSITION_X[0][1], S8_TURTLES_POSITION_X[0][2] }} 
                                                               }, //stage 8
                                                               {
                                                                {},
                                                                { {LEFT}, {S9_TURTLES_VELOCITIES[1][0]}, {0}, {1, 0, 1},    { S9_TURTLES_POSITION_X[1][0], S9_TURTLES_POSITION_X[1][1], S9_TURTLES_POSITION_X[1][2] }},
                                                                {}, 
                                                                {},
                                                                { {LEFT}, {S9_TURTLES_VELOCITIES[0][0]}, {0}, {0, 1, 1},    { S9_TURTLES_POSITION_X[0][0], S9_TURTLES_POSITION_X[0][1], S9_TURTLES_POSITION_X[0][2] }} 
                                                               }, //stage 9
                                                               {
                                                                {},
                                                                { {LEFT}, {S10_TURTLES_VELOCITIES[1][0]}, {0}, {1, 0, 1},    { S10_TURTLES_POSITION_X[1][0], S10_TURTLES_POSITION_X[1][1], S10_TURTLES_POSITION_X[1][2] }},
                                                                {}, 
                                                                {},
                                                                { {LEFT}, {S10_TURTLES_VELOCITIES[0][0]}, {0}, {0, 1, 1},    { S10_TURTLES_POSITION_X[0][0], S10_TURTLES_POSITION_X[0][1], S10_TURTLES_POSITION_X[0][2] }} 
                                                               }, //stage 10
                                                             };

    //gator head (time to appearence & duration) - ignore item 0
    //same for the mosquito
    public static final int [][] GATOR_HEAD_CONFIG        = {{}, {-1, -1}, {-1, -1}, {6, 5}, {5, 5}, {5, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {3, 5}};
    public static final int [][] MOSQUITO_CONFIG          = {{}, {5, 5}, {5, 5}, {5, 5}, {6, 4}, {6, 4}, {6, 4}, {6, 4}, {6, 4}, {6, 4}, {6, 4}};

    //snake speed - ignore item/stage 0 - [2nd snake - max 100]
    public static final short [][] SNAKE_SPEED            = {{}, {-1, -1}, {-1, -1}, {-1, -1}, {50, -1}, {70, -1}, {70, 30}, {80, 30}, {80, 40}, {90, 40}, {90, 50}};

    //ignore (item 0 - stage 0)
    //points distribution: ROAD STEP, RIVER STEP, DOCKER IN, DOCKER COMPLETE, MOSQUITO
    public static final int [][] STAGE_POINTS             = {{}, {10, 20, 50, 100, 200}, {15, 20, 60, 120, 220}, {20, 25, 60, 120, 220}, {20, 25, 60, 150, 250}, {20, 25, 60, 150, 250}, {20, 25, 60, 150, 250}, {20, 25, 60, 150, 250}, {20, 25, 60, 150, 250}, {20, 25, 60, 150, 250}, {20, 25, 60, 150, 250}};

}