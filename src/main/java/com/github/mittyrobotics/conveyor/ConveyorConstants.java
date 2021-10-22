package com.github.mittyrobotics.conveyor;

import com.ctre.phoenix.motorcontrol.NeutralMode;


/**
 * Constants for the {@link ConveyorSubsystem}
 */
public class ConveyorConstants {
    public static final double TICKS_PER_CYCLE = 52900;
    public static final double INDEX_SPEED = -0.4;
    public static final double OUTTAKE_SPEED = 0.6;
    public static final double SHOOT_SPEED = -.8;
    public static final int CONVEYOR_TOP_ID = 41;
    public static final int CONVEYOR_BOTTOM_ID = 42;
    public static final double BUFFER_TIME = .2;
    public static final boolean CONVEYOR_TOP_INVERSION = true;
    public static final boolean CONVEYOR_BOTTOM_INVERSION = false;
    public static final boolean CONVEYOR_ENCODER_INVERSION = true;
    public static final int BALL_SENSOR_ID = 6;
    public static final NeutralMode CONVEYOR_NEUTRAL_MODE = NeutralMode.Brake;
    public static final int SHOOTER_BALL_SENSOR_ID = 9;
}
