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

package com.github.mittyrobotics.constants;

public class ClimberConstants {
    public static final int LEFT_WINCH_ID = 50;
    public static final int RIGHT_WINCH_ID = 51;
    public static final int PISTON_FORWARD_ID = 1;
    public static final int PISTON_REVERSE_ID = 6;
    public static final double WINCH_TPI = 10;
    public static final double PISTON_DISTANCE = 5;
    public static final double[] WINCH_PID_VALUES = {0.5, 0, 0};
    public static final double PID_OUTPUT_RANGE = 0.5;
    public static final double UP_POSITION = 0;
    public static final int LEFT_ACTUATOR_ID = 0;
    public static final int RIGHT_ACTATOR_ID = 1;
    public static final boolean LEFT_WINCH_INVERSION = false;
    public static final boolean RIGHT_WINCH_INVERSION = false;
    public static final double THRESHOLD = 5;
    public static final double MIDDLE_UP_POSITION_LEFT = 10;
    public static final double MIDDLE_UP_POSITION_RIGHT = 10;
    public static final double LEFT_UP_POSITION_LEFT = 10;
    public static final double LEFT_UP_POSITION_RIGHT = 10;
    public static final double RIGHT_UP_POSITION_LEFT = 10;
    public static final double RIGHT_UP_POSITION_RIGHT = 10;
    public static final double CLIMBER_DOWN_POSITION = 10;
}
