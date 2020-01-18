package com.github.mittyrobotics.drive;

public class Constants {
    public static final int LEFT_SPARK_1_ID = 1;
    public static final int LEFT_SPARK_2_ID = 2;
    public static final int RIGHT_SPARK_1_ID = 3;
    public static final int RIGHT_SPARK_2_ID = 4;

    public static final double TICKS_PER_INCH = 163;

    public static final int LEFT_TALON_1 = 1;
    public static final int LEFT_TALON_2 = 0;
    public static final int RIGHT_TALON_1 = 2;
    public static final int RIGHT_TALON_2 = 3;

    public static final double[] DRIVE_VELOCITY_PID = {6, 0, 0}; //position: p val: 0.0668 i val: 0.00001 d val: 0.0315
    public static final double[] TURN = {0.1, 0, 0};

    public static final boolean LEFT_TALON_INVERSIONS[] = {false, false};
    public static final boolean RIGHT_TALON_INVERSIONS[] = {true, true};

}
