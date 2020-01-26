package com.github.mittyrobotics;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.github.mittyrobotics.drive.Drive;
import com.github.mittyrobotics.drive.DriveTrainFalcon;
import com.github.mittyrobotics.drive.DriveTrainSparks;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  @Override
  public void robotInit() {

    DriveTrainFalcon.getInstance().initHardware();
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
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void teleopPeriodic() {
    DriveTrainFalcon.getInstance().tankDrive(OI.getInstance().getJoystick1().getY()/3, OI.getInstance().getJoystick2().getY()/3);
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {

  }
}