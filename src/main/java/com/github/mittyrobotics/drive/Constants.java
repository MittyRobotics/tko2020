package com.github.mittyrobotics.drive;

public class Constants {
    public static final int LEFT_SPARK_1_ID = 1;
    public static final int LEFT_SPARK_2_ID = 2;
    public static final int RIGHT_SPARK_1_ID = 3;
    public static final int RIGHT_SPARK_2_ID = 4;

    public static final double TICKS_PER_INCH = 170.73611; //talon chassis
    public static final double TICKS_PER_INCH_FALCON = 642.83;

    public static final int LEFT_TALON_1 = 20;
    public static final int LEFT_TALON_2 = 21;
    public static final int RIGHT_TALON_1 = 23;
    public static final int RIGHT_TALON_2 = 22;

    public static final int LEFT_FALCON_1 = 0;
    public static final int LEFT_FALCON_2 = 1;
    public static final int RIGHT_FALCON_1 = 2;
    public static final int RIGHT_FALCON_2 = 3;



    public static final double MAX_FALCON_SPEED = 21600.0;
    public static final double MAX_TALON_SPEED = 1000.0;
    public static final double[] HIGH_SPEED_VELOCITY_PID_CONTROLLER = {0.0001, 0, 0};
    //public static final double[] LOW_SPEED_VELOCITY_PID_CONTROLLER = {0.000065, 0, 0};
    public static final double[] DRIVE_VELOCITY_PID = {2.05, 0, 0}; //position: p val: 0.0668 i val: 0.00001 d val: 0.0315
    public static final double[] TURN = {0.1, 0, 0};

    public static final boolean LEFT_TALON_INVERSIONS[] = {false, false};
    public static final boolean RIGHT_TALON_INVERSIONS[] = {true, true};

    public static final int COLORWHEELDRIVE_CURRENT = 5;

}
