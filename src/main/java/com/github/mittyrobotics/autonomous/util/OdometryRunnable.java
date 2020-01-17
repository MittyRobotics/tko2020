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

import com.github.mittyrobotics.path.following.util.Odometry;

import java.util.Timer;
import java.util.TimerTask;

public class OdometryRunnable extends TimerTask {

    private static OdometryRunnable instance = new OdometryRunnable();

    public static OdometryRunnable getInstance() {
        return instance;
    }

    /**
     * Starts the {@link OdometryRunnable} that updates the {@link Odometry} at a frequency of
     * <code>updateFrequence</code>.
     *
     * @param updateFrequency the time in seconds between each {@link Odometry} update call.
     */
    public void start(long updateFrequency) {
        Timer timer = new Timer();
        timer.schedule(this, (long) 0.0, updateFrequency);
    }

    @Override
    public void run() {
        //TODO: Get left and right encoder position and heading value from drivetrain and gyro
        double leftEncoderPosition = 0;
        double rightEncoderPosition = 0;
        double heading = 0;
        Odometry.getInstance().update(leftEncoderPosition, rightEncoderPosition, heading);
    }
}
