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

package com.github.mittyrobotics.autonomous.vision;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.vision.Limelight;

import java.util.Timer;
import java.util.TimerTask;

public class VisionManager extends TimerTask {
    private static VisionManager instance = new VisionManager();
    private double visionDistance;

    private boolean started = false;

    public static VisionManager getInstance() {
        return instance;
    }

    public void start(double updateFrequency) {
        if(!started){
            Timer timer = new Timer();
            updateFrequency = updateFrequency * 1000;
            timer.schedule(getInstance(), 0, (long)updateFrequency);
            started = true;
        }
    }

    @Override
    public void run() {
        Limelight.getInstance().updateLimelightValues();
        this.visionDistance = computeVisionDistance(Limelight.getInstance().getPitchToTarget());
    }

    /**
     * Returns if the vision system is safe to use.
     *
     * @return if the vision system is safe to use.
     */
    public boolean isSafeToUseVision() {
        return Limelight.getInstance().isHasValidTarget();
    }

    private double computeVisionDistance(double pitch) {
        return (AutonConstants.HIGH_TARGET_HEIGHT - AutonConstants.LIMELIGHT_HEIGHT) /
                Math.tan(Math.toRadians(pitch + AutonConstants.LIMELIGHT_PITCH));
    }

    public double getVisionDistance() {
        return visionDistance;
    }
}
