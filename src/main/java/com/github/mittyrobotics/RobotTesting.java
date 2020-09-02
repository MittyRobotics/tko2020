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
import com.github.mittyrobotics.commands.StateSpaceFlywheelCommand;
import com.github.mittyrobotics.subsystems.*;
import com.github.mittyrobotics.util.Compressor;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import java.io.File;

public class RobotTesting extends TimedRobot {
    public RobotTesting() {
        super(0.02);
    }

    @Override
    public void robotInit() {
//        DriveTrainSubsystem.getInstance().initHardware();
//        IntakeSubsystem.getInstance().initHardware();
//        ConveyorSubsystem.getInstance().initHardware();
//        BufferSubsystem.getInstance().initHardware();
        ShooterSubsystem.getInstance().initHardware();
//        TurretSubsystem.getInstance().initHardware();
//        IntakePistonSubsystem.getInstance().initHardware();
//        Gyro.getInstance().initHardware();
//        Compressor.getInstance().initHardware();


    }

    @Override
    public void disabledPeriodic() {
//        DriveTrainSubsystem.getInstance().setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void robotPeriodic() {
        //Run command scheduler
        CommandScheduler.getInstance().run();
//        ShooterSubsystem.getInstance().updateDashboard();
    }

    @Override
    public void disabledInit() {
//        DriveTrainSubsystem.getInstance().setNeutralMode(NeutralMode.Brake);
//        DriveTrainSubsystem.getInstance().stopMotor();
//        TurretSubsystem.getInstance().setMotor(0);
    }

    @Override
    public void autonomousInit() {
        CommandScheduler.getInstance().schedule(new StateSpaceFlywheelCommand(4000));
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
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

    }
}