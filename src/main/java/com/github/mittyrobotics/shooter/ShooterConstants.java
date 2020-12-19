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

package com.github.mittyrobotics.shooter;

public class ShooterConstants {
    //Shooter
    public static final double SHOOTER_F = 1.0 / 5150;
    public static final double SHOOTER_P = 0.0004;
    public static final double SHOOTER_I = 0;
    public static final double SHOOTER_D = 0;
    public static final int SHOOTER_SPARK_MASTER_ID = 31;
    public static final int SHOOTER_SPARK_FOLLOWER_ID = 32;
    public static final boolean SHOOTER_SPARK_MASTER_INVERSION = true;
    public static final boolean SHOOTER_SPARK_FOLLOWER_INVERSION = false;
    public static final int DEFAULT_MANUAL_SHOOTER_SPEED = 4000;
}
