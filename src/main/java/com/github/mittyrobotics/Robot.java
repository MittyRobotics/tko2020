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

import com.github.mittyrobotics.autonomous.*;
import com.github.mittyrobotics.autonomous.commands.*;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.drivetrain.commands.ManualTankDriveCommand;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.Compressor;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.OI;
import com.github.mittyrobotics.util.SubsystemManager;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;

/**
 * Robot Class to run the robot code (uses timed robot)
 */
public class Robot extends TimedRobot {
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
//                ConveyorSubsystem.getInstance(),
                DrivetrainSubsystem.getInstance()
//                IntakePistonSubsystem.getInstance(),
//                IntakeSubsystem.getInstance(),
//                ShooterSubsystem.getInstance(),
//                TurretSubsystem.getInstance()
        );
        SubsystemManager.getInstance().initHardware();
        Gyro.getInstance().initHardware();
        Gyro.getInstance().calibrate();
        Gyro.getInstance().reset();
//        Compressor.getInstance().initHardware();
//        RobotPositionTracker.getInstance().init(.02);
//        RobotPositionTracker.getInstance().calibrateEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
//        RobotPositionTracker.getInstance().setHeading(0, Gyro.getInstance().getAngle360());
        Odometry.getInstance().zeroEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
        Odometry.getInstance().zeroHeading(Gyro.getInstance().getAngle360());
        Odometry.getInstance().zeroPosition();

    }

    /**
     * Runs Scheduler for commands and updates the dashboard and OI
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        SubsystemManager.getInstance().updateDashboard();

        Odometry.getInstance().update(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition(), Gyro.getInstance().getAngle360());
    }

    /**
     * Brakes the drivetrain when disabling
     */
    @Override
    public void disabledInit() {
        DrivetrainSubsystem.getInstance().coast();
    }

    /**
     * Initializes and starts autonomous command
     */
    @Override
    public void autonomousInit() {

        DrivetrainSubsystem.getInstance().resetEncoder();
        Gyro.getInstance().reset();
//        RobotPositionTracker.getInstance().calibrateEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
//        RobotPositionTracker.getInstance().setHeading(0, Gyro.getInstance().getAngle360());
        Odometry.getInstance().zeroEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
        Odometry.getInstance().zeroHeading(Gyro.getInstance().getAngle360());
        Odometry.getInstance().zeroPosition();

//        new Ball8AutonSWM().schedule();

        QuinticHermiteSpline spline = new QuinticHermiteSpline(
            new Pose2D(0, 0, 0),
            new Pose2D(inches(150), inches(50), 0)
        );


//        RamsetePath path = new RamsetePath(spline,
//                40 * Path.TO_METERS, 40 * Path.TO_METERS,
//                50 * Path.TO_METERS, 100 * Path.TO_METERS,
//                0 * Path.TO_METERS, 0 * Path.TO_METERS
//        );


//        RamsetePathFollowingCommand pathCommand = new RamsetePathFollowingCommand(path, true);

//        pathCommand.schedule();

        RamsetePathGroup ramsetePathGroup = new RamsetePathGroup(
                new Pose2D(0, 0, 0),
                new Pose2D(inches(50), inches(30), 0),
                3
        );

        ramsetePathGroup.schedule();
    }

    @Override
    public void autonomousPeriodic() {
//        Vision.getInstance().run();
//        Autonomous.getInstance().run();
//        Autonomous.getInstance().updateDashboard();
//        RobotPositionTracker.getInstance().updateOdometry();
    }

    /**
     * Stops autonomous command and initializes controls
     */
    @Override
    public void teleopInit() {
//        OI.getInstance().setupControls();
        DrivetrainSubsystem.getInstance().setDefaultCommand(new ManualTankDriveCommand());
    }

    @Override
    public void teleopPeriodic() {
//        Vision.getInstance().run();
//        Autonomous.getInstance().run();
//        Autonomous.getInstance().updateDashboard();
//        RobotPositionTracker.getInstance().updateOdometry();
    }

    /**
     * Function for initializing test code
     */
    @Override
    public void testInit() {

    }

    /**
     * Function for testing code
     */
    @Override
    public void testPeriodic() {
    }

}