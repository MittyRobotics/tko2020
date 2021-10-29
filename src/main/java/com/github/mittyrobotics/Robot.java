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

<<<<<<< Updated upstream
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

=======
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * Robot Class to run the robot code (uses timed robot)
 */
>>>>>>> Stashed changes
public class Robot extends TimedRobot {

    @Override
    public void robotInit() {
<<<<<<< Updated upstream

=======
//        SubsystemManager.getInstance().addSubsystems(
//                ConveyorSubsystem.getInstance(),
//                DrivetrainSubsystem.getInstance(),
//                IntakePistonSubsystem.getInstance(),
//                IntakeSubsystem.getInstance(),
//                ShooterSubsystem.getInstance(),
//                TurretSubsystem.getInstance()
//        );
//        SubsystemManager.getInstance().initHardware();
//        Gyro.getInstance().initHardware();
//        Gyro.getInstance().calibrate();
//        Compressor.getInstance().initHardware();
//        RobotPositionTracker.getInstance().init(.02);
//        RobotPositionTracker.getInstance().calibrateEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
//
//        SmartDashboard.putNumber("shootGain", AutonConstants.RANGE_SHOOTER_GAIN);
//        Odometry.getInstance().zeroEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
//        Odometry.getInstance().zeroHeading(Gyro.getInstance().getAngle360());
//        Odometry.getInstance().zeroPosition();

//        input1 = new DigitalInput(6);
//        input2 = new DigitalInput(9);
//        input1 = new DigitalInput(4);
        Slider.getInstance().initHardware();
>>>>>>> Stashed changes
    }

    @Override
    public void robotPeriodic() {
<<<<<<< Updated upstream
        CommandScheduler.getInstance().run();
=======
//        CommandScheduler.getInstance().run();
//        SubsystemManager.getInstance().updateDashboard();
//
//        Odometry.getInstance().update(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition(), Gyro.getInstance().getAngle360());
>>>>>>> Stashed changes
    }

    @Override
    public void disabledInit() {
<<<<<<< Updated upstream

=======
//        DrivetrainSubsystem.getInstance().coast();
>>>>>>> Stashed changes
    }

    @Override
    public void disabledPeriodic(){

<<<<<<< Updated upstream
    }

    @Override
    public void autonomousInit() {

=======
//        DrivetrainSubsystem.getInstance().resetEncoder();
//        Gyro.getInstance().reset();
//        Odometry.getInstance().zeroEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
//        Odometry.getInstance().zeroHeading(Gyro.getInstance().getAngle360());
//        Odometry.getInstance().zeroPosition();
//
//        new Ball8AutonSWM().schedule();
//        new ShootingWhileMovingTurretControlLoop().schedule();
>>>>>>> Stashed changes
    }

    @Override
    public void autonomousPeriodic() {
<<<<<<< Updated upstream

=======
//        Vision.getInstance().run();
//        Autonomous.getInstance().run();
//        Autonomous.getInstance().updateDashboard();
//        RobotPositionTracker.getInstance().updateOdometry();
>>>>>>> Stashed changes
    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {

<<<<<<< Updated upstream
=======
//        new IntakeBallCommand(true, -0.8).schedule();

        double val = OI.getInstance().getXboxController().getX(GenericHID.Hand.kLeft) * 0.8;

        Slider.getInstance().setMotors(val);
>>>>>>> Stashed changes
    }

    @Override
    public void testInit(){

    }

    @Override
    public void testPeriodic(){

    }
}