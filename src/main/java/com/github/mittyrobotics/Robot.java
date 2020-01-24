package com.github.mittyrobotics;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.drive.DriveTrainSparks;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import com.github.mittyrobotics.drive.JoystickDrive_CarSteering;
import com.github.mittyrobotics.drive.RampingCommand;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  public WPI_TalonFX talon1, talon2;

  @Override
  public void robotInit() {
    //    OI.getInstance().digitalInputControls();
    DriveTrainTalon.getInstance().initHardware();
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
//      System.out.println("Desired Vel" + DriveTrainTalon.getInstance().getLeftTalon().getClosedLoopTarget());
  }

  @Override
  public void teleopInit() {
    //    DriveTrainTalon.getInstance().resetEncoder();
//    DriveTrainTalon.getInstance().movePos(70, 70);
  }

  @Override
  public void teleopPeriodic() {
    //    System.out.print(DriveTrainTalon.getInstance().getLeftEncoder());
//    System.out.print(DriveTrainTalon.getInstance().getRightEncoder());
//    talon1.set(TalonFXControlMode.PercentOutput, OI.getInstance().getJoystick1().getY());
  }

  @Override
  public void testInit() {
    //CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {

  }
}