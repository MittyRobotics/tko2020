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
import com.github.mittyrobotics.autonomous.util.ShooterSetpoint;
import com.github.mittyrobotics.vision.Limelight;

public class Vision {
    private static Vision instance = new Vision();
    private double turretRelativeVisionDistance;
    private double turretRelativeVisionYaw;
    private ShooterSetpoint shooterSetpoint;

    public static Vision getInstance() {
        return instance;
    }

    public void run() {
        Limelight.getInstance().updateLimelightValues();
        this.turretRelativeVisionDistance = computeVisionDistance(Limelight.getInstance().getPitchToTarget());
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

    private double computeTurretRelativeVisionDistance(){
        //TODO: Implement this
        return 0;
    }

    private double computeTurretRelativeVisionYaw(){
        //TODO: Implement this
        return 0;
    }

    private double computeShooterVelocityFromDistance(double distance){
        //TODO: Implement this
        return 0;
    }

    private double computeLatencyAndVelocityCompensationAngle(){
        //TODO: Implement this
        return 0;
    }

    public double getTurretRelativeVisionDistance() {
        return turretRelativeVisionDistance;
    }

    public double getTurretRelativeVisionYaw() {
        return turretRelativeVisionYaw;
    }

    public ShooterSetpoint getShooterSetpoint() {
        return shooterSetpoint;
    }
}
