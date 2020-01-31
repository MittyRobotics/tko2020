package com.github.mittyrobotics;

import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.turret.Constants;
import com.github.mittyrobotics.turret.MagEncoderTesting;
import com.github.mittyrobotics.turret.TurretSubsystem;
import com.github.mittyrobotics.vision.Limelight;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

    private double turretSpeed, shooterSpeed;

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
        SmartDashboard.putNumber("rpm",ShooterSubsystem.getInstance().getShooterSpeed());
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
   //     System.out.println("angle value: " + (TurretSubsystem.getInstance().getEncoderValue()/Constants.TICKS_PER_ANGLE));
//        System.out.println(TurretSubsystem.getInstance().getAngle());
        if (OI.getInstance().getJoystick1().getRawButtonPressed(11)) {
            ShooterSubsystem.getInstance().setShooterSpeed(ShooterSubsystem.currentSetpoint+25);
        }
        if (OI.getInstance().getJoystick1().getRawButtonPressed(10)) {
            ShooterSubsystem.getInstance().setShooterSpeed(ShooterSubsystem.currentSetpoint-25);
        }

        turretSpeed = OI.getInstance().getJoystick1().getX();
        TurretSubsystem.getInstance().manualSetTurret(turretSpeed/3);


//
//        shooterSpeed = OI.getInstance().getJoystick1().getY();
//        ShooterSubsystem.getInstance().manualControl(shooterSpeed);
    }

    public double vision(){
        double yaw = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
        return 0.05 * yaw;
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