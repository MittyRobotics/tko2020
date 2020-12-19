/*
 * MIT License
 *
 * Copyright (c) 2020 Mitty Robotics (Team 1351)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mittyrobotics.colorwheel;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.util.Color;

/**
 * Constants for the {@link ColorPistonSubsystem} and {@link SpinnerSubsystem} classes
 */
public class ColorWheelConstants {

    //channels for hardware
    public static final int SOLENOID_FOWARD_CHANNEL = 0;
    public static final int SOLENOID_REVERSE_CHANNEL = 7;

    public static final NeutralMode SPINNER_NEUTRAL_MODE = NeutralMode.Brake;

    public static final int SPINNER_TALON_ID = 20;
    public static final boolean SPINNER_TALON_INVERSION = false;
    public static final boolean SPINNER_ENCODER_INVERSION = false;

    public static final double SPINNER_P = 0.0000001;
    public static final double SPINNER_I = 0;
    public static final double SPINNER_D = 0;
    public static final double SPINNER_FF = 1 / 6250.0;
    public static final double DEFUALT_REVS = 4;


    //ticks for one inch of the color wheel
    public static final int TICKS_PER_INCH = 326;
    //slow and fast velocity for motor
    public static final int FAST_VELOCITY = 480;
    public static final int SLOW_VELOCITY = 23 * 8;


    //rgb for color sensor targets (CALIBRATE THIS)
    public static final double BLUE_R = 0.21;
    public static final double BLUE_G = 0.48;
    public static final double BLUE_B = 0.31;

    public static final double GREEN_R = 0.22;
    public static final double GREEN_G = 0.51;
    public static final double GREEN_B = 0.27;

    public static final double RED_R = 0.31;
    public static final double RED_G = 0.45;
    public static final double RED_B = 0.24;

    public static final double YELLOW_R = 0.29;
    public static final double YELLOW_G = 0.56;
    public static final double YELLOW_B = 0.16;

    public static final double NULL_R = 0.26;
    public static final double NULL_G = 0.53;
    public static final double NULL_B = 0.21;

    public static final double ALSO_NULL_R = 0.23;
    public static final double ALSO_NULL_G = 0.50;
    public static final double ALSO_NULL_B = 0.27;

    public static final Color BLUE_COLOR =
            ColorMatch.makeColor(ColorWheelConstants.BLUE_R, ColorWheelConstants.BLUE_G, ColorWheelConstants.BLUE_B);
    public static final Color GREEN_COLOR =
            ColorMatch.makeColor(ColorWheelConstants.GREEN_R, ColorWheelConstants.GREEN_G, ColorWheelConstants.GREEN_B);
    public static final Color RED_COLOR =
            ColorMatch.makeColor(ColorWheelConstants.RED_R, ColorWheelConstants.RED_G, ColorWheelConstants.RED_B);
    public static final Color YELLOW_COLOR = ColorMatch
            .makeColor(ColorWheelConstants.YELLOW_R, ColorWheelConstants.YELLOW_G, ColorWheelConstants.YELLOW_B);
    public static final Color NULL_COLOR =
            ColorMatch.makeColor(ColorWheelConstants.NULL_R, ColorWheelConstants.NULL_G, ColorWheelConstants.NULL_B);
    public static final Color NULL_COLOR_2 =
            ColorMatch.makeColor(ColorWheelConstants.ALSO_NULL_R, ColorWheelConstants.ALSO_NULL_G,
                    ColorWheelConstants.ALSO_NULL_B);
}