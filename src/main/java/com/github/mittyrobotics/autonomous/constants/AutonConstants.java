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

public class AutonConstants {
    //Field
    public static double HIGH_TARGET_HEIGHT = (6.836 + 9.438) / 2.0; //ft
    public static double HIGH_TARGET_MIN_HEIGHT = 6.938 + (7.0 / 12.0 / 2.0); //ft
    public static double HIGH_TARGET_MAX_HEIGHT = 9.438 - (7.0 / 12.0 / 2.0); //ft

    //Vision
    public static double LIMELIGHT_HEIGHT = 22; //TODO: Find this value
    public static double LIMELIGHT_PITCH = 60; //TODO: Find this value

    //Robot
    public static double DRIVETRAIN_TRACK_WIDTH = 26; //Inches
    public static double SHOOTER_OUTPUT_HEIGHT = 22; //Inches
    public static double CAMERA_TURRET_OFFSET = 10; //Inches //TODO: Find this value

    //Shooter //TODO: Tune these
    /**
     * Shooter RPM lookup table. Used to find the correct RPM of the shooter flywheel given a distance.
     *
     * Elements are in the format {distance, RPM}
     */
    public static double[][] SHOOTER_RPM_TABLE = {
        {0,0}
    };
}
