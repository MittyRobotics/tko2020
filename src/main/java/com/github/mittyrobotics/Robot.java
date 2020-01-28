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

import com.github.mittyrobotics.autonomous.AutonSelector;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.util.OdometryManager;
import com.github.mittyrobotics.autonomous.vision.AutomatedTurretSuperstructure;
import com.github.mittyrobotics.autonomous.vision.Vision;
import com.github.mittyrobotics.colorwheel.ColorPiston;
import com.github.mittyrobotics.colorwheel.Spinner;
import com.github.mittyrobotics.datatypes.motion.DifferentialDriveKinematics;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.turret.TurretSubsystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private CommandGroupBase autonCommand;

    public Robot() {
        super(0.02);
    }

    @Override
    public void robotInit() {
        //Hardware initialization
        TurretSubsystem.getInstance().initHardware();
        ShooterSubsystem.getInstance().initHardware();
        DriveTrainTalon.getInstance().initHardware();
        ColorPiston.getInstance().initHardware();
        Spinner.getInstance().initHardware();
        //Setup DifferentialDriveKinematics
        DifferentialDriveKinematics.getInstance().setTrackWidth(AutonConstants.DRIVETRAIN_TRACK_WIDTH);
    }

    @Override
    public void robotPeriodic() {
        OdometryManager.getInstance().run();
        Vision.getInstance().run();
        AutomatedTurretSuperstructure.getInstance().run();
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void autonomousInit() {
        autonCommand = AutonSelector.getInstance().getSelectedAutonomousMode();
        CommandScheduler.getInstance().schedule(autonCommand);
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
        CommandScheduler.getInstance().cancelAll();
        OI.getInstance().digitalInputControls();
    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
        OI.getInstance().testButtons();
    }

    @Override
    public void testPeriodic() {

    }
}