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

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.modes.EightBallAuton;
import com.github.mittyrobotics.autonomous.util.OdometryManager;
import com.github.mittyrobotics.commands.ArcadeDriveCommand;
import com.github.mittyrobotics.commands.AutoConveyorIndexCommand;
import com.github.mittyrobotics.datatypes.motion.DifferentialDriveKinematics;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.subsystems.*;
import com.github.mittyrobotics.util.Compressor;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Testing extends TimedRobot {
    Command autonCommandGroup;

    public Testing() {
        super(0.02);
    }

    @Override
    public void robotInit() {
        Odometry.getInstance().setTransform(new Transform(), 0);
        DifferentialDriveKinematics.getInstance().setTrackWidth(27);
        DriveTrainSubsystem.getInstance().initHardware();
        IntakeSubsystem.getInstance().initHardware();
        ConveyorSubsystem.getInstance().initHardware();
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
    public void disabledPeriodic() {
        DriveTrainSubsystem.getInstance().setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void robotPeriodic() {
        //Run command scheduler
        CommandScheduler.getInstance().run();
        OdometryManager.getInstance().run();
        Vision.getInstance().run(false);
        //AutomatedTurretSuperstructure.getInstance().run();
        //Update dashboards
        DriveTrainSubsystem.getInstance().updateDashboard();
        OdometryManager.getInstance().updateDashboard();
////        IntakeSubsystem.getInstance().updateDashboard();
        ConveyorSubsystem.getInstance().updateDashboard();
        ShooterSubsystem.getInstance().updateDashboard();
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
        DriveTrainSubsystem.getInstance().setNeutralMode(NeutralMode.Brake);
        DriveTrainSubsystem.getInstance().stopMotor();
        TurretSubsystem.getInstance().setMotor(0);
    }

    @Override
    public void autonomousInit() {
////        IntakeSubsystem.getInstance().setDefaultCommand(new IntakeBallShootingCommand());
//        Odometry.getInstance().setTransform(new Transform(0,0,0), Gyro.getInstance().getAngle());
//        new TestConveyorAuto().schedule();
////        CommandScheduler.getInstance().schedule(new SixBallAuton());
////
////        new IntakeBallCommand().schedule();
//////        new Bowling().schedule();
////        new RunCommand(()->ConveyorSubsystem.getInstance().setMotor(1), ConveyorSubsystem.getInstance()).schedule();
////        new RunCommand(()->ShooterSubsystem.getInstance().setShooterRpm(3000), ShooterSubsystem.getInstance()).schedule();
//////        ConveyorSubsystem.getInstance().setDefaultCommand(new ConveyorAutonCommand());

        new EightBallAuton().schedule();
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
        CommandScheduler.getInstance().cancelAll();
        ConveyorSubsystem.getInstance().setDefaultCommand(new AutoConveyorIndexCommand());
        DriveTrainSubsystem.getInstance().setNeutralMode(NeutralMode.Coast);
        CommandScheduler.getInstance().schedule(new ArcadeDriveCommand());
        ConveyorSubsystem.getInstance().resetBallCount();
        OI.getInstance().testButtons2();
    }

    @Override
    public void teleopPeriodic() {
//        System.out.println(Vision.getInstance().getLatestVisionTarget().getObserverDistanceToTarget());
//        ConveyorSubsystem.getInstance().periodic2();
    }

    @Override
    public void testInit() {
        Compressor.getInstance().start();

    }

    @Override
    public void testPeriodic() {
        System.out.println("Testing: " + Vision.getInstance().getLatestVisionTarget().getObserverDistanceToTarget());
        ConveyorSubsystem.getInstance().setMotor(-1);
//        ShooterSubsystem.getInstance().setMotor(1);
//        DriveTrainSubsystem.getInstance()
//                .tankDrive(0, OI.getInstance().getXboxController().getY(GenericHID.Hand.kLeft));
//        ShooterSubsystem.getInstance().setShooterPercent(1);
//        if(OI.getInstance().getController2().getBButton()){
//            IntakePistonSubsystem.getInstance().retractIntake();
//        } else if(OI.getInstance().getController2().getAButton()){
//            IntakePistonSubsystem.getInstance().extendIntake();
//        }
//        System.out.println(SpinnerSubsystem.getInstance().getColor());

    }
}