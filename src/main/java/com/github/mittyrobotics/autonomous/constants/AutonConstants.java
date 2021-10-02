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

package com.github.mittyrobotics.autonomous.constants;

import com.github.mittyrobotics.core.math.geometry.Vector2D;

public class AutonConstants {
    //Field
    public static double HIGH_TARGET_HEIGHT = 91;
    //(6.836 + 9.438) / 2.0 * 12; /*(6.836 + 9.438) / 2.0;*/ //ft //TODO:
    // Calibrate to field
    public static double HIGH_TARGET_MIN_HEIGHT = 6.938 + (7.0 / 12.0 / 2.0); //ft
    public static double HIGH_TARGET_MAX_HEIGHT = 9.438 - (7.0 / 12.0 / 2.0); //ft

    //Vision
    public static double LIMELIGHT_HEIGHT = 21; //TODO: Find this value
    public static double LIMELIGHT_PITCH = 15; //TODO: Find this value
    public static double SAFE_VISION_ANGLE_THRESHOLD = 2; //TODO: Find this value
    public static double VISION_SCAN_PERCENT_OUTPUT = .5;

    //Robot
    public static double DRIVETRAIN_TRACK_WIDTH = 26; //Inches
    public static double SHOOTER_OUTPUT_HEIGHT = 22; //Inches
    public static double CAMERA_TURRET_OFFSET = 7.5; //Inches //TODO: Find this value
    public static Vector2D turretPositionRelativeToRobotCenter = new Vector2D(24, 0); //Inches //Todo: Find this value
    public static Vector2D TURRET_ON_ROBOT = new Vector2D(7, 0); //Inches //Todo: Find this value

    public static double RANGE_SHOOTER_GAIN = 1;

    public static double LOW_RANGE_SHOOTER_GAIN = 1*RANGE_SHOOTER_GAIN;
    public static double MID_RANGE_SHOOTER_GAIN = 1*RANGE_SHOOTER_GAIN;
    public static double HIGH_RANGE_SHOOTER_GAIN = 1*RANGE_SHOOTER_GAIN;

    //Shooter //TODO: Tune these
    //TODO: Convert real distances into vision distance
    /**
     * Shooter RPM lookup table. Used to find the correct RPM of the shooter flywheel given a distance.
     * <p>
     * Elements are in the format {distance, RPM}
     */
    public static double[][] SHOOTER_RPM_TABLE = {
            {10, 3700 * LOW_RANGE_SHOOTER_GAIN},
            {11, 3700 * LOW_RANGE_SHOOTER_GAIN},
            {12, 3450 * LOW_RANGE_SHOOTER_GAIN},
            {13, 3500 * LOW_RANGE_SHOOTER_GAIN},
            {14, 3500 * LOW_RANGE_SHOOTER_GAIN},
            {15, 3550 * LOW_RANGE_SHOOTER_GAIN},
            {16, 3800 * MID_RANGE_SHOOTER_GAIN},
            {17, 3850 * MID_RANGE_SHOOTER_GAIN},
            {18, 3850 * MID_RANGE_SHOOTER_GAIN},
            {19, 3900 * MID_RANGE_SHOOTER_GAIN},
            {20, 3950 * MID_RANGE_SHOOTER_GAIN},
            {21, 4000 * HIGH_RANGE_SHOOTER_GAIN},
            {22, 4050 * HIGH_RANGE_SHOOTER_GAIN},
            {23, 4100 * HIGH_RANGE_SHOOTER_GAIN},
            {24, 4150 * HIGH_RANGE_SHOOTER_GAIN},
            {25, 4200 * HIGH_RANGE_SHOOTER_GAIN}
    };
}