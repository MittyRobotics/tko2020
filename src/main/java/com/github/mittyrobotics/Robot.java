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

import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.util.RobotPositionTracker;
import com.github.mittyrobotics.colorwheel.ColorPistonSubsystem;
import com.github.mittyrobotics.colorwheel.SpinnerSubsystem;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretEncoderUpdater;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.Compressor;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.OI;
import com.github.mittyrobotics.util.SubsystemManager;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

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

        Notifier turretEncoderNotifier = new Notifier(new TurretEncoderUpdater());
        turretEncoderNotifier.startPeriodic(0.02);
        
        //EXPERIMENTAL
//        Vision.getInstance();
//        RobotPositionTracker.getInstance();
    }

    /**
     * Runs Scheduler for commands and updates the dashboard and OI
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        SubsystemManager.getInstance().updateDashboard();
        OI.getInstance().updateOI();
        //EXPERIMENTAL
//        RobotPositionTracker.getInstance().run(Timer.getFPGATimestamp());
//        Vision.getInstance().run();
//        Vision.getInstance().updateDashboard();
    }

    /**
     * Brakes the drivetrain when disabling
     */
    @Override
    public void disabledInit() {
        DrivetrainSubsystem.getInstance().brake();
    }

    /**
     * Initializes and starts autonomous command
     */
    @Override
    public void autonomousInit() {
    }

    /**
     * Stops autonomous command and initializes controls
     */
    @Override
    public void teleopInit() {
        CommandScheduler.getInstance().cancel(autonCommandGroup);
        OI.getInstance().setupControls();
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