package com.GitHub.mittyrobotics.climber;

public class Constants {
    public static final int LEFT_SPARK_ID = 0;
    public static final int RIGHT_SPARK_ID = 1;
    public static final int LEFT_PISTON_FORWARD_ID = 0;
    public static final int LEFT_PISTON_REVERSE_ID = 1;
    public static final int RIGHT_PISTON_FORWARD_ID = 2;
    public static final int RIGHT_PISTON_REVERSE_ID = 3;
    public static final double WINCH_TPI = 10;
    public static final double PISTON_DISTANCE = 5;
    public static final double[] WINCH_PID_VALUES = {0.5, 0, 0};
    public static final double PID_OUTPUT_RANGE = 0.5;
}
