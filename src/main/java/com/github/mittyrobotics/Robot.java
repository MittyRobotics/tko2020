package com.github.mittyrobotics;

import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.turret.Constants;
import com.github.mittyrobotics.turret.MagEncoderTesting;
import com.github.mittyrobotics.turret.TurretSubsystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

    private double speed;

    @Override
    public void robotInit() {
//        ShooterSubsystem.getInstance().initHardware();
        TurretSubsystem.getInstance().initHardware();
//        TurretSubsystem.getInstance().manualSetTurret(.2);
        OI.getInstance().digitalInputControls();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
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
        CommandScheduler.getInstance().run();
        TurretSubsystem.getInstance().zeroEncoder();

    }

    @Override
    public void teleopPeriodic() {
        System.out.println("angle value: " + (TurretSubsystem.getInstance().getEncoderValue()/Constants.TICKS_PER_ANGLE));
//        System.out.println(TurretSubsystem.getInstance().getAngle());
//        if (OI.getInstance().getJoystick1().getTrigger()) {
//            ShooterSubsystem.getInstance().manualControl(.5);
//        } else {
//            ShooterSubsystem.getInstance().manualControl(0);
//        }

    }



    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
        speed = OI.getInstance().getJoystick1().getY();
        TurretSubsystem.getInstance().manualSetTurret(speed);
        System.out.println("angle value: " + (TurretSubsystem.getInstance().getEncoderValue()/ Constants.TICKS_PER_ANGLE));
//        System.out.println(TurretSubsystem.getInstance().getAngle());

    }
}