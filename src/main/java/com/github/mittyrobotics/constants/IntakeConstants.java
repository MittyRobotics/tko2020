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

import com.ctre.phoenix.motorcontrol.NeutralMode;

public class IntakeConstants {
    public static final int INTAKE_WHEEL_ID = 40;
    public static final boolean INTAKE_WHEEL_INVERSION = false;
    public static final double INTAKE_SPEED_FAST = .7;
    public static final double INTAKE_SPEED_SLOW = .2;
    public static final double OUTTAKE_SPEED = -.5;
    public static final int SOLENOID_FORWQRD_CHANNEL = 3;
    public static final int SOLENOID_REVERSE_CHANNEL = 4;
    public static final NeutralMode INTAKE_NEUTRAL_MODE = NeutralMode.Coast;

}
