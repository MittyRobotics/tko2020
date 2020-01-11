package com.github.mittyrobotics;

import com.github.mittyrobotics.drive.DriveTrainTalon;
import com.github.mittyrobotics.drive.RampingCommand;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  @Override
  public void robotInit() {
//    OI.getInstance().digitalInputControls();
    DriveTrainTalon.getInstance().initHardware();
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
//    DriveTrainTalon.getInstance().resetEncoder();
    //DriveTrainTalon.getInstance().movePos(48, 48);
//    CommandScheduler.getInstance().schedule(new RampingCommand(110));

  }

  @Override
  public void autonomousPeriodic() {
//    System.out.println("Left encoder: "+DriveTrainTalon.getInstance().getLeftEncoder());
//    System.out.println("Right encoder: "+DriveTrainTalon.getInstance().getRightEncoder());
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

  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {

  }
}