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
import com.github.mittyrobotics.autonomous.enums.TurretAutomationMode;
import com.github.mittyrobotics.datatypes.CircularTimestampedList;
import com.github.mittyrobotics.datatypes.TimestampedElement;
import com.github.mittyrobotics.datatypes.geometry.Line;
import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.subsystems.TurretSubsystem;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj.Timer;

/**
 * {@link AutomatedTurretSuperstructure} class. Manages the turret's position relative to the field and relative to vision targets.
 * <p>
 * Definitions:
 * Field-relative turret angle (<code>fieldRelativeRotation</code>): the angle of the turret relative to the field. Does not care
 * about the angle of the robot.
 * Robot-relative turret angle (<code>robotRelativeRotation</code>): the angle of the turret relative to the robot. In other words,
 * the rotation of the turret offset from the turret's default location.
 */
public class AutomatedTurretSuperstructure {
    public static AutomatedTurretSuperstructure instance;
    private Rotation robotRelativeRotation;
    private Rotation fieldRelativeRotation;
    private Position latestAccurateFieldRelativePosition;
    private Position trackedFieldRelativePosition;
    private TurretAutomationMode aimMode = TurretAutomationMode.NO_AUTOMATION;
    private Transform setpoint;

    private CircularTimestampedList<Rotation> turretRobotRelativeRotations = new CircularTimestampedList<>(50);

    public static AutomatedTurretSuperstructure getInstance() {
        if (instance == null) {
            instance = new AutomatedTurretSuperstructure();
        }
        return instance;
    }

    public void run() {
        //Compute turret position and rotations
        this.robotRelativeRotation = new Rotation(TurretSubsystem.getInstance().getAngle());
        this.fieldRelativeRotation = robotToFieldRelativeAngle(Gyro.getInstance().getRotation(),
                robotRelativeRotation);
        this.turretRobotRelativeRotations
                .addFront(new TimestampedElement<>(robotRelativeRotation, Timer.getFPGATimestamp()));

        //If vision is safe to use, update the latest accurate field-relative position and calibrate the odometry
        if (Vision.getInstance().isSafeToUseVision()) {
            this.latestAccurateFieldRelativePosition =
                    Vision.getInstance().getLatestVisionTarget().getObserverTransform().getPosition();
            this.trackedFieldRelativePosition = latestAccurateFieldRelativePosition;
            Odometry.getInstance().setPosition(turretToRobotPosition(latestAccurateFieldRelativePosition,
                    Gyro.getInstance().getRotation()));
        } else {
            //If vision is not safe to use, capture turret position from odometry
            this.trackedFieldRelativePosition = robotToTurretPosition(Odometry.getInstance().getLatestRobotTransform());
        }

        //Maintain the automated turret control
        maintainAutomation();
    }

    /**
     * Maintains the {@link TurretSubsystem}'s automation. Updates the setpoint based on the {@link TurretAutomationMode} and
     * setpoint {@link Transform}.
     */
    private void maintainAutomation() {
        switch (aimMode) {
            case FIELD_RELATIVE_AIM:
                maintainFieldRelativeAim(setpoint.getPosition());
                TurretSubsystem.getInstance().updateTurretControlLoop();
                break;
            case FIELD_RELATIVE_ANGLE:
                maintainFieldRelativeRotation(setpoint.getRotation());
                TurretSubsystem.getInstance().updateTurretControlLoop();
                break;
            case ROBOT_RELATIVE_ANGLE:
                maintainRobotRelativeRotation(setpoint.getRotation());
                TurretSubsystem.getInstance().updateTurretControlLoop();
                break;
            case NO_AUTOMATION:
                //Don't do anything! Let someone else move the turret.
                break;
        }
    }

    /**
     * Maintains the {@link TurretSubsystem}'s field-relative angle by updating it's robot-relative setpoint.
     *
     * @param setpoint the field-relative {@link Rotation} setpoint.
     */
    private void maintainFieldRelativeRotation(Rotation setpoint) {
        maintainRobotRelativeRotation(fieldToRobotRelativeAngle(Gyro.getInstance().getRotation(), setpoint));
    }

    /**
     * Maintains the {@link TurretSubsystem}'s robot-relative angle by updating it's robot-relative setpoint.
     *
     * @param setpoint the robot-relative {@link Rotation} setpoint.
     */
    private void maintainRobotRelativeRotation(Rotation setpoint) {
        TurretSubsystem.getInstance().setTurretAngle(setpoint.getHeading());
    }

    /**
     * Maintains the {@link TurretSubsystem}'s field-relative aiming position by updating it's field-relative setpoint.
     * <p>
     * The field-relative angle is determined by finding a line intersecting the {@link AutomatedTurretSuperstructure}'s
     * current field-relative {@link Position} and the aiming setpoint {@link Position}. The angle of that line then
     * represents the field-relative {@link Rotation} to aim towards.
     *
     * @param setpoint the field-relative {@link Rotation} setpoint.
     */
    private void maintainFieldRelativeAim(Position setpoint) {
        Rotation rotationSetpoint =
                new Line(latestAccurateFieldRelativePosition, setpoint).getLineAngle();
        maintainFieldRelativeRotation(rotationSetpoint);
    }

    /**
     * Sets the field-relative aiming {@link Position}.
     *
     * @param setpoint the field-relative aiming {@link Position}.
     */
    public void setFieldRelativeAimPosition(Position setpoint) {
        this.aimMode = TurretAutomationMode.FIELD_RELATIVE_AIM;
        this.setpoint = new Transform(setpoint, 0);
    }

    /**
     * Sets the field-relative aiming {@link Rotation}.
     *
     * @param setpoint the field-relative aiming {@link Rotation}.
     */
    public void setFieldRelativeAimRotation(Rotation setpoint) {
        this.aimMode = TurretAutomationMode.FIELD_RELATIVE_ANGLE;
        this.setpoint = new Transform(0, 0, setpoint);
    }

    /**
     * Sets the robot-relative aiming {@link Rotation}.
     *
     * @param setpoint the robot-relative aiming {@link Rotation}.
     */
    public void setRobotRelativeAimRotation(Rotation setpoint) {
        this.aimMode = TurretAutomationMode.ROBOT_RELATIVE_ANGLE;
        this.setpoint = new Transform(0, 0, setpoint);
    }

    /**
     * Sets the {@link VisionTarget} to aim at.
     *
     * @param target the {@link VisionTarget} to aim at.
     */
    public void setVisionAim(VisionTarget target) {
        setFieldRelativeAimRotation(target.getObserverTransform().getRotation());
    }

    /**
     * Disables all turret automation. This stops the {@link AutomatedTurretSuperstructure} from updating the
     * turret's setpoint, allowing the turret to be moved from other places.
     */
    public void disableAutomatedTurret() {
        this.aimMode = TurretAutomationMode.NO_AUTOMATION;
    }

    public Rotation turretToRobotRelativeAngle(Rotation turretRotation, Rotation turretRelativeRotation) {
        return turretRelativeRotation.add(turretRotation);
    }

    /**
     * Converts a robot-relative {@link Rotation} into a field-relative {@link Rotation} using the gyro
     * {@link Rotation}.
     *
     * @param gyro                  the robot gyro {@link Rotation}
     * @param robotRelativeRotation the robot-relative {@link Rotation}
     * @return a field-relative {@link Rotation}.
     */
    public Rotation robotToFieldRelativeAngle(Rotation gyro, Rotation robotRelativeRotation) {
        return gyro.add(robotRelativeRotation);
    }

    /**
     * Converts a field-relative {@link Rotation} into a robot-relative {@link Rotation} using the gyro
     * {@link Rotation}.
     *
     * @param gyro                  the robot gyro {@link Rotation}
     * @param fieldRelativeRotation the field-relative {@link Rotation}
     * @return a robot-relative {@link Rotation}.
     */
    public Rotation fieldToRobotRelativeAngle(Rotation gyro, Rotation fieldRelativeRotation) {
        return fieldRelativeRotation.subtract(gyro);
    }

    public Position robotToTurretPosition(Transform robotTransform) {
        return AutonConstants.turretPositionRelativeToRobotCenter.rotateBy(robotTransform.getRotation())
                .add(robotTransform.getPosition());
    }

    public Position turretToRobotPosition(Position turretPosition, Rotation gyro) {
        return turretPosition.subtract(AutonConstants.turretPositionRelativeToRobotCenter.rotateBy(gyro));
    }

    /**
     * Compensates the angle based on the robot's movement. This works with either robot-relative angles or
     * field-relative angles.
     * <p>
     * This will make the turret aim a little to the left or right of the target depending on the robot's movement in
     * order to correctly aim for the ball to be shot into the target.
     *
     * @return the motion-compensated angle {@link Rotation}.
     */
    private Rotation computeMotionCompensationAngle(Rotation rotation) {
        //TODO: Implement this
        return rotation;
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
            if (Math.abs(distance - AutonConstants.SHOOTER_RPM_TABLE[i][0]) < closest) {
                closest = Math.abs(distance - AutonConstants.SHOOTER_RPM_TABLE[i][0]);
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

    public TurretAutomationMode getAimMode() {
        return aimMode;
    }

    public Position getLatestAccurateFieldRelativePosition() {
        return latestAccurateFieldRelativePosition;
    }

    public CircularTimestampedList<Rotation> getTurretRobotRelativeRotations() {
        return turretRobotRelativeRotations;
    }

    public Position getTrackedFieldRelativePosition() {
        return trackedFieldRelativePosition;
    }
}
