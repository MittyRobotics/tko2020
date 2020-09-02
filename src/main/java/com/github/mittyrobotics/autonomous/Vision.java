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

package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.subsystems.TurretSubsystem;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.interfaces.IDashboard;
import com.github.mittyrobotics.vision.Limelight;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Vision implements IDashboard {
    private static Vision instance;
    private double visibleTimer;
    private double visibleTimerStart;
    private double latestVisionLatency;
    private VisionTarget latestVisionTarget;
    private MedianFilter yawFilter = new MedianFilter(10);
    private MedianFilter pitchFilter = new MedianFilter(10);
    private MedianFilter distanceFilter = new MedianFilter(10);

    private double resetTimer;
    private double resetTimerStart;

    public static Vision getInstance() {
        if (instance == null) {
            instance = new Vision();
        }
        return instance;
    }

    public void run(boolean withTurretSuperStructure) {
        //Update reset timer
        resetTimer = Timer.getFPGATimestamp()-resetTimerStart;

        //Update limelight values
        Limelight.getInstance().updateLimelightValues();

        //Update vision latency
        latestVisionLatency = Limelight.getInstance().getLimelightLatency();

        //If vision is detected, update vision values
        if (isVisionDetected()) {
            //Get yaw and pitch to target from limelight
            Rotation visionYaw = new Rotation(Limelight.getInstance().getYawToTarget());
            Rotation visionPitch = new Rotation(Limelight.getInstance().getPitchToTarget());

            //Calculate vision distance
            double visionDistance = computeVisionDistance(visionPitch) * 120 / 135.0;

            //Update rotations from median filter
            visionYaw = new Rotation(yawFilter.calculate(visionYaw.getDegrees()));
            visionPitch = new Rotation(pitchFilter.calculate(visionPitch.getDegrees()));
            visionDistance = distanceFilter.calculate(visionDistance);

            //Get distance and angle from turret's center of rotation to the target instead of camera to the target
            visionDistance = visionToTurretDistance(visionDistance, visionYaw);
            visionYaw = visionToTurretYaw(visionDistance, visionDistance, visionYaw);

            //Init blank turret transform
            Transform turretTransform = new Transform();


            //Set latest vision target
            latestVisionTarget = new VisionTarget(turretTransform, visionYaw, visionDistance);

            //Set the start of the reset timer
            resetTimerStart = Timer.getFPGATimestamp();
        } else {
            //If reset timer is above 2 seconds, reset the filters
            if(resetTimer > 2){
                //Reset filters
                yawFilter.reset();
                pitchFilter.reset();
                distanceFilter.reset();
            }
        }

        //Update vision visible timer
        visibleTimer = Timer.getFPGATimestamp() - visibleTimerStart;
        if (!isVisionDetected()) {
            //Reset vision visible timer
            visibleTimerStart = Timer.getFPGATimestamp();
        }
    }

    public boolean isVisionDetected() {
        return Limelight.getInstance().isHasValidTarget();
    }

    /**
     * Returns if the vision system is safe to use for localization calculations.
     *
     * @param seconds the seconds for the target to be visible
     * @return if the vision system is safe to use for localization calculations.
     */
    public boolean isSafeToUseVisionCalculations(double seconds) {
        return visibleTimer >= seconds;
    }

    /**
     * Returns if the vision system is aligned.
     *
     * @return if the vision system is aligned.
     */
    public boolean isVisionAligned() {
        return Math.abs(TurretSubsystem.getInstance().getError()) < AutonConstants.SAFE_VISION_ANGLE_THRESHOLD;
    }

    /**
     * Updates the {@link SmartDashboard} values associated with the class
     */
    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("vision-turret-yaw", latestVisionTarget.getObserverYawToTarget().getDegrees());
        SmartDashboard
                .putNumber("vision-field-yaw", latestVisionTarget.getObserverTransform().getRotation().getDegrees());
        SmartDashboard
                .putNumber("vision-localization-x", latestVisionTarget.getObserverTransform().getPosition().getX());
        SmartDashboard
                .putNumber("vision-localization-y", latestVisionTarget.getObserverTransform().getPosition().getY());
        SmartDashboard.putNumber("vision-distance", latestVisionTarget.getObserverDistanceToTarget());
        SmartDashboard.putNumber("vision-latency", latestVisionLatency);
    }


    /**
     * Computes the field-relative turret {@link Position} based on the vision values.
     *
     * @param visionDistance the distance from the turret's center to the vision target
     * @param visionYaw      the yaw angle from the turret's center to the vision target
     * @return the field-relative turret {@link Position}
     */
    private Position computeTurretPosition(double visionDistance, Rotation visionYaw) {
        //Target relative position
        Position turretPosition = new Position(
                -visionDistance * visionYaw.cos(),
                -visionDistance * visionYaw.sin());

        //Add scoring target to get field-relative position
        turretPosition = turretPosition.add(AutonCoordinates.SCORING_TARGET);

        return turretPosition;
    }

    private double visionToTurretDistance(double visionDistance, Rotation visionYaw) {
        double x = AutonConstants.CAMERA_TURRET_OFFSET;
        return Math.sqrt(x * x + visionDistance * visionDistance +
                2 * x * visionDistance * visionYaw.cos());
    }

    private Rotation visionToTurretYaw(double visionDistance, double turretDistance,
                                       Rotation visionYaw) {
        return new Rotation(
                Math.toDegrees(Math.asin((visionDistance / turretDistance) * visionYaw.sin())));
    }

    private double computeVisionDistance(Rotation pitch) {
        return (AutonConstants.HIGH_TARGET_HEIGHT - AutonConstants.LIMELIGHT_HEIGHT) /
                Math.tan(Math.toRadians(pitch.getDegrees() + AutonConstants.LIMELIGHT_PITCH));
    }

    public VisionTarget getLatestVisionTarget() {
        return latestVisionTarget;
    }
}
