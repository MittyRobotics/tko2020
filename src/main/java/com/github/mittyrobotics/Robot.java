/*
 * MIT License
 *
 * Copyright (c) 2020 Mitty Robotics (Team 1351)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mittyrobotics;


import com.github.mittyrobotics.turret.TurretSubsystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;

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
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        OdometryRunnable.getInstance().run();
        TurretFieldManager.getInstance().run();
        Vision.getInstance().run();
    }

    @Override
    public void teleopInit() {
        CommandScheduler.getInstance().schedule(new RunCommand(
                () -> DriveTrainTalon.getInstance().tankDrive(-OI.getInstance().getXboxController().getY(GenericHID.Hand
                        .kLeft), -OI.getInstance().getXboxController().getY(
                        GenericHID.Hand.kRight)), DriveTrainTalon.getInstance()));
    }

    @Override
    public void teleopPeriodic() {
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