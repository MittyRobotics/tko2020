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

package com.github.mittyrobotics.shooter;

import com.github.mittyrobotics.interfaces.ISubsystem;
import com.revrobotics.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Shooter subsystem to shoot balls
 */
public class Shooter extends SubsystemBase implements ISubsystem {
    /**
     * {@link Shooter} instance
     */
    private static Shooter instance;

    /**
     * Shooter setpoint speed
     */
    private double currentSetpoint;

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
     * Calls SubsystemBase constructor and names the subsystem "Shooter'
     */
    private Shooter() {
        super();
        setName("Shooter");
    }

    /**
     * Returns the {@link Shooter}'s {@link SubsystemBase} instance.
     *
     * @return the {@link Shooter}'s {@link SubsystemBase} instance.
     */
    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    /**
     * Initializes the shooter's hardware.
     */
    @Override
    public void initHardware() {
        shooterSparkMaster =
                new CANSparkMax(Constants.SHOOTER_SPARK_MASTER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterSparkMaster.restoreFactoryDefaults();
        shooterSparkMaster.setInverted(Constants.SHOOTER_SPARK_MASTER_INVERSION);
        this.masterPIDController = shooterSparkMaster.getPIDController();
        this.masterEncoder = shooterSparkMaster.getEncoder();
        masterPIDController.setFF(Constants.SHOOTER_F);
        masterPIDController.setP(Constants.SHOOTER_P);
        masterPIDController.setI(Constants.SHOOTER_I);
        masterPIDController.setD(Constants.SHOOTER_D);
//      masterEncoder.setInverted(Constants.SHOOTER_SPARK_MASTER_ENCODER_INVERSION);

        shooterSparkFollower =
                new CANSparkMax(Constants.SHOOTER_SPARK_FOLLOWER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterSparkFollower.restoreFactoryDefaults();
        shooterSparkFollower.setInverted(Constants.SHOOTER_SPARK_FOLLOWER_INVERSION);
        this.followerPIDController = shooterSparkFollower.getPIDController();
        this.followerEncoder = shooterSparkFollower.getEncoder();
        followerPIDController.setFF(Constants.SHOOTER_F);
        followerPIDController.setP(Constants.SHOOTER_P);
        followerPIDController.setI(Constants.SHOOTER_I);
        followerPIDController.setD(Constants.SHOOTER_D);
//      followerEncoder.setInverted(Constants.SHOOTER_SPARK_FOLLOWER_ENCODER_INVERSION);
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("shooter-rpm", getShooterRPM());
        SmartDashboard.putNumber("shooter-rpm-setpoint", getCurrentSetpoint());
    }

    /**
     * Gets the average RPM of both motors
     *
     * @return the shooter RPM
     */
    public double getShooterRPM() {
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
        return getCurrentSetpoint() - getShooterRPM();
    }

    /**
     * Sets the shooter speed using velocity PID
     *
     * @param setpoint the speed to set the shooter at
     */
    public void setShooterSpeed(double setpoint) { //in rpm of the motors
        if (setpoint != 0) {
            masterPIDController.setReference(setpoint, ControlType.kVelocity);
            followerPIDController.setReference(setpoint, ControlType.kVelocity);
        } else {
            shooterSparkMaster.set(0);
            shooterSparkFollower.set(0);
        }
        currentSetpoint = setpoint;
    }
    public void setShooterPercent(double percent){
        shooterSparkMaster.set(percent);
        shooterSparkFollower.set(percent);
        System.out.println((shooterSparkMaster.get()+shooterSparkFollower.get())/2.0);
    }
}
