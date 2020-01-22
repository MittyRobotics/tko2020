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

import com.github.mittyrobotics.Gyro;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.autonomous.vision.Vision;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.turret.TurretSubsystem;

/**
 * {@link TurretFieldManager} class. Manages the turret's position relative to the field and relative to vision targets.
 * <p>
 * Definitions:
 * Field-relative turret angle (<code>fieldTurretAngle</code>): the angle of the turret relative to the field. Does not care
 * about the angle of the robot.
 * Robot-relative turret angle (<code>robotTurretAngle</code>): the angle of the turret relative to the robot. In other words,
 * the rotation of the turret offset from the turret's default location.
 *
 * TODO: List of actions for vision turret placement:
 * -get vision angle
 * -get turret-relative vision angle
 * -compensate turret-relative vision angle from latency
 * -compensate turret-rleative vision angle from robot movement
 * -get robot-relative turret angle from current turret angle and turret-relative vision angle
 * -get field-relative turret angle from robot-relative turret angle
 * -set turret's field-relative turret angle
 * -maintain field-relative turret angle
 */
public class TurretFieldManager {
    private static TurretFieldManager instance = new TurretFieldManager();

    private Transform fieldTurretTransform;

    public static TurretFieldManager getInstance() {
        return instance;
    }

    /**
     * Updates the {@link TurretFieldManager}
     */
    public void run() {
        Rotation gyroAngle = new Rotation(Gyro.getInstance().getAngle360());
        Rotation robotTurretAngle = new Rotation(TurretSubsystem.getInstance().getAngle());
        double distanceToTarget = Vision.getInstance().getCurrentVisionTarget().getDistance();
        this.fieldTurretTransform = computeTurretTransform(gyroAngle, robotTurretAngle, distanceToTarget);
    }

    /**
     * Computes the field-relative angle of the turret in the form of a {@link Rotation}.
     *
     * @param gyroAngle        the gyro angle
     * @param robotTurretAngle the robot-relative turret angle
     * @return the field-relative angle of the turret in the form of a {@link Rotation}.
     */
    public Rotation computeRobotToFieldAngle(Rotation gyroAngle, Rotation robotTurretAngle) {
        return gyroAngle.subtract(robotTurretAngle);
    }

    /**
     * Computes the robot-relative angle of the turret in the form of a {@link Rotation}.
     * <p>
     * The robot-relative angle is computed from the gyro angle and a field-relative turret angle.
     *
     * @param gyroAngle        the gyro angle
     * @param fieldTurretAngle the field-relative turret angle
     * @return the robot-relative angle of the turret in the form of a {@link Rotation}.
     */
    public Rotation computeFieldToRobotAngle(Rotation gyroAngle, Rotation fieldTurretAngle) {
        return gyroAngle.subtract(fieldTurretAngle);
    }

    /**
     * Computes the field-relative {@link Transform} of the turret given parameters from the vision system.
     *
     * @param distanceToTarget the distance from the turret to the vision target (not camera!).
     * @param gyroAngle
     * @param robotTurretAngle
     * @return the {@link Transform} that makes up the turret's position relative to the field.
     */
    private Transform computeTurretTransform(Rotation gyroAngle, Rotation robotTurretAngle, double distanceToTarget) {
        if (distanceToTarget == -1000) {
            return new Transform();
        }

        Rotation fieldTurretRotation = computeRobotToFieldAngle(gyroAngle, robotTurretAngle);

        //Target relative position
        Transform turretPosition = new Transform(
                -distanceToTarget * fieldTurretRotation.cos(),
                -distanceToTarget * fieldTurretRotation.sin(),
                fieldTurretRotation);

        //Add scoring zone to get field-relative position
        turretPosition = turretPosition.add(AutonCoordinates.SCORING_TARGET);

        return turretPosition;
    }

    /**
     * Computes the RPM needed for the shooter to shoot from <code>distance</code>.
     *
     * @param distance the distance from the target to shoot from.
     * @return the RPM needed for the shooter to shoot from <code>distance</code>.
     */
    public double computeShooterRPMFromDistance(double distance) {
        double closest = Double.POSITIVE_INFINITY;
        double rpm = 0;
        for (int i = 0; i < AutonConstants.SHOOTER_RPM_TABLE.length; i++) {
            if (AutonConstants.SHOOTER_RPM_TABLE[i][0] < closest) {
                closest = AutonConstants.SHOOTER_RPM_TABLE[i][0];
                rpm = AutonConstants.SHOOTER_RPM_TABLE[i][1];
            }
        }
        return rpm;
    }

    /**
     * Returns the current field-relative turret {@link Transform}
     *
     * @return the current field-relative turret {@link Transform}
     */
    public Transform getFieldTurretTransform() {
        return fieldTurretTransform;
    }
}
