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

package com.github.mittyrobotics.autonomous.util;

import com.github.mittyrobotics.datatypes.positioning.Position;

/**
 * Computes the values of the turret yaw, pitch, and shooter wheel velocity given a distance
 */
public class TurretComputer {
    private static TurretComputer instance = new TurretComputer();

    public static TurretComputer getInstance() {
        return instance;
    }

    /**
     * Returns the intersection height of the ball trajectory and the target line (y intercept).
     *
     * Uses this trajectory calculator: https://www.desmos.com/calculator/xy3t8xsgg2.
     *
     * @param velocity the initial velocity of the ball
     * @param angle the initial angle of the ball
     * @param distance the distance away from the target
     * @return the intersection height of the parabola and the target location.
     */
    public double computeHitLocation(double velocity, double angle, double distance){
        final double v = velocity;
        final double s = angle;
        final double d = distance;

        final double g = 32.174;

        return 0;
    }
}
