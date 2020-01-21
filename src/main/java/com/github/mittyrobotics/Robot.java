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
    talon1 = new WPI_TalonFX(0);
    talon2 = new WPI_TalonFX(1);
    talon2.set(TalonFXControlMode.Follower, talon1.getDeviceID());
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

  }

  @Override
  public void teleopPeriodic() {
    talon1.set(TalonFXControlMode.PercentOutput, OI.getInstance().getJoystick1().getY());
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {

  }
}