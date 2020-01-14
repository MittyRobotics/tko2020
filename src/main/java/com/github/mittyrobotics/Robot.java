package com.github.mittyrobotics;

import com.github.mittyrobotics.shooter.Shooter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  @Override
  public void robotInit() {
    OI.getInstance().digitalInputControls();
    Shooter.getInstance().initHardware();
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
    if(OI.getInstance().getJoystick1().getTrigger()){
      Shooter.getInstance().manualControl(.5);
    } else {
      Shooter.getInstance().manualControl(0);
//      CommandScheduler.getInstance().run();
    }
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {

  }
}