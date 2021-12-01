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

package com.github.mittyrobotics.drivetrain;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.github.mittyrobotics.autonomous.Odometry;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.interfaces.IDualMotorSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;

/**
 * Drivetrain Subsystem to move the chassis
 */
public class DrivetrainSubsystem extends SubsystemBase implements IDualMotorSubsystem {
    /**
     * {@link DrivetrainSubsystem} instance
     */
    private static DrivetrainSubsystem instance;

    /**
     * Drivetrain left {@link WPI_TalonFX}s
     */
    private final WPI_TalonFX[] leftDrive = new WPI_TalonFX[2];

    /**
     * Drivetrain right {@link WPI_TalonFX}s
     */
    private final WPI_TalonFX[] rightDrive = new WPI_TalonFX[2];

    /**
     * Variables used for velocity pid calculations
     */
    private double leftLastMeasured = 0;
    private double rightLastMeasured = 0;
    private double latestLeftVelSetpoint = 0;
    private double latestRightVelSetpoint = 0;

    /**
     * Calls {@link SubsystemBase} constructor and names the subsystem "Drivetrain"
     */
    private DrivetrainSubsystem() {
        super();
        setName("Drivetrain");
    }

    /**
     * Returns the {@link DrivetrainSubsystem} instance.
     *
     * @return the {@link DrivetrainSubsystem} instance.
     */
    public static DrivetrainSubsystem getInstance() {
        if (instance == null) {
            instance = new DrivetrainSubsystem();
        }
        return instance;
    }



    /**
     * Initialize the drivetrain's hardware
     */
    @Override
    public void initHardware() {

        leftDrive[0] = new WPI_TalonFX(DriveConstants.LEFT_FALCON_MASTER_ID);
        leftDrive[1] = new WPI_TalonFX(DriveConstants.LEFT_FALCON_SLAVE_ID);
        rightDrive[0] = new WPI_TalonFX(DriveConstants.RIGHT_FALCON_MASTER_ID);
        rightDrive[1] = new WPI_TalonFX(DriveConstants.RIGHT_FALCON_SLAVE_ID);

        leftDrive[0].configFactoryDefault();
        leftDrive[1].configFactoryDefault();
        rightDrive[0].configFactoryDefault();
        rightDrive[1].configFactoryDefault();

        leftDrive[0].setInverted(DriveConstants.LEFT_FACLON_MASTER_INVERSION);
        leftDrive[1].setInverted(DriveConstants.LEFT_FACLON_SLAVE_INVERSION);

        rightDrive[0].setInverted(DriveConstants.RIGHT_FACLON_MASTER_INVERSION);
        rightDrive[1].setInverted(DriveConstants.RIGHT_FACLON_SLAVE_INVERSION);

        leftDrive[0].setSensorPhase(DriveConstants.LEFT_ENCODER_INVERSION);
        rightDrive[0].setSensorPhase(DriveConstants.RIGHT_ENCODER_INVERSION);

        rightDrive[0].setSelectedSensorPosition(0);
        leftDrive[0].setSelectedSensorPosition(0);
//
//        leftDrive[0].configOpenloopRamp(3);
//        rightDrive[0].configOpenloopRamp(3);
//        leftDrive[1].configOpenloopRamp(3);
//        rightDrive[1].configOpenloopRamp(3);
        setNeutralMode(NeutralMode.Coast);
    }

    /**
     * Update the drivetrain's dashboard values
     */
    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Drive Velocity Left", getLeftVelocity());
        SmartDashboard.putNumber("Drive Velocity Right", getRightVelocity());
        SmartDashboard.putNumber("Drive Velocity Left Setpoint", getLeftVelSetpoint());
        SmartDashboard.putNumber("Drive Velocity Right Setpoint", getRightVelSetpoint());
        SmartDashboard.putNumber("Drive Position Left", getLeftPosition());
        SmartDashboard.putNumber("Drive Position Right", getRightPosition());
        SmartDashboard.putNumber("Drive X", Odometry.getInstance().getRobotVector().getX());
        SmartDashboard.putNumber("Drive Y", Odometry.getInstance().getRobotVector().getY());
        SmartDashboard.putNumber("Gyro Angle", Gyro.getInstance().getAngle360());
    }

    public void setMode(NeutralMode mode) {
        leftDrive[0].setNeutralMode(mode);
        leftDrive[1].setNeutralMode(mode);
        rightDrive[0].setNeutralMode(mode);
        rightDrive[1].setNeutralMode(mode);
    }

    /**
     * Drives the robot using tank drive
     *
     * @param left percent output to move the left side
     *
     * @param right percent output to move the right side
     *
     * @param threshold minimum percent output for each side
     *
     * @param multiplier multiplier for the percent output on each side
     */
    public void tankDrive(double left, double right, double threshold, double multiplier) {
        setNeutralMode(NeutralMode.Coast);
        multiplier = MathUtil.clamp(multiplier, 0, 1);
        threshold = MathUtil.clamp(threshold, 0, 1);
        left = MathUtil.clamp(left, -1, 1);
        right = MathUtil.clamp(right, -1, 1);
        if (Math.abs(left) < threshold) {
            left = 0;
        }
        if (Math.abs(right) < threshold) {
            right = 0;
        }
        overrideSetMotor(left * multiplier, right * multiplier);
    }

    /**
     * Drives the robot using tank drive
     *
     * Default threshold of 0.1
     *
     * @param left percent output to move the left side
     *
     * @param right percent output to move the right side
     */
    public void tankDrive(double left, double right) {
        tankDrive(left, right, 0.1, 1);
    }

    /**
     * Returns the left encoder position in inches
     *
     * @return the left encoder position in inches
     */
    @Override
    public double getLeftPosition() {
        return leftDrive[0].getSelectedSensorPosition() / DriveConstants.TICKS_PER_INCH;
    }

    /**
     * Returns the right encoder position in inches
     *
     * @return the right encoder position in inches
     */
    @Override
    public double getRightPosition() {
        return rightDrive[0].getSelectedSensorPosition() / DriveConstants.TICKS_PER_INCH;
    }

    /**
     * Returns the left encoder velocity in inches / sec
     *
     * @return the left encoder velocity in inches / sec
     */
    @Override
    public double getLeftVelocity() {
        return ((leftDrive[0].getSelectedSensorVelocity() / DriveConstants.TICKS_PER_INCH) * 10);
    }

    /**
     * Returns the right encoder velocity in inches / sec
     *
     * @return the right encoder velocity in inches / sec
     */
    @Override
    public double getRightVelocity() {
        return ((rightDrive[0].getSelectedSensorVelocity() / DriveConstants.TICKS_PER_INCH) * 10);
    }

    /**
     * Sets the motor output ignoring thresholds
     *
     * Sets the robot to {@link NeutralMode#Coast}
     *
     * @param leftPercent left percent output
     *
     * @param rightPercent right percent output
     */
    @Override
    public void overrideSetMotor(double leftPercent, double rightPercent) {
//        setNeutralMode(NeutralMode.Coast);
        leftDrive[0].set(leftPercent);
        leftDrive[1].set(leftPercent);
        rightDrive[0].set(rightPercent);
        rightDrive[1].set(rightPercent);
    }

    /**
     * Resets the left and right encoder position
     */
    @Override
    public void resetEncoder() {
        leftDrive[0].setSelectedSensorPosition(0);
        rightDrive[0].setSelectedSensorPosition(0);
    }

    /**
     * Moves the robot using tank drive in
     *
     * @param leftVel left velocity in inches / sec
     *
     * @param rightVel right velocity in inches / sec
     */
    public void tankVelocity(double leftVel, double rightVel) {
//        setNeutralMode(NeutralMode.Coast);
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
        SmartDashboard.putNumber("FBLeft", FBLeft);
        SmartDashboard.putNumber("ErrorLeft", errorLeft);
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

//        System.out.println(left + " | " + right);

        overrideSetMotor(left, right);
    }

    /**
     * Returns current left velocity setpoint
     *
     * @return current left velocity setpoint
     */
    public double getRightVelSetpoint() {
        return latestRightVelSetpoint;
    }

    /**
     * Returns current right velocity setpoint
     *
     * @return current right velocity setpoint
     */
    public double getLeftVelSetpoint() {
        return latestLeftVelSetpoint;
    }

    /**
     * Sets each of the {@link WPI_TalonFX}s to the inputted neutral mdoe
     *
     * @param neutralMode the neutral mode to set the talons to
     */
    private void setNeutralMode(NeutralMode neutralMode) {
        leftDrive[0].setNeutralMode(neutralMode);
        leftDrive[1].setNeutralMode(neutralMode);
        rightDrive[0].setNeutralMode(neutralMode);
        rightDrive[1].setNeutralMode(neutralMode);
    }

    /**
     * Brakes the robot
     *
     * Stops the robot form moving and sets to robot to {@link NeutralMode#Brake} to make it stop immediately
     */
    public void brake(){
        overrideSetMotor(0, 0);
        setNeutralMode(NeutralMode.Brake);
    }

    public void coast(){
        setNeutralMode(NeutralMode.Coast);
    }
}