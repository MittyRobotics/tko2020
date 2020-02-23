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

package com.github.mittyrobotics.drive;

public class Constants {

    public static final double TICKS_PER_INCH = 170.73611; //talon chassis
    public static final double TICKS_PER_INCH_LEFT = 169.34;
    public static final double TICKS_PER_INCH_RIGHT = 169.76;
    public static final double TICKS_PER_INCH_FALCON = 642.83;

    public static final int LEFT_TALON_1 = 20;
    public static final int LEFT_TALON_2 = 21;
    public static final int RIGHT_TALON_1 = 23;
    public static final int RIGHT_TALON_2 = 22;

    public static final int LEFT_FALCON_1 = 0;
    public static final int LEFT_FALCON_2 = 1;
    public static final int RIGHT_FALCON_1 = 2;
    public static final int RIGHT_FALCON_2 = 3;

    public static final double MAX_FALCON_SPEED = 21600.0;
    public static final double MAX_TALON_SPEED = 3075.0;
    public static final double[] HIGH_SPEED_VELOCITY_PID_CONTROLLER = {0.0007, 0, 0}; // falcon chassis: 0.0001, 0, 0
    //public static final double[] LOW_SPEED_VELOCITY_PID_CONTROLLER = {0.000065, 0, 0};
    public static final double[] DRIVE_VELOCITY_PID = {2, 0, 0};
            //position: p val: 0.0668 i val: 0.00001 d val: 0.0315
    public static final double[] TURN = {0.1, 0, 0};

    public static final boolean[] LEFT_TALON_INVERSIONS = {false, false};
    public static final boolean[] RIGHT_TALON_INVERSIONS = {true, true};

    public static final int COLORWHEELDRIVE_CURRENT = 5;
    public static final double DRIVE_FALCON_FF = 0.06;
    public static final double DRIVE_FALCON_P = 0.01;
}
