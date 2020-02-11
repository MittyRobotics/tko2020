package com.github.mittyrobotics;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.github.mittyrobotics.music.TKOOrchestra;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

  WPI_TalonFX falcon1, falcon2;

  TKOOrchestra orchestra;

  @Override
  public void robotInit() {

    falcon1 = new WPI_TalonFX(0);
    falcon2 = new WPI_TalonFX(1);

    orchestra = new TKOOrchestra(falcon1, falcon2);
    orchestra.loadMusic("memories.chrp");
  }

  @Override
  public void robotPeriodic() {
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
    falcon1.set(TalonFXControlMode.PercentOutput, 0);
    falcon2.set(TalonFXControlMode.PercentOutput, 0);

    orchestra.play();
  }

  @Override
  public void teleopPeriodic() {
    if(!orchestra.isPlaying()) {
      orchestra.stop();
      orchestra.play();
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
