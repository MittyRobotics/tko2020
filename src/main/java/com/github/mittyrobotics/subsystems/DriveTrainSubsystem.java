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

package com.github.mittyrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.github.mittyrobotics.constants.DriveConstants;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.interfaces.IDualMotorSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class DriveTrainSubsystem extends SubsystemBase implements IDualMotorSubsystem {
    private static DriveTrainSubsystem instance;
    private WPI_TalonFX[] leftDrive = new WPI_TalonFX[2];
    private WPI_TalonFX[] rightDrive = new WPI_TalonFX[2];
    private double leftLastMeasured = 0;
    private double rightLastMeasured = 0;
    private double latestLeftVelSetpoint = 0;
    private double latestRightVelSetpoint = 0;

    private DriveTrainSubsystem() {
        super();
        setName("Drive Train Falcon");
    }

    public static DriveTrainSubsystem getInstance() {
        if (instance == null) {
            instance = new DriveTrainSubsystem();
        }
        return instance;
    }

    @Override
    public void periodic() {

    }

    public void initHardware() {

        leftDrive[0] = new WPI_TalonFX(DriveConstants.LEFT_FALCON_1);
        leftDrive[1] = new WPI_TalonFX(DriveConstants.LEFT_FALCON_2);
        rightDrive[0] = new WPI_TalonFX(DriveConstants.RIGHT_FALCON_1);
        rightDrive[1] = new WPI_TalonFX(DriveConstants.RIGHT_FALCON_2);
        leftDrive[0].configFactoryDefault();
        leftDrive[1].configFactoryDefault();
        rightDrive[0].configFactoryDefault();
        rightDrive[1].configFactoryDefault();

        leftDrive[0].setInverted(false);
        leftDrive[1].setInverted(false);

        rightDrive[0].setInverted(true);
        rightDrive[1].setInverted(true);

        setNeutralMode(NeutralMode.Coast);
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Drive Velocity Left", getLeftVelocity());
        SmartDashboard.putNumber("Drive Velocity Right", getRightVelocity());
        SmartDashboard.putNumber("Drive Velocity Left Setpoint", getLeftVelSetpoint());
        SmartDashboard.putNumber("Drive Velocity Right Setpoint", getRightVelSetpoint());
        SmartDashboard.putNumber("Gyro Angle", Gyro.getInstance().getAngle360());
    }

    public void tankDrive(double left, double right, double threshold, double multiplier) {
        multiplier = MathUtil.clamp(multiplier, 0, 1);
        threshold = MathUtil.clamp(threshold, 0, 1);
        left = MathUtil.clamp(left, -1, 1);
        right = MathUtil.clamp(right, -1, 1);
        if (Math.abs(left) > threshold) {
            leftDrive[0].set(left * multiplier);
            leftDrive[1].set(left * multiplier);
        } else {
            leftDrive[0].set(0);
            leftDrive[1].set(0);
        }
        if (Math.abs(right) > 0.1) {
            rightDrive[0].set(right * multiplier);
            rightDrive[1].set(right * multiplier);
        } else {
            rightDrive[0].set(0);
            rightDrive[1].set(0);
        }
    }

    public void tankDrive(double left, double right) {
        tankDrive(left, right, 0.1, 1);
    }

    @Override
    public double getLeftPosition() {
        return leftDrive[0].getSelectedSensorPosition() / DriveConstants.TICKS_PER_INCH_FALCON;
    }

    @Override
    public double getRightPosition() {
        return rightDrive[0].getSelectedSensorPosition() / DriveConstants.TICKS_PER_INCH_FALCON;
    }

    @Override
    public double getLeftVelocity() {
        return (leftDrive[0].getSelectedSensorVelocity() / DriveConstants.TICKS_PER_INCH_FALCON * 10);
    }

    @Override
    public double getRightVelocity() {
        return (rightDrive[0].getSelectedSensorVelocity() / DriveConstants.TICKS_PER_INCH_FALCON * 10);
    }

    @Override
    public void overrideSetMotor(double leftPercent, double rightPercent) {
        leftDrive[0].set(leftPercent);
        leftDrive[1].set(leftPercent);
        rightDrive[0].set(rightPercent);
        rightDrive[1].set(rightPercent);
    }

    @Override
    public void resetEncoder() {
        leftDrive[0].setSelectedSensorPosition(0);
        rightDrive[0].setSelectedSensorPosition(0);
    }

    public void tankVelocity(double leftVel, double rightVel) {
        double left;
        double right;

        this.latestLeftVelSetpoint = leftVel;
        this.latestRightVelSetpoint = rightVel;

        double MAX_SPEED = 12;

        double measuredLeft = getLeftVelocity();
        //0.06
        //0.0
        double kA = 0.0;
        double FFLeft = DriveConstants.DRIVE_FALCON_FF * leftVel + kA * ((measuredLeft - leftLastMeasured) / .02);
        leftLastMeasured = measuredLeft;
        double errorLeft = leftVel - measuredLeft;
        //0.01
        double FBLeft = DriveConstants.DRIVE_FALCON_P * errorLeft;
        left = (FFLeft + FBLeft);
        left = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, left));

        double measuredRight = getRightVelocity();

        double FFRight = DriveConstants.DRIVE_FALCON_FF * rightVel + kA * ((measuredRight - rightLastMeasured) / .02);

        rightLastMeasured = measuredRight;

        double errorRight = rightVel - measuredRight;

        double FBRight = DriveConstants.DRIVE_FALCON_P * errorRight;

        right = (FFRight + FBRight);

        right = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, right));

        left = left / 12;
        right = right / 12;

        tankDrive(left, right);
    }

    public double getRightVelSetpoint() {
        return latestRightVelSetpoint;
    }

    public double getLeftVelSetpoint() {
        return latestLeftVelSetpoint;
    }

    public void setNeutralMode(NeutralMode neutralMode) {
        leftDrive[0].setNeutralMode(neutralMode);
        leftDrive[1].setNeutralMode(neutralMode);
        rightDrive[0].setNeutralMode(neutralMode);
        rightDrive[1].setNeutralMode(neutralMode);
    }
}