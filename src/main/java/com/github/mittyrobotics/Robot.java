package com.github.mittyrobotics;

import com.github.mittyrobotics.climber.Hooks;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  Compressor compressor;
  @Override
  public void robotInit() {
    /*System.out.println("robot initialize");
    OI.getInstance().digitalInputControls();
    Hooks.getInstance().initHardware();
    compressor = new Compressor();
    compressor.start();
    compressor.setClosedLoopControl(true);*/

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

  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {

  }
}