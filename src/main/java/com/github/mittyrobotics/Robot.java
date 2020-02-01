package com.github.mittyrobotics;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.github.mittyrobotics.drive.Drive;
import com.github.mittyrobotics.drive.DriveTrainFalcon;
import com.github.mittyrobotics.drive.DriveTrainSparks;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  //public WPI_TalonFX talon1, talon2;

  @Override
  public void robotInit() {

    DriveTrainFalcon.getInstance().initHardware();
    OI.getInstance().digitalInputControls();
    //DriveTrainTalon.getInstance().initHardware();
    //DriveTrainSparks.getInstance().initHardware();
//    talon1 = new WPI_TalonFX(0);
//    talon2 = new WPI_TalonFX(1);
//    talon2.set(TalonFXControlMode.Follower, talon1.getDeviceID());
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
//    CommandScheduler.getInstance().cancelAll();
//    DriveTrainTalon.getInstance().resetEncoder();
//    CommandScheduler.getInstance().cancelAll();
//    DriveTrainTalon.getInstance().resetEncoder();
    //DriveTrainTalon.getInstance().tankVelocity(-10, -10);
    //DriveTrainTalon.getInstance().movePos(24, 24);
    //CommandScheduler.getInstance().schedule(new RampingCommand());

  }

  @Override
  public void autonomousPeriodic() {
    //      System.out.println("Left encoder: "+DriveTrainTalon.getInstance().getLeftEncoder());
//      System.out.println("Right encoder: "+DriveTrainTalon.getInstance().getRightEncoder());
//    DriveTrainTalon.getInstance().velocityPIDFeedForward(30);
//    System.out.println("Left Velocity: " + DriveTrainTalon.getInstance().getLeftEncoderVelocity());
//    System.out.println("Right Velocity: " + DriveTrainTalon.getInstance().getRightEncoderVelocity());
//    DriveTrainTalon.getInstance().velocityPIDFeedForward(30);
//    System.out.println("Left Velocity: " + DriveTrainTalon.getInstance().getLeftEncoderVelocity());
//    System.out.println("Right Velocity: " + DriveTrainTalon.getInstance().getRightEncoderVelocity());
//      System.out.println("Desired Vel" + DriveTrainTalon.getInstance().getLeftTalon().getClosedLoopTarget());
    
  }

  @Override
  public void teleopInit() {

    //CommandScheduler.getInstance().cancelAll();

  }

  @Override
  public void teleopPeriodic() {

    //DriveTrainFalcon.getInstance().tankDrive(OI.getInstance().getJoystick1().getY()/3, OI.getInstance().getJoystick2().getY()/3);
  }

  @Override
  public void testInit() {
    //CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {

  }
}