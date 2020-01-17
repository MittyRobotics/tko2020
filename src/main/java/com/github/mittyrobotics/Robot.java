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

import com.github.mittyrobotics.drive.DriveTrainTalon;
import com.github.mittyrobotics.drive.RampingCommand;
import edu.wpi.first.wpilibj.Talon;

import com.github.mittyrobotics.autonomous.commands.TurretAimbot;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.util.OdometryRunnable;
import com.github.mittyrobotics.autonomous.util.TurretFieldManager;
import com.github.mittyrobotics.autonomous.vision.VisionManager;
import com.github.mittyrobotics.datatypes.motion.DifferentialDriveKinematics;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

  @Override
  public void robotInit() {
//    OI.getInstance().digitalInputControls();
    DriveTrainTalon.getInstance().initHardware();
  }

  @Override
  public void robotPeriodic() {
    //CommandScheduler.getInstance().run();

  }
=======

    public Robot() {
        super(0.02);
    }

    @Override
    public void robotInit() {
        OI.getInstance().digitalInputControls();
        //Setup track width for DifferentialDriveKinematics
        DifferentialDriveKinematics.getInstance().setTrackWidth(AutonConstants.DRIVETRAIN_TRACK_WIDTH);
        //Start Odometry runnable at frequency of 0.02
        OdometryRunnable.getInstance().start((long) 0.02);
        //Start turret field manager at frequency of 0.02
        TurretFieldManager.getInstance().start((long) 0.02);
        //Start vision manager at frequency of 0.02
        VisionManager.getInstance().start((long) 0.02);
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

  @Override
  public void autonomousInit() {
    CommandScheduler.getInstance().cancelAll();
    DriveTrainTalon.getInstance().resetEncoder();
    //DriveTrainTalon.getInstance().movePos(24, 24);
//    CommandScheduler.getInstance().schedule(new RampingCommand(110));
    }


    @Override
    public void autonomousInit() {


  @Override
  public void autonomousPeriodic() {
    DriveTrainTalon.getInstance().tankVelocity(5, 5);
//      System.out.println("Left encoder: "+DriveTrainTalon.getInstance().getLeftEncoder());
//      System.out.println("Right encoder: "+DriveTrainTalon.getInstance().getRightEncoder());
      System.out.println("Left Velocity: " + DriveTrainTalon.getInstance().getLeftEncoderVelocity());
      System.out.println("Right Velocity: " + DriveTrainTalon.getInstance().getRightEncoderVelocity());
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
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {

    }


    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().schedule(new TurretAimbot());
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {

    }
}