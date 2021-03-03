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

import com.github.mittyrobotics.autonomous.Autonomous;
import com.github.mittyrobotics.autonomous.FieldRotation;
import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.commands.FollowPath;
import com.github.mittyrobotics.autonomous.commands.SixBallAuton;
import com.github.mittyrobotics.autonomous.commands.TestCommandGroup;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.autonomous.util.RobotPositionTracker;
import com.github.mittyrobotics.colorwheel.ColorPistonSubsystem;
import com.github.mittyrobotics.colorwheel.SpinnerSubsystem;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.conveyor.commands.ManualSetConveyorCommand;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.datatypes.units.Conversions;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.drivetrain.commands.TankDriveCommand;
import com.github.mittyrobotics.motion.controllers.PathVelocityController;
import com.github.mittyrobotics.motion.controllers.PurePursuitController;
import com.github.mittyrobotics.motion.pathfollowing.PathFollower;
import com.github.mittyrobotics.motion.pathfollowing.PathFollowerProperties;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretEncoderUpdater;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.shooter.commands.*;
import com.github.mittyrobotics.util.Compressor;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.OI;
import com.github.mittyrobotics.util.SubsystemManager;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.*;

import java.util.Set;

/**
 * Robot Class to run the robot code (uses timed robot)
 */
public class Robot extends TimedRobot {

    /**
     * Command group for autonomous
     */
    Command autonCommandGroup;

    /**
     * Sets the Robot to loop at 20 ms cycle
     */
    public Robot() {
        super(0.02);
    }

    /**
     * Initializes all the hardware
     */
    @Override
    public void robotInit() {
        SubsystemManager.getInstance().addSubsystems(
                ColorPistonSubsystem.getInstance(),
                ConveyorSubsystem.getInstance(),
                DrivetrainSubsystem.getInstance(),
                IntakePistonSubsystem.getInstance(),
                IntakeSubsystem.getInstance(),
                ShooterSubsystem.getInstance(),
//                SpinnerSubsystem.getInstance(),
                TurretSubsystem.getInstance()
        );

        SubsystemManager.getInstance().initHardware();
        Gyro.getInstance().initHardware();

//        Compressor.getInstance().initHardware();
        DrivetrainSubsystem.getInstance().setMaxPercent(.3);

        Vision.getInstance();
        RobotPositionTracker.getInstance().init(getPeriod());
        Vision.getInstance().run();
        RobotPositionTracker.getInstance().calibrateEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
        RobotPositionTracker.getInstance().setOdometryTransform(Vision.getInstance().getLatestRobotTransformEstimate());
    }

    /**
     * Runs Scheduler for commands and updates the dashboard and OI
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        SubsystemManager.getInstance().updateDashboard();
        OI.getInstance().updateOI();
        Vision.getInstance().run();
        Vision.getInstance().updateDashboard();
        Autonomous.getInstance().updateDashboard();
        RobotPositionTracker.getInstance().updateOdometry();
    }

    /**
     * Brakes the drivetrain when disabling
     */
    @Override
    public void disabledInit() {
//        DrivetrainSubsystem.getInstance().brake();
        new InstantCommand(()->{TurretSubsystem.getInstance().setMotor(0);}, TurretSubsystem.getInstance());
        new InstantCommand(()->{ShooterSubsystem.getInstance().setShooterRpm(0); ShooterSubsystem.getInstance().stopMotor();}, ShooterSubsystem.getInstance());
        new InstantCommand(()->{DrivetrainSubsystem.getInstance().stopMotor();}, DrivetrainSubsystem.getInstance());
        new InstantCommand(()->{ConveyorSubsystem.getInstance().stopMotor();}, ConveyorSubsystem.getInstance());
        new InstantCommand(()->{IntakeSubsystem.getInstance().stopMotor();}, IntakeSubsystem.getInstance());
    }

    /**
     * Initializes and starts autonomous command
     */
    @Override
    public void autonomousInit() {
        new SixBallAuton().schedule();
    }

    /**
     * Stops autonomous command and initializes controls
     */
    @Override
    public void teleopInit() {
//        new TankDriveCommand().schedule();
//        CommandScheduler.getInstance().cancel(autonCommandGroup);
//        OI.getInstance().setupControls();
        TurretSubsystem.getInstance().setControlLoopMaxPercent(.5);
        new AutomatedTurretControlLoop().schedule();
        new AutomatedShooterControlLoop().schedule();
//
//        new ManualTurretCommand().schedule();

//        new SetShooterRpmCommand(2000).schedule();
        new ManualSetConveyorCommand(.5, .3).schedule();
    }

    /**
     * Function for initializing test code
     */
    @Override
    public void testInit() {
        Gyro.getInstance().initHardware();
        Vision.getInstance().run();
        RobotPositionTracker.getInstance().init(getPeriod());
        RobotPositionTracker.getInstance().calibrateEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
        RobotPositionTracker.getInstance().setOdometryTransform(Vision.getInstance().getLatestRobotTransformEstimate());

    }

    /**
     * Function for testing code
     */
    @Override
    public void testPeriodic() {

    }

}