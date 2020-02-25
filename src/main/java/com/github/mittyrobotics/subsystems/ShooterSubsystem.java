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

package com.github.mittyrobotics.subsystems;

import com.github.mittyrobotics.util.interfaces.ISubsystem;
import com.github.mittyrobotics.constants.ShooterConstants;
import com.github.mittyrobotics.util.OI;
import com.revrobotics.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Shooter subsystem to shoot balls
 */
public class ShooterSubsystem extends SubsystemBase implements ISubsystem {
    /**
     * {@link ShooterSubsystem} instance
     */
    private static ShooterSubsystem instance;

    /**
     * Shooter setpoint speed
     */
    private double currentSetpoint;

    private double manualSetpoint;

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
    private ShooterSubsystem() {
        super();
        setName("Shooter");
    }

    /**
     * Returns the {@link ShooterSubsystem}'s {@link SubsystemBase} instance.
     *
     * @return the {@link ShooterSubsystem}'s {@link SubsystemBase} instance.
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
//      masterEncoder.setInverted(Constants.SHOOTER_SPARK_MASTER_ENCODER_INVERSION);

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
        manualSetpoint = 3000; //TODO set actual default value
//      followerEncoder.setInverted(Constants.SHOOTER_SPARK_FOLLOWER_ENCODER_INVERSION);
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("shooter-rpm", getShooterRPM());
        SmartDashboard.putNumber("shooter-rpm-setpoint", getCurrentSetpoint());
    }

    @Override
    public void periodic() {
        if (OI.getInstance().getXboxController().getYButtonPressed()) {
            manualSetpoint += 100; //TODO find increment
        } else if (OI.getInstance().getXboxController().getAButtonPressed()) {
            manualSetpoint -= 100; //TODO find decrement;
        }
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

    public void setShooterPercent(double percent) {
        shooterSparkMaster.set(percent);
        shooterSparkFollower.set(percent);
        System.out.println((shooterSparkMaster.get() + shooterSparkFollower.get()) / 2.0);
    }

    public double getManualSetpoint() {
        return manualSetpoint;
    }
}
