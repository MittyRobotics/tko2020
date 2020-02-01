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

package com.github.mittyrobotics;

import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.turret.TurretSubsystem;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

    private double turretSpeed, shooterSpeed;
    private double[][] RPMS = {
            {180, 3675},
            {193, 3650},
            {222, 3700},
            {231, 3750},
            {253, 3800},
            {283, 3860}
    };

    @Override
    public void robotInit() {
        ShooterSubsystem.getInstance().initHardware();
        TurretSubsystem.getInstance().initHardware();
//        TurretSubsystem.getInstance().manualSetTurret(.2);
        OI.getInstance().digitalInputControls();

    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        SmartDashboard.putNumber("rpm", ShooterSubsystem.getInstance().getShooterSpeed());
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
//        CommandScheduler.getInstance().run();
        //  TurretSubsystem.getInstance().zeroEncoder();
//        ShooterSubsystem.getInstance().initHardware();
        ShooterSubsystem.getInstance().setShooterSpeed(3850);
    }

    @Override
    public void teleopPeriodic() {
//        System.out.println("angle value: " + (TurretSubsystem.getInstance().getEncoderValue()/Constants.TICKS_PER_ANGLE));
//        System.out.println(TurretSubsystem.getInstance().getAngle());

//        if (OI.getInstance().getJoystick1().getRawButtonPressed(6)) {
//            ShooterSubsystem.getInstance().setShooterSpeed(ShooterSubsystem.currentSetpoint+50);
//        }
//        if (OI.getInstance().getJoystick1().getRawButtonPressed(7)) {
//            ShooterSubsystem.getInstance().setShooterSpeed(ShooterSubsystem.currentSetpoint-50);
//        }
//
//        if (OI.getInstance().getJoystick1().getRawButtonPressed(11)) {
//            ShooterSubsystem.getInstance().setShooterSpeed(ShooterSubsystem.currentSetpoint+10);
//        }
//        if (OI.getInstance().getJoystick1().getRawButtonPressed(10)) {
//            ShooterSubsystem.getInstance().setShooterSpeed(ShooterSubsystem.currentSetpoint-10);
//        }

        turretSpeed = OI.getInstance().getJoystick1().getX();
        TurretSubsystem.getInstance().manualSetTurret(vision());

        double pitch = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);
        double distance = computeVisionDistance(pitch);
        SmartDashboard.putNumber("vision_dist", distance);

        ShooterSubsystem.getInstance().setShooterSpeed(rpmEquation(distance));

    }

    public double vision() {
        double yaw = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
        return 0.1 * yaw;
    }

    private double computeVisionDistance(double pitch) {
        double LIMELIGHT_PITCH = 25;
        double LIMELIGHT_HEIGHT = 22;

        double TARGET_HEIGHT = 92;
        return (TARGET_HEIGHT - LIMELIGHT_HEIGHT) /
                Math.tan(Math.toRadians(pitch + LIMELIGHT_PITCH));
    }

    private double computeShooterRPMFromDistance(double distance) {
        double closest = 99999;
        double rpm = 0;
        for (int i = 0; i < RPMS.length; i++) {
            if (Math.abs(RPMS[i][0] - distance) < closest) {
                closest = Math.abs(RPMS[i][0] - distance);
                rpm = RPMS[i][1];
            }
        }
        return rpm;
    }

    private double rpmEquation(double distance) {
        return 9.766 * Math.pow(10, -3) * (distance * distance) - 2.4741 * distance + 3785.7830;
//        return 0.0199*Math.pow(distance,2) - 5.5182*distance + 3960.6993;
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {


        ShooterSubsystem.getInstance().manualControl(-1);
        System.out.println("RPM" + ShooterSubsystem.getInstance().getShooterSpeed());


//        speed = OI.getInstance().getJoystick1().getY();
//        TurretSubsystem.getInstance().manualSetTurret(speed);
//        System.out.println("angle value: " + (TurretSubsystem.getInstance().getEncoderValue()/ Constants.TICKS_PER_ANGLE));
////        System.out.println(TurretSubsystem.getInstance().getAngle());

    }
}