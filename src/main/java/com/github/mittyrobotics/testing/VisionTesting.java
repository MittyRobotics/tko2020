/*
 * MIT License
 *
 * Copyright (c) 2019 Mitty Robotics (Team 1351)
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

import com.github.mittyrobotics.autonomous.AutomatedTurretSuperstructure;
import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.util.OdometryManager;
import com.github.mittyrobotics.buffer.Buffer;
import com.github.mittyrobotics.climber.Hooks;
import com.github.mittyrobotics.climber.Winch;
import com.github.mittyrobotics.colorwheel.ColorPiston;
import com.github.mittyrobotics.colorwheel.Spinner;
import com.github.mittyrobotics.conveyor.Conveyor;
import com.github.mittyrobotics.drive.DriveTrainFalcon;
import com.github.mittyrobotics.intake.Intake;
import com.github.mittyrobotics.shooter.Shooter;
import com.github.mittyrobotics.turret.Turret;
import com.github.mittyrobotics.util.Compressor;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class VisionTesting extends TimedRobot {

    @Override
    public void robotInit() {
        //Init hardware
        Shooter.getInstance().initHardware();
        Turret.getInstance().initHardware();

        //Set Odometry position to robot starting position and calibrate Odometry
        Gyro.getInstance().calibrate();
        Gyro.getInstance().reset();
    }

    @Override
    public void robotPeriodic() {
        Vision.getInstance().run();
        AutomatedTurretSuperstructure.getInstance().run();
        CommandScheduler.getInstance().run();

        //Update dashboards
        Shooter.getInstance().updateDashboard();
        Turret.getInstance().updateDashboard();
        Vision.getInstance().updateDashboard();
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
        //Shooter.getInstance().setShooterSpeed(3000);
    }

    @Override
    public void teleopPeriodic() {
        //OI.getInstance().shooterDebugControl();
    }

    @Override
    public void testInit() {

    }
}