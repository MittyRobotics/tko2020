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

import com.github.mittyrobotics.Gyro;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.util.TurretFieldManager;
import com.github.mittyrobotics.autonomous.util.VisionTarget;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.vision.Limelight;

public class Vision {
    private static Vision instance = new Vision();
    private VisionTarget currentVisionTarget;

    public static Vision getInstance() {
        return instance;
    }

    public void run() {
        Limelight.getInstance().updateLimelightValues();
        Rotation visionPitch = new Rotation(Limelight.getInstance().getPitchToTarget());
        Rotation visionYaw = new Rotation(Limelight.getInstance().getYawToTarget());
        Rotation gyro = new Rotation(Gyro.getInstance().getAngle());
        this.currentVisionTarget = computeVisionTarget(visionPitch, visionYaw,gyro);
    }

    public VisionTarget computeVisionTarget(Rotation visionPitch, Rotation visionYaw, Rotation gyro) {
        double visionDistance = computeVisionDistance(visionPitch);
        double turretRelativeVisionDistance = computeTurretRelativeVisionDistance(visionDistance, visionYaw);

        Rotation turretRelativeVisionYaw = computeTurretRelativeVisionYaw(visionDistance,
                turretRelativeVisionDistance
                , visionYaw);

        Rotation fieldRelativeVisionYaw =
                TurretFieldManager.getInstance().computeRobotToFieldAngle(gyro,
                        turretRelativeVisionYaw);

        return new VisionTarget(turretRelativeVisionYaw, fieldRelativeVisionYaw, turretRelativeVisionDistance);
    }

    /**
     * Returns if the vision system is safe to use.
     *
     * @return if the vision system is safe to use.
     */
    public boolean isSafeToUseVision() {
        return Limelight.getInstance().isHasValidTarget();
    }

    private double computeVisionDistance(Rotation pitch) {
        return (AutonConstants.HIGH_TARGET_HEIGHT - AutonConstants.LIMELIGHT_HEIGHT) /
                Math.tan(Math.toRadians(pitch.getHeading() + AutonConstants.LIMELIGHT_PITCH));
    }

    /**
     * Calculates the turret-relative vision distance. This is the distance from the center of the turret to the
     * vision target.
     *
     * @param visionDistance the distance from the vision camera to the vision target
     * @param visionYaw      the yaw from the vision camera to the vision target
     * @return the turret-relative vision distance
     */
    private double computeTurretRelativeVisionDistance(double visionDistance, Rotation visionYaw) {
        double x = AutonConstants.CAMERA_TURRET_OFFSET;
        return Math.sqrt(x * x + visionDistance * visionDistance +
                2 * x * visionDistance * visionYaw.cos());
    }

    /**
     * Calculates the turret-relative vision yaw {@link Rotation}. This is the yaw from the turret's current rotation
     * to the vision target.
     *
     * @param visionDistance               the distance from the vision camera to the vision target
     * @param turretRelativeVisionDistance the distance from the center of the turret to the vision target
     * @param visionYaw                    the yaw from the vision camera to the vision target
     * @return the turret-relative vision yaw {@link Rotation}
     */
    private Rotation computeTurretRelativeVisionYaw(double visionDistance, double turretRelativeVisionDistance,
                                                    Rotation visionYaw) {
        return new Rotation(Math.toDegrees(Math.asin((visionDistance / turretRelativeVisionDistance) * visionYaw.sin())));
    }

    private Rotation computeLatencyAndVelocityCompensationAngle() {
        //TODO: Implement this
        return new Rotation();
    }

    public VisionTarget getCurrentVisionTarget() {
        return currentVisionTarget;
    }
}
