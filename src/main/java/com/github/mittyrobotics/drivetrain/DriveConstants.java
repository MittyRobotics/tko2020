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

package com.github.mittyrobotics.drivetrain;

/**
 * Constants for the {@link DrivetrainSubsystem}
 */
public class DriveConstants {

    public static final double TICKS_PER_INCH = 642.83;
    public static final int LEFT_FALCON_MASTER_ID = 12; // 12
    public static final int LEFT_FALCON_SLAVE_ID = 13; // 13
    public static final int RIGHT_FALCON_MASTER_ID = 10;
    public static final int RIGHT_FALCON_SLAVE_ID = 11;
    public static final double DRIVE_FALCON_MAX_VELOCITY = 85.5/.3; //12*.3/85.5
    public static final double DRIVE_FALCON_FF = (12*.3)/85.5; //12*.3/85.5
    public static final double DRIVE_FALCON_P = 0.01;
    public static final boolean LEFT_FACLON_MASTER_INVERSION = true;
    public static final boolean LEFT_FACLON_SLAVE_INVERSION = true;
    public static final boolean RIGHT_FACLON_MASTER_INVERSION = false;
    public static final boolean RIGHT_FACLON_SLAVE_INVERSION = false;
    public static final boolean LEFT_ENCODER_INVERSION = false;
    public static final boolean RIGHT_ENCODER_INVERSION = false;
}
