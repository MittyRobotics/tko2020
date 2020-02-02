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
import edu.wpi.first.wpiutil.math.MathUtil;

public class ShooterSubsystem extends SubsystemBase {

    public static double currentSetpoint;
    private static ShooterSubsystem instance;
    private CANSparkMax spark1, spark2;
    private double bangSpeed = 0;
    private boolean inThreshold;

    private ShooterSubsystem() {
        super();
        setName("Shooter");
    }

    public static ShooterSubsystem getInstance() {
        if (instance == null) {
            instance = new ShooterSubsystem();
        }
        return instance;
    }



    public void initHardware() {
        double f = 0.00019;
        double p = 0.0001;
        double d = 0.00001;

        spark1 = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
        spark1.restoreFactoryDefaults();
        spark1.setInverted(true);
        spark1.getPIDController().setFF(f);
        spark1.getPIDController().setP(p);
        spark1.getPIDController().setD(d);

        spark2 = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless); //TODO: find device id
        spark2.restoreFactoryDefaults();
        spark2.getPIDController().setFF(f);
        spark2.getPIDController().setP(p);
        spark2.getPIDController().setD(d);
    }

    public void manualControl(double speed) {
        if (Math.abs(speed) >= 0.05) {
            spark1.set(speed);
            spark2.set(speed);
            System.out.println("Spark speed: " + spark1.getEncoder().getVelocity());
        } else {
            spark1.stopMotor();
            spark2.stopMotor();
        }
        System.out.println("Joystick speed: " + speed);
    }

    public void setPercent(double percent) { //in rpm of the motors
        spark1.set(percent);
        spark2.set(percent);
    }

    public void bangControl(double speed, double threshold) {
        if (!(Math.abs(speed - spark1.getEncoder().getVelocity()) < threshold)) {
            if ((spark1.getEncoder().getVelocity()) > speed) {
                bangSpeed -= Math.pow(MathUtil.clamp(
                        Math.abs(speed - spark1.getEncoder().getVelocity()) * .001, 0.02,
                        .04), 2);
                bangSpeed = MathUtil.clamp(bangSpeed, -1, 1);
                spark1.set(bangSpeed);
                spark2.set(bangSpeed);
            } else if ((spark1.getEncoder().getVelocity()) < speed) {
                bangSpeed += Math.pow(MathUtil.clamp(Math.abs(speed - spark1.getEncoder().getVelocity()) * .0005,
                        0.02,
                        .04), 2);
                bangSpeed = MathUtil.clamp(bangSpeed, -1, 1);
                spark1.set(bangSpeed);
                spark2.set(bangSpeed);
            }
        }
    }

    public double getShooterSpeed() {
        return (spark1.getEncoder().getVelocity() + spark2.getEncoder().getVelocity()) / 2;
    }

    public void setShooterSpeed(double setpoint) { //in rpm of the motors
        spark1.getPIDController().setReference(setpoint, ControlType.kVelocity);
        spark2.getPIDController().setReference(setpoint, ControlType.kVelocity);
        currentSetpoint = setpoint;
    }
}
