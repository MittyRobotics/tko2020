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

package com.github.mittyrobotics.shooter;

import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import com.revrobotics.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Shooter subsystem to shoot balls
 */
public class ShooterSubsystem extends SubsystemBase implements IMotorSubsystem {
    /**
     * {@link ShooterSubsystem} instance
     */
    private static ShooterSubsystem instance;

    /**
     * Shooter setpoint speed
     */
    private double currentSetpoint;

    private double manualRPMSetpoint;

    /**
     * Shooter {@link CANSparkMax}s
     */
    private CANSparkMax shooterSparkMaster, shooterSparkFollower;

    /**
     * Shooter {@link CANEncoder}s
     */
    private CANEncoder masterEncoder, followerEncoder;

    /**
     * Shooter {@link CANPIDController}s
     */
    private CANPIDController masterPIDController, followerPIDController;

    /**
     * Calls {@link SubsystemBase} constructor and names the subsystem "Shooter"
     */
    private ShooterSubsystem() {
        super();
        setName("Shooter");
    }

    /**
     * Returns the {@link ShooterSubsystem} instance.
     *
     * @return the {@link ShooterSubsystem} instance.
     */
    public static ShooterSubsystem getInstance() {
        if (instance == null) {
            instance = new ShooterSubsystem();
        }
        return instance;
    }

    /**
     * Initializes the shooter's hardware.
     */
    @Override
    public void initHardware() {
        shooterSparkMaster =
                new CANSparkMax(ShooterConstants.SHOOTER_SPARK_MASTER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterSparkMaster.restoreFactoryDefaults();
        shooterSparkMaster.setInverted(ShooterConstants.SHOOTER_SPARK_MASTER_INVERSION);
        this.masterPIDController = shooterSparkMaster.getPIDController();
        this.masterEncoder = shooterSparkMaster.getEncoder();
        masterPIDController.setFF(ShooterConstants.SHOOTER_F);
        masterPIDController.setP(ShooterConstants.SHOOTER_P);
        masterPIDController.setI(ShooterConstants.SHOOTER_I);
        masterPIDController.setD(ShooterConstants.SHOOTER_D);

        shooterSparkFollower =
                new CANSparkMax(ShooterConstants.SHOOTER_SPARK_FOLLOWER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterSparkFollower.restoreFactoryDefaults();
        shooterSparkFollower.setInverted(ShooterConstants.SHOOTER_SPARK_FOLLOWER_INVERSION);
        this.followerPIDController = shooterSparkFollower.getPIDController();
        this.followerEncoder = shooterSparkFollower.getEncoder();
        followerPIDController.setFF(ShooterConstants.SHOOTER_F);
        followerPIDController.setP(ShooterConstants.SHOOTER_P);
        followerPIDController.setI(ShooterConstants.SHOOTER_I);
        followerPIDController.setD(ShooterConstants.SHOOTER_D);
        manualRPMSetpoint = ShooterConstants.DEFAULT_MANUAL_SHOOTER_SPEED;

        shooterSparkMaster.setClosedLoopRampRate(.5);
        shooterSparkFollower.setClosedLoopRampRate(.5);
//        setDefaultCommand(new StopShooterCommand());
    }

    /**
     * Updates the shooter's dashboard values
     */
    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Current Shooter RPM", getVelocity());
        SmartDashboard.putNumber("Shooter RPM Setpoint", getCurrentSetpoint());
        SmartDashboard.putNumber("Manual Shooter RPM Setpoint", getManualRPMSetpoint());
    }

    /**
     * Gets the average RPM of both motors
     *
     * @return the shooter RPM
     */
    public double getVelocity() {
        return (masterEncoder.getVelocity() + followerEncoder.getVelocity()) / 2;
    }

    /**
     * Gets the current setpoint for the motors to spin at
     *
     * @return current setpoint
     */
    public double getCurrentSetpoint() {
        return currentSetpoint;
    }

    /**
     * Returns the difference between the current setpoint and the actual RPM
     *
     * @return the RPM error
     */
    public double getRPMError() {
        return getCurrentSetpoint() - getVelocity();
    }

    /**
     * Sets the shooter RPM using velocity PID
     *
     * @param rpmSetpoint the RPM to set the shooter to
     */
    public void setShooterRpm(double rpmSetpoint) { //in rpm of the motors
        if (rpmSetpoint != 0) {
            masterPIDController.setReference(rpmSetpoint, ControlType.kVelocity);
            followerPIDController.setReference(rpmSetpoint, ControlType.kVelocity);
        } else {
            stopMotor();
        }
        currentSetpoint = rpmSetpoint;
    }

    /**
     * Sets the shooter to move regardless of the current robot state
     *
     * @param percent percentage to set shooter motors
     */
    public void overrideSetMotor(double percent) {
        shooterSparkMaster.set(percent);
        shooterSparkFollower.set(percent);
    }

    /**
     * Returns the manual RPM setpoint
     *
     * @return the manual RPM setpoint
     */
    public double getManualRPMSetpoint() {
        return manualRPMSetpoint;
    }

    /**
     * Sets the manual RPM setpoint
     *
     * @param manualRPMSetpoint the new manual RPM setpoint
     */
    public void setManualRPMSetpoint(double manualRPMSetpoint) {
        this.manualRPMSetpoint = manualRPMSetpoint;
    }

    /**
     * Increments the manual RPM setpoint
     *
     * @param rpm RPM to increment the setpoint by
     */
    public void changeManualRPMSetpoint(double rpm) {
        setManualRPMSetpoint(getManualRPMSetpoint() + rpm);
    }
}