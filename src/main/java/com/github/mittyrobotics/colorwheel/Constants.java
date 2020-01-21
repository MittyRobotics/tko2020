package com.github.mittyrobotics.colorwheel;

public class Constants {
    //channels for hardware
    public static final int SOLENOID_FOWARD_CHANNEL = 0;
    public static final int SOLENOID_REVERSE_CHANNEL = 1;

    public static final int TALON_DEVICE_NUMBER = 0;


    //ticks for one inch of the color wheel
    public static final int TICKS_PER_INCH = 300;
    //slow and fast velocity for motor
    public static final int FAST_VELOCITY = 5000;
    public static final int SLOW_VELOCITY = 2000;


    //rgb for color sensor targets (CALIBRATE THIS)
    public static final double BLUE_R = 0.162;
    public static final double BLUE_G = 0.404;
    public static final double BLUE_B = 0.333;

    public static final double GREEN_R = 0.186;
    public static final double GREEN_G = 0.483;
    public static final double GREEN_B = 0.230;

    public static final double RED_R = 0.373;
    public static final double RED_G = 0.360;
    public static final double RED_B = 0.167;

    public static final double YELLOW_R = 0.285;
    public static final double YELLOW_G = 0.480;
    public static final double YELLOW_B = 0.135;
}
