package com.github.mittyrobotics;

import com.github.mittyrobotics.turret.TurretSubsystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {


  @Override
  public void robotInit() {
    OI.getInstance().digitalInputControls();
//        ShooterSubsystem.getInstance().initHardware();
    TurretSubsystem.getInstance().initHardware();

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
  public void teleopInit() {
//    DriveTrainTalon.getInstance().resetEconder();
//    DriveTrainTalon.getInstance().movePos(70, 70)
  }

  @Override
  public void teleopPeriodic() {
//    System.out.print(DriveTrainTalon.getInstance().getLeftEncoder());
//    System.out.print(DriveTrainTalon.getInstance().getRightEncoder());
  }
  @Override
  public void testInit(){

  }
  @Override
  public void testPeriodic(){

  }
}