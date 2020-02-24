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
import com.github.mittyrobotics.autonomous.commands.TurretAimbotCommand;
import com.github.mittyrobotics.autonomous.util.OdometryManager;
import com.github.mittyrobotics.buffer.Buffer;
import com.github.mittyrobotics.buffer.LockBall;
import com.github.mittyrobotics.climber.Hooks;
import com.github.mittyrobotics.climber.Winch;
import com.github.mittyrobotics.colorwheel.ColorPiston;
import com.github.mittyrobotics.colorwheel.Spinner;
import com.github.mittyrobotics.conveyor.Conveyor;
import com.github.mittyrobotics.drive.DriveTrainFalcon;
import com.github.mittyrobotics.intake.Intake;
import com.github.mittyrobotics.shooter.Shooter;
import com.github.mittyrobotics.turret.AutoTurret;
import com.github.mittyrobotics.turret.Turret;
import com.github.mittyrobotics.util.Compressor;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

    Robot() {
        super(0.02);
    }

    @Override
    public void robotInit() {
        DriveTrainFalcon.getInstance().initHardware();
        Intake.getInstance().initHardware();
        Conveyor.getInstance().initHardware();
        Buffer.getInstance().initHardware();
        Shooter.getInstance().initHardware();
        Turret.getInstance().initHardware();
        ColorPiston.getInstance().initHardware();
        Spinner.getInstance().initHardware();
        Hooks.getInstance().initHardware();
        Winch.getInstance().initHardware();
        Gyro.getInstance().initHardware();
        Compressor.getInstance().initHardware();
    }

    @Override
    public void robotPeriodic() {
        //Run command scheduler
        CommandScheduler.getInstance().run();
        //Update dashboards
        DriveTrainFalcon.getInstance().updateDashboard();
        Intake.getInstance().updateDashboard();
        Conveyor.getInstance().updateDashboard();
        Buffer.getInstance().updateDashboard();
        Shooter.getInstance().updateDashboard();
        Turret.getInstance().updateDashboard();
        ColorPiston.getInstance().updateDashboard();
        Spinner.getInstance().updateDashboard();
        Hooks.getInstance().updateDashboard();
        Winch.getInstance().updateDashboard();
        Vision.getInstance().updateDashboard();
        OdometryManager.getInstance().updateDashboard();
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void autonomousInit() {
        Buffer.getInstance().setDefaultCommand(new LockBall());
        Turret.getInstance().setDefaultCommand(new AutoTurret());
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
        OI.getInstance().setupControls();
    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {

    }
}