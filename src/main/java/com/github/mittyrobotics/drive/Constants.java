package com.github.mittyrobotics.drive;

public class Constants {
    public static final int LEFT_SPARK_1_ID = 1;
    public static final int LEFT_SPARK_2_ID = 2;
    public static final int RIGHT_SPARK_1_ID = 3;
    public static final int RIGHT_SPARK_2_ID = 4;

    public static final double TICKS_PER_INCH = 79.68;

    public static final int LEFT_TALON_1 = 20;
    public static final int LEFT_TALON_2 = 21;
    public static final int RIGHT_TALON_1 = 23;
    public static final int RIGHT_TALON_2 = 22;

    public static final double[] DRIVE_VELOCITY_PID = {0.2, 0, 0};
    public static final double[] TURN = {0.1, 0, 0};

    public static final boolean LEFT_TALON_INVERSIONS[] = {false, false};
    public static final boolean RIGHT_TALON_INVERSIONS[] = {true, true};

}
