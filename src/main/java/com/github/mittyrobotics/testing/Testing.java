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

package com.github.mittyrobotics.testing;

import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.util.AutonSelector;
import com.github.mittyrobotics.autonomous.util.OdometryManager;
import com.github.mittyrobotics.commands.*;
import com.github.mittyrobotics.subsystems.*;
import com.github.mittyrobotics.util.Compressor;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

public class Testing extends TimedRobot {
    Command autonCommandGroup;
    public Testing() {
        super(0.02);
    }

    @Override
    public void robotInit() {
        DriveTrainSubsystem.getInstance().initHardware();
        IntakeSubsystem.getInstance().initHardware();
        ConveyorSubsystem.getInstance().initHardware();
        BufferSubsystem.getInstance().initHardware();
        ShooterSubsystem.getInstance().initHardware();
        TurretSubsystem.getInstance().initHardware();
        ColorPistonSubsystem.getInstance().initHardware();
        SpinnerSubsystem.getInstance().initHardware();
        IntakePistonSubsystem.getInstance().initHardware();
//        HooksSubsystem.getInstance().initHardware();
//        WinchSubsystem.getInstance().initHardware();
//        WinchLockSubsystem.getInstance().initHardware();
        Gyro.getInstance().initHardware();
        Compressor.getInstance().initHardware();
    }

    @Override
    public void robotPeriodic() {
        //Run command scheduler
        CommandScheduler.getInstance().run();
        //Update dashboards
//        DriveTrainSubsystem.getInstance().updateDashboard();
////        IntakeSubsystem.getInstance().updateDashboard();
//        ConveyorSubsystem.getInstance().updateDashboard();
//        BufferSubsystem.getInstance().updateDashboard();
//        ShooterSubsystem.getInstance().updateDashboard();
//        TurretSubsystem.getInstance().updateDashboard();
//        ColorPistonSubsystem.getInstance().updateDashboard();
//        SpinnerSubsystem.getInstance().updateDashboard();
////        HooksSubsystem.getInstance().updateDashboard();
////        WinchSubsystem.getInstance().updateDashboard();
////        WinchLockSubsystem.getInstance().updateDashboard();
//        Vision.getInstance().updateDashboard();
//        OdometryManager.getInstance().updateDashboard();
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void autonomousInit() {
//        BufferSubsystem.getInstance().setDefaultCommand(new LockBallCommand());
//        ShooterSubsystem.getInstance().setDefaultCommand(new SetShooterRpmCommand(0));
//        IntakeSubsystem.getInstance().setDefaultCommand(new StopRollersCommand());
//        autonCommandGroup = AutonSelector.getInstance().getSelectedAutonomousMode();
//        CommandScheduler.getInstance().schedule(autonCommandGroup);
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
//        IntakePistonSubsystem.getInstance().retractIntake();
//        CommandScheduler.getInstance().cancel(autonCommandGroup);
//        OI.getInstance().setupControls();
        DriveTrainSubsystem.getInstance().setDefaultCommand(new TankDriveCommand());
//        TurretSubsystem.getInstance().setDefaultCommand(new ManualTurretCommand());
        BufferSubsystem.getInstance().setDefaultCommand(new LockBallCommand());
        SpinnerSubsystem.getInstance().setDefaultCommand(new ManualSpinColorWheelCommand());
        OI.getInstance().testButtons();
    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void testInit() {
        Compressor.getInstance().start();
    }

    @Override
    public void testPeriodic() {
//        if(OI.getInstance().getController2().getBButton()){
//            IntakePistonSubsystem.getInstance().retractIntake();
//        } else if(OI.getInstance().getController2().getAButton()){
//            IntakePistonSubsystem.getInstance().extendIntake();
//        }
//        System.out.println(SpinnerSubsystem.getInstance().getColor());

    }
}