package com.github.mittyrobotics;

import com.github.mittyrobotics.buffer.Buffer;
import com.github.mittyrobotics.conveyor.Conveyor;
import com.github.mittyrobotics.conveyor.MoveConveyorAddBall;
import com.github.mittyrobotics.conveyor.MoveConveyorRemoveBall;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private int counter;
//  private DigitalInput ballSensor;

  @Override
  public void robotInit() {
//    OI.getInstance().digitalInputControls();
    Conveyor.getInstance().initHardware();
    Buffer.getInstance().initHardware();
    Conveyor.getInstance().resetEncoder();
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
//    counter = 0;
    CommandScheduler.getInstance().schedule(new MoveConveyorRemoveBall(7.5));
  }

  @Override
  public void autonomousPeriodic() {
    Conveyor.getInstance().manualSetConveyorSpeed(0.35);
    if(Conveyor.getInstance().getExitSwitch()){
      counter++;
    }
    if(counter > 70){
      Buffer.getInstance().manualBufferSpeed(0.35);
    } else {
      Buffer.getInstance().manualBufferSpeed(0.125);
    }
  }

  @Override
  public void teleopInit() {

  }

  @Override
  public void teleopPeriodic() {
//    System.out.println(ConveyorSubsystem.getInstance().getTotalBallCount());

    Conveyor.getInstance().manualSetConveyorSpeed(OI.getInstance().getJoystick1().getY());
    Buffer.getInstance().manualBufferSpeed(OI.getInstance().getJoystick1().getX());
////
////    System.out.println("entrance: " + Conveyor.getInstance().getEntranceSwitch());
////    System.out.println("exit " + Conveyor.getInstance().getExitSwitch());
//    System.out.println(Conveyor.getInstance().getPosition());




  }

  @Override
  public void testInit() {

  }

  @Override
  public void testPeriodic() {
    if(OI.getInstance().getJoystick1().getTriggerPressed()){
      CommandScheduler.getInstance().schedule(new MoveConveyorAddBall(8));
    } else if(OI.getInstance().getJoystick1().getRawButtonPressed(2)){
      CommandScheduler.getInstance().schedule(new MoveConveyorAddBall(7));
    } else if(OI.getInstance().getJoystick1().getRawButtonPressed(3)){
      CommandScheduler.getInstance().schedule(new MoveConveyorAddBall(7.5));
    }
//    Conveyor.getInstance().manualSetConveyorSpeed(-0.5);
//    Buffer.getInstance().manualBufferSpeed(-0.5);

  }
}