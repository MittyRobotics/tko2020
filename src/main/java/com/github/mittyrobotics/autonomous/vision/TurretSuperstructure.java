/*
 * MIT License
 *
 * Copyright (c) 2019 Mitty Robotics (Team 1351)
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
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.autonomous.util.TurretAimMode;
import com.github.mittyrobotics.autonomous.util.VisionTarget;
import com.github.mittyrobotics.datatypes.geometry.Line;
import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.turret.TurretSubsystem;

/**
 * {@link TurretSuperstructure} class. Manages the turret's position relative to the field and relative to vision targets.
 * <p>
 * Definitions:
 * Field-relative turret angle (<code>fieldRelativeRotation</code>): the angle of the turret relative to the field. Does not care
 * about the angle of the robot.
 * Robot-relative turret angle (<code>robotRelativeRotation</code>): the angle of the turret relative to the robot. In other words,
 * the rotation of the turret offset from the turret's default location.
 */
public class TurretSuperstructure {
    public static TurretSuperstructure instance = new TurretSuperstructure();
    private Rotation robotRelativeRotation;
    private Rotation fieldRelativeRotation;
    private Position fieldRelativePosition;
    private TurretAimMode aimMode;
    private Transform setpoint;

    public static TurretSuperstructure getInstance() {
        return instance;
    }

    public void run() {
        //Compute turret position and rotations
        this.robotRelativeRotation = new Rotation(TurretSubsystem.getInstance().getAngle());
        this.fieldRelativeRotation = computeFieldRelativeRotation(Gyro.getInstance().getRotation(),
                robotRelativeRotation);
        this.fieldRelativePosition = computeFieldRelativePosition(Gyro.getInstance().getRotation(),
                robotRelativeRotation, Vision.getInstance().getCurrentVisionTarget().getDistance());

        //Maintain the turret's setpoint
        maintainSetpoint();
    }

    public void maintainSetpoint() {
        switch (aimMode) {
            case FIELD_RELATIVE_AIM:
                maintainFieldRelativeAim(setpoint.getPosition());
                break;
            case FIELD_RELATIVE_ANGLE:
                maintainFieldRelativeRotation(setpoint.getRotation());
                break;
            case ROBOT_RELATIVE_ANGLE:
                maintainRobotRelativeRotation(setpoint.getRotation());
                break;
        }
    }

    private void maintainFieldRelativeRotation(Rotation setpoint) {
        TurretSubsystem.getInstance().setAngle(computeRobotRelativeRotation(Gyro.getInstance().getRotation(),
                setpoint).getHeading());
    }

    private void maintainRobotRelativeRotation(Rotation setpoint) {
        TurretSubsystem.getInstance().setAngle(setpoint.getHeading());
    }

    private void maintainFieldRelativeAim(Position setpoint) {
        Rotation rotationSetpoint =
                new Line(Odometry.getInstance().getRobotTransform().getPosition(), setpoint).getLineAngle();
        maintainFieldRelativeRotation(rotationSetpoint);
    }

    public void setFieldRelativeAimPosition(Position setpoint) {
        this.aimMode = TurretAimMode.FIELD_RELATIVE_AIM;
        this.setpoint = new Transform(setpoint, 0);
    }

    public void setFieldRelativeAimRotation(Rotation setpoint) {
        this.aimMode = TurretAimMode.FIELD_RELATIVE_ANGLE;
        this.setpoint = new Transform(0, 0, setpoint);
    }

    public void setRobotRelativeAimRotation(Rotation setpoint) {
        this.aimMode = TurretAimMode.ROBOT_RELATIVE_ANGLE;
        this.setpoint = new Transform(0, 0, setpoint);
    }

    /**
     * Computes the field-relative {@link Position} of the turret given parameters from the vision system.
     *
     * @param distanceToTarget the distance from the turret to the vision target (not camera!).
     * @param gyroAngle
     * @param robotTurretAngle
     * @return the {@link Position} that makes up the turret's position relative to the field.
     */
    private Position computeFieldRelativePosition(Rotation gyroAngle, Rotation robotTurretAngle,
                                                  double distanceToTarget) {
        if (distanceToTarget == -1000) {
            return new Position();
        }

        Rotation fieldTurretRotation = computeFieldRelativeRotation(gyroAngle, robotTurretAngle);

        //Target relative position
        Position turretPosition = new Position(
                -distanceToTarget * fieldTurretRotation.cos(),
                -distanceToTarget * fieldTurretRotation.sin());

        //Add scoring zone to get field-relative position
        turretPosition = turretPosition.add(AutonCoordinates.SCORING_TARGET.getPosition());

        return turretPosition;
    }

    /**
     * Aims the turret towards a {@link VisionTarget}.
     * <p>
     * This sets the field-relative angle of the turret to the field-relative angle from the turret to the vision
     * target. This results in the turret using the gyro to maintain field-relative positioning while also aiming at
     * the vision target.
     *
     * @param target the {@link VisionTarget} to take aim at
     */
    public void visionAim(VisionTarget target) {
        setFieldRelativeAimRotation(target.getFieldRelativeYaw());
    }

    public Rotation computeFieldRelativeRotation(Rotation gyro, Rotation robotRelativeRotation) {
        return gyro.subtract(robotRelativeRotation);
    }

    public Rotation computeRobotRelativeRotation(Rotation gyro, Rotation fieldRelativeRotation) {
        return gyro.subtract(fieldRelativeRotation);
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

    public Rotation getRobotRelativeRotation() {
        return robotRelativeRotation;
    }

    public Rotation getFieldRelativeRotation() {
        return fieldRelativeRotation;
    }

    public TurretAimMode getAimMode() {
        return aimMode;
    }
}
