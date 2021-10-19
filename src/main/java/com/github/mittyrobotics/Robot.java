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

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.github.mittyrobotics.autonomous.Autonomous;
import com.github.mittyrobotics.autonomous.Odometry;
import com.github.mittyrobotics.autonomous.RobotPositionTracker;
import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.commands.ManualVisionTurretAim;
import com.github.mittyrobotics.autonomous.commands.PathFollowingCommand;
import com.github.mittyrobotics.autonomous.commands.ShootingMovingTest;
import com.github.mittyrobotics.autonomous.commands.VisionTurretAim;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.test.TestCommand;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.conveyor.commands.UnloadConveyorCommand;
import com.github.mittyrobotics.conveyor.commands.intake.IntakeBallCommand;
import com.github.mittyrobotics.core.math.geometry.Rotation;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.core.math.geometry.Vector2D;
import com.github.mittyrobotics.core.math.spline.Path;
import com.github.mittyrobotics.drivetrain.DriveConstants;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.motion.profiles.PathTrajectory;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.shooter.commands.ShootingWhileMovingShooterControlLoop;
import com.github.mittyrobotics.shooter.commands.ShootingWhileMovingTurretControlLoop;
import com.github.mittyrobotics.util.Compressor;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.OI;
import com.github.mittyrobotics.util.SubsystemManager;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Button;

import javax.swing.text.Position;

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
//        RobotPositionTracker.getInstance().init(.02);
//        RobotPositionTracker.getInstance().calibrateEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
//
//        SmartDashboard.putNumber("shootGain", AutonConstants.RANGE_SHOOTER_GAIN);
//        Odometry.getInstance().zeroEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
//        Odometry.getInstance().zeroHeading(Gyro.getInstance().getAngle360());
//        Odometry.getInstance().zeroPosition();
    }

    /**
     * Runs Scheduler for commands and updates the dashboard and OI
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        SubsystemManager.getInstance().updateDashboard();

//        Odometry.getInstance().update(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition(), Gyro.getInstance().getAngle360());
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

//        DrivetrainSubsystem.getInstance().overrideSetMotor(0.4, 0.4);
//        autonCommand.schedule();
//        DrivetrainSubsystem.getInstance().resetEncoder();
//        Gyro.getInstance().reset();
//        Odometry.getInstance().zeroEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
//        Odometry.getInstance().zeroHeading(Gyro.getInstance().getAngle360());
//        Odometry.getInstance().zeroPosition();
//        Path path = Path.Companion.quinticHermitePath(new Transform[] {
//                new Transform(new Vector2D(0.0, 0.0), new Rotation(0)),
//                new Transform(new Vector2D(inches(100), inches(50)), new Rotation(degrees(45))),
//        });
//
//        PathTrajectory trajectory = new PathTrajectory(path, inches(100), inches(100),
//                0.5, 0, 0, 0, inches(50));
//
//        new PathFollowingCommand(trajectory).schedule();
//        DrivetrainSubsystem.getInstance().setMode(NeutralMode.Brake);
//        new ShootingWhileMovingTurretControlLoop().schedule();
    }

    @Override
    public void autonomousPeriodic() {
//        TurretSubsystem.getInstance().setMotor(TurretSubsystem.getInstance().turretPID(45));
//        DrivetrainSubsystem.getInstance().tankVelocity(50, 50);
    }

    /**
     * Stops autonomous command and initializes controls
     */
    @Override
    public void teleopInit() {

//        OI.getInstance().setupControls();

//        DrivetrainSubsystem.getInstance().setMode(NeutralMode.Brake);

//        new UnloadConveyorCommand(true).schedule();
//        new IntakeBallCommand(true).schedule();

//        new ShootingMovingTest().schedule();
//        new VisionTurretAim(true).schedule();
//        new ShootingWhileMovingTurretControlLoop().schedule();
    }

    @Override
    public void teleopPeriodic() {
//        Vision.getInstance().run();
//        Autonomous.getInstance().run();
//        Autonomous.getInstance().updateDashboard();
//        RobotPositionTracker.getInstance().updateOdometry();

//        DrivetrainSubsystem.getInstance().tankVelocity(50, 0);
//        ShooterSubsystem.getInstance().setShooterRpm(ShooterSubsystem.getInstance().getManualRPMSetpoint());

        if (OI.getInstance().getXboxController2().getTriggerAxis(GenericHID.Hand.kLeft) > DriveConstants.DRIVE_TRIGGER_THRESHOLD) {
            DrivetrainSubsystem.getInstance().brake();
        } else {
            double left = -OI.getInstance().getXboxController2().getY(GenericHID.Hand.kLeft);
            double right = -OI.getInstance().getXboxController2().getY(GenericHID.Hand.kRight);

            DrivetrainSubsystem.getInstance()
                    .tankDrive(left,
                            right, DriveConstants.DRIVE_THRESHOLD, 0.4);
        }
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
//        DrivetrainSubsystem.getInstance().tankVelocity(50, 50);
    }

}