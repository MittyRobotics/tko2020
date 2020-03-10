package com.github.mittyrobotics.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;

public class ConveyorConstants {
    public static final double TICKS_PER_CYCLE = 52900;
    public static final double INDEX_SPEED = 1;
    public static final double OUTTAKE_SPEED = -1;
    public static final double SHOVE_SPEED = 0.8;
    public static final double SHOOT_SPEED = 1;
    public static final int CONVEYOR_TOP_ID = 41;
    public static final int CONVEYOR_BOTTOM_ID = 42;
    public static final boolean CONVEYOR_TOP_INVERSION = true;
    public static final boolean CONVEYOR_BOTTOM_INVERSION = false;
    public static final boolean CONVEYOR_ENCODER_INVERSION = true;
    public static final int BALL_SENSOR_ID = 0;
    public static final int MINIMUM_BALL_COUNT = 0;
    public static final int MAXIMUM_BALL_COUNT = 5;
    public static final NeutralMode CONVEYOR_NEUTRAL_MODE = NeutralMode.Brake;
}
