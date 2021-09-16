package com.github.mittyrobotics.climber;

import com.ctre.phoenix.motorcontrol.NeutralMode;

public class ClimberConstants {
    public static final int ACTUATOR_ID = 10;

    public static final double POSITION_P = 0;
    public static final double POSITION_I = 0;
    public static final double POSITION_D = 0;

    public static final NeutralMode CLIMBER_NEUTRAL_MODE = NeutralMode.Brake;


    public static final int MOTOR_ID = 1;
    public static final int CLIMBER_EXTENDED = 0;
    public static final int CLIMBER_DEEXTENDED = 0;

    public static final double CLIMBER_THRESHOLD = 1;
}