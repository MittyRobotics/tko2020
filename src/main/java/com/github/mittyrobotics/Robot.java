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


import com.github.mittyrobotics.OI.OI;
import com.github.mittyrobotics.commands.LimitSwitchStopCommand;
import com.github.mittyrobotics.commands.LogCommand;
import com.github.mittyrobotics.commands.PidCommand;
import com.github.mittyrobotics.subsystems.Slider;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;

import java.util.ArrayList;

/**
 * Robot Class to run the robot code (uses timed robot)
 */

public class Robot extends TimedRobot {
    public static ArrayList<Double> timeArray = new ArrayList<>();
    public static ArrayList<Double> posArray = new ArrayList<>();
    public static ArrayList<Double> voltArray = new ArrayList<>();


    @Override
    public void robotInit() {



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

    }

    @Override
    public void robotPeriodic() {

        CommandScheduler.getInstance().run();

//        CommandScheduler.getInstance().run();
//        SubsystemManager.getInstance().updateDashboard();
//
//        Odometry.getInstance().update(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition(), Gyro.getInstance().getAngle360());

    }

    @Override



    public void disabledInit() {


//        DrivetrainSubsystem.getInstance().coast();

    }

    @Override
    public void disabledPeriodic(){


    }

    @Override
    public void autonomousInit() {


//        DrivetrainSubsystem.getInstance().resetEncoder();
//        Gyro.getInstance().reset();
//        Odometry.getInstance().zeroEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
//        Odometry.getInstance().zeroHeading(Gyro.getInstance().getAngle360());
//        Odometry.getInstance().zeroPosition();
//
//        new Ball8AutonSWM().schedule();
//        new ShootingWhileMovingTurretControlLoop().schedule();

    }

    @Override
    public void autonomousPeriodic() {



//        Vision.getInstance().run();
//        Autonomous.getInstance().run();
//        Autonomous.getInstance().updateDashboard();
//        RobotPositionTracker.getInstance().updateOdometry();

    }

    @Override
    public void teleopInit() {
        CommandScheduler.getInstance().schedule(new LogCommand());
    }

    @Override
    public void teleopPeriodic() {

        double val = OI.getInstance().getXboxController().getX(GenericHID.Hand.kLeft);
        Slider.getInstance().motorsWithLimitSwitch(OI.getInstance().getXboxController().getX(GenericHID.Hand.kLeft));

//        new IntakeBallCommand(true, -0.8).schedule();
//        System.out.println(Slider.getInstance().getMotors()[1].getSelectedSensorPosition());
        System.out.println(Slider.getInstance().getPosition());
        Slider.getInstance().getMotors()[0].getMotorOutputVoltage();



//        double val = OI.getInstance().getXboxController().getX(GenericHID.Hand.kLeft) * 0.8;

    }

    @Override
    public void testInit(){

    }

    @Override
    public void testPeriodic(){

    }
}