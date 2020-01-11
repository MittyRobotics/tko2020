package com.github.mittyrobotics;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.motionprofile.TrapezoidalMotionProfile;
import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {


  @Override
  public void robotInit() {
    Subsystem.getInstance().initHardware();
    OI.getInstance().digitalInputControls();

  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit(){
  }

  @Override
  public void teleopPeriodic(){

    if(OI.getInstance().getJoystick1().getRawButton(3)){
      Subsystem.getInstance().resetEncoder();
      // System.out.println("Talon position: " + SliderSubsystem.leftTalon.getSelectedSensorPosition());
      CommandScheduler.getInstance().cancelAll();
    } else {
      CommandScheduler.getInstance().run();
    }

    double speed = OI.getInstance().getJoystick1().getY();
    if(speed > 0.05 || speed < -0.05){
      Subsystem.getInstance().manualSpeed(speed);
    }
    else{
      Subsystem.getInstance().manualSpeed(0);
    }


  }


  @Override
  public void testInit(){
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {

  }
}

