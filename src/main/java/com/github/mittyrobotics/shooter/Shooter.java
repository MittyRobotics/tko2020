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

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

    public static double currentSetpoint; //TODO for testing purposes only right?
    private static Shooter instance;
    private CANSparkMax shooterSparkMaster, shooterSparkFollower;

    private Shooter() {
        super();
        setName("Shooter");
    }

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    public void initHardware() {

        shooterSparkMaster = new CANSparkMax(Constants.SHOOTER_SPARK_MASTER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterSparkMaster.restoreFactoryDefaults();
        shooterSparkMaster.getPIDController().setFF(Constants.SHOOTER_F);
        shooterSparkMaster.getPIDController().setP(Constants.SHOOTER_P);
        shooterSparkMaster.getPIDController().setI(Constants.SHOOTER_I);
        shooterSparkMaster.getPIDController().setD(Constants.SHOOTER_D);

        //TODO test to see if followers will work
        shooterSparkFollower = new CANSparkMax(Constants.SHOOTER_SPARK_FOLLOWER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterSparkFollower.restoreFactoryDefaults();
        shooterSparkFollower.setInverted(true);
        shooterSparkFollower.getPIDController().setFF(Constants.SHOOTER_F);
        shooterSparkFollower.getPIDController().setP(Constants.SHOOTER_P);
        shooterSparkFollower.getPIDController().setI(Constants.SHOOTER_I);
        shooterSparkFollower.getPIDController().setD(Constants.SHOOTER_D);
    }

    public void manualControl(double speed) {
        if (Math.abs(speed) >= 0.05) {
            shooterSparkMaster.set(speed);
            shooterSparkFollower.set(speed);
            System.out.println("Spark speed: " + shooterSparkMaster.getEncoder().getVelocity());
        } else {
            shooterSparkMaster.stopMotor();
            shooterSparkFollower.stopMotor();
        }
        System.out.println("Joystick speed: " + speed);
    }

    public void setPercent(double percent) { //in rpm of the motors
        shooterSparkMaster.set(percent);
        shooterSparkFollower.set(percent);
    }

    public double getShooterSpeed() {
        return (shooterSparkMaster.getEncoder().getVelocity() + shooterSparkFollower.getEncoder().getVelocity()) / 2;
    }

    public void setShooterSpeed(double setpoint) { //in rpm of the motors
        shooterSparkMaster.getPIDController().setReference(setpoint, ControlType.kVelocity);
        shooterSparkFollower.getPIDController().setReference(setpoint, ControlType.kVelocity);
        currentSetpoint = setpoint;
    }
}
