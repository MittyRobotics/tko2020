/*
 * MIT License
 *
 * Copyright (c) 2019 Mitty Robotics (Team 1351)
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

public class Constants {
    //channels for hardware
    public static final int SOLENOID_FOWARD_CHANNEL = 1;
    public static final int SOLENOID_REVERSE_CHANNEL = 0;

    public static final int TALON_DEVICE_NUMBER = 2;

    public static final double REVS = 1.75;


    //ticks for one inch of the color wheel
    public static final int TICKS_PER_INCH = 326;
    //slow and fast velocity for motor
    public static final int FAST_VELOCITY = 5000;
    public static final int SLOW_VELOCITY = 2000;


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
}
