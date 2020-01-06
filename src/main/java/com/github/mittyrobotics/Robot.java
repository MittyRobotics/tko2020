
/*
package com.github.mittyrobotics;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  @Override
  public void robotInit() {
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


*/






package com.amhsrobotics;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.motionprofile.TrapezoidalMotionProfile;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;


public class Robot extends TimedRobot {

  private Joystick joystick1;
  private DigitalInput switch1, switch2;
  private WPI_TalonSRX talon1;
  private Encoder encoder1;
  private double speed = 0;
  private PIDSubsystem pidthing;




  @Override
  public void robotInit() {
    talon1 = new WPI_TalonSRX(3);
    joystick1 = new Joystick(0);
    switch1 = new DigitalInput(0);
    switch2 = new DigitalInput(1);
    talon1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    pidthing = new TrapezoidalMotionProfile(0, 0, 10);


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
  public void teleopPeriodic() {

    if (joystick1.getRawButtonPressed(2)) {
      speed = speed + 0.25;
      if(speed == 1){
        speed = 0;
      }
    }
    System.out.println(speed);



    double direction = joystick1.getY();
    if(direction > 0.05){
      direction = 1;
    }
    else if(direction < -0.05){
      direction = -1;
    }
    else{
      direction = 0;
    }
    System.out.println("Speed" + speed);

    System.out.println("Switch1" + switch1.get());
    System.out.println("Switch2" + switch2.get());
    if (switch1.get()) {
      talon1.setSelectedSensorPosition(0);
      talon1.set(ControlMode.Position, 0.5 * 4393);

    }
    else if (switch2.get()) {
      System.out.println("Ticks" + talon1.getSelectedSensorPosition());
      talon1.set(ControlMode.Position, -0.5 * 4393);

    }

    if (!switch1.get() && !switch2.get()) {
      talon1.set(ControlMode.PercentOutput, direction * speed);
    }

    else {
      talon1.set(ControlMode.Position, 0.5 * 4939);
    }



      /*

      try{
        Thread.sleep(150);

      }
      catch(InterruptedException e){

      }

       */


  }

  @Override
  public void testInit(){

  }

  @Override
  public void testPeriodic() {

  }
}

