package com.github.mittyrobotics;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.github.mittyrobotics.drive.Drive;
import com.github.mittyrobotics.drive.DriveTrainFalcon;
import com.github.mittyrobotics.drive.DriveTrainSparks;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import edu.wpi.first.wpilibj.GenericHID;
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


  }

  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit() {

    //CommandScheduler.getInstance().cancelAll();
    //DriveTrainFalcon.getInstance().tankDrive(1, 1);
    //DriveTrainFalcon.getInstance().resetEncoder();

  }

  @Override
  public void teleopPeriodic() {
//    DriveTrainFalcon.getInstance().tankVelocity(-OI.getInstance().getXboxController().getY(GenericHID.Hand.kRight), -OI.getInstance().getXboxController().getY(GenericHID.Hand.kLeft));
//    System.out.println(-OI.getInstance().getXboxController().getY(GenericHID.Hand.kLeft));
//    System.out.println(-OI.getInstance().getXboxController().getY(GenericHID.Hand.kRight));
    //DriveTrainFalcon.getInstance().tankVelocity(50, 50);
//    System.out.println("Left Encoder Velocity: " + DriveTrainFalcon.getInstance().getLeftEncoderVelocity());
//    System.out.println("Right Encoder Velocity: " + DriveTrainFalcon.getInstance().getRightEncoderVelocity());
  }

  @Override
  public void testInit() {
    //CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {

  }
}