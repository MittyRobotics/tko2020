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
import com.github.mittyrobotics.autonomous.Odometry;
import com.github.mittyrobotics.autonomous.RobotPositionTracker;
import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.commands.*;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.conveyor.commands.AutoConveyorCommand;
import com.github.mittyrobotics.conveyor.commands.intake.IntakeBallCommand;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.Compressor;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.SubsystemManager;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.degrees;
import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;

/**
 * Robot Class to run the robot code (uses timed robot)
 */
public class Robot extends TimedRobot {
    /**
     * Sets the Robot to loop at 20 ms cycle
     */
//    TestCommand testCommand = new TestCommand(true, false);

    DigitalInput input1, input2;

    public Robot() {
        super(0.02);
    }

//    private final Command autonCommand = new Ball3Auton();

    /**
     * Initializes all the hardware
     */
    @Override
    public void robotInit() {
        SubsystemManager.getInstance().addSubsystems(
                ConveyorSubsystem.getInstance(),
                DrivetrainSubsystem.getInstance(),
                IntakePistonSubsystem.getInstance(),
                IntakeSubsystem.getInstance(),
                ShooterSubsystem.getInstance(),
                TurretSubsystem.getInstance()
        );
        SubsystemManager.getInstance().initHardware();
        Gyro.getInstance().initHardware();
        Gyro.getInstance().calibrate();
        Compressor.getInstance().initHardware();
        RobotPositionTracker.getInstance().init(.02);
        RobotPositionTracker.getInstance().calibrateEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
//
//        SmartDashboard.putNumber("shootGain", AutonConstants.RANGE_SHOOTER_GAIN);
        Odometry.getInstance().zeroEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
        Odometry.getInstance().zeroHeading(Gyro.getInstance().getAngle360());
        Odometry.getInstance().zeroPosition();

//        input1 = new DigitalInput(6);
//        input2 = new DigitalInput(9);

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
        Odometry.getInstance().zeroEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
        Odometry.getInstance().zeroHeading(Gyro.getInstance().getAngle360());
        Odometry.getInstance().zeroPosition();

        new Ball8AutonSWM().schedule();
//        new ShootingWhileMovingTurretControlLoop().schedule();
    }

    @Override
    public void autonomousPeriodic() {
        Vision.getInstance().run();
        Autonomous.getInstance().run();
        Autonomous.getInstance().updateDashboard();
        RobotPositionTracker.getInstance().updateOdometry();
    }

    /**
     * Stops autonomous command and initializes controls
     */
    @Override
    public void teleopInit() {
//        new ShootingMovingTest().schedule();
//        new VisionTurretAim(true).schedule();
////        new ShootingWhileMovingTurretControlLoop().schedule();
//
//        ConveyorSubsystem.getInstance().setDefaultCommand(new AutoConveyorCommand());
//        new IntakeBallCommand(true).schedule();

    }

    @Override
    public void teleopPeriodic() {
//        Vision.getInstance().run();
//        Autonomous.getInstance().run();
//        Autonomous.getInstance().updateDashboard();
//        RobotPositionTracker.getInstance().updateOdometry();

//        SmartDashboard.putBoolean("ID 6", input1.get());
//        SmartDashboard.putBoolean("ID 9", input2.get());
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