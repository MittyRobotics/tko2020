package com.github.mittyrobotics.climber;

/**
 * Constants for the {@link ClimberSubsystem} and {@link RatchetSubsystem}
 */
public class ClimberConstants {
    public static final int LEFT_SPARK_ID = 31;
    public static final int RIGHT_SPARK_ID = 32;

    public static final boolean LEFT_SPARK_INVERSION = true;
    public static final boolean RIGHT_SPARK_INVERSION = false;

    public static final int LEFT_SOLENOID_FOWARD_CHANNEL = 0;
    public static final int LEFT_SOLENOID_REVERSE_CHANNEL = 0;

    public static final int RIGHT_SOLENOID_FOWARD_CHANNEL = 0;
    public static final int RIGHT_SOLENOID_REVERSE_CHANNEL = 0;

    public static final double LEFT_UP_SETPOINT = 0;
    public static final double RIGHT_UP_SETPOINT = 0;
    public static final double LEFT_DOWN_SETPOINT = 0;
    public static final double RIGHT_DOWN_SETPOINT = 0;

    public static final double POSITION_P = 0;
    public static final double POSITION_I = 0;
    public static final double POSITION_D = 0;

    public static final double AUX_P = 0;
    public static final double AUX_I = 0;
    public static final double AUX_D = 0;

    public static final double CLIMBER_THRESHOLD = 0;
}