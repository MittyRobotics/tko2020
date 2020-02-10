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

import com.github.mittyrobotics.autonomous.commands.EasyVisionCommand;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.util.AutonSelector;
import com.github.mittyrobotics.autonomous.util.OdometryManager;
import com.github.mittyrobotics.autonomous.vision.AutomatedTurretSuperstructure;
import com.github.mittyrobotics.autonomous.vision.Vision;
import com.github.mittyrobotics.datatypes.motion.DifferentialDriveKinematics;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.turret.TurretSubsystem;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private CommandGroupBase autonCommand;

    @Override
    public void robotInit() {
        OI.getInstance().digitalInputControls();

        ShooterSubsystem.getInstance().initHardware();
        TurretSubsystem.getInstance().initHardware();
        DriveTrainTalon.getInstance().initHardware();

        DifferentialDriveKinematics.getInstance().setTrackWidth(AutonConstants.DRIVETRAIN_TRACK_WIDTH);
    }

    @Override
    public void robotPeriodic() {
        //OdometryManager.getInstance().run();
        Vision.getInstance().run();
        AutomatedTurretSuperstructure.getInstance().run();
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void autonomousInit() {
//        autonCommand = AutonSelector.getInstance().getSelectedAutonomousMode();
//        CommandScheduler.getInstance().schedule(autonCommand);
    }

    @Override
    public void autonomousPeriodic() {
        //ShooterSubsystem.getInstance().setPercent(1);
        SmartDashboard.putNumber("turret_encoder", TurretSubsystem.getInstance().getAngle());
        SmartDashboard.putNumber("rpm", ShooterSubsystem.getInstance().getShooterSpeed());

    }

    @Override
    public void teleopInit() {
        ShooterSubsystem.getInstance().setShooterSpeed(4000);
        //ShooterSubsystem.getInstance().setPercent(1);
        //CommandScheduler.getInstance().schedule(new EasyVisionCommand());

    }

    @Override
    public void teleopPeriodic() {
        shooterControl();
        //drive();
//        TurretSubsystem.getInstance().manualSetTurret(OI.getInstance().getJoystick1().getY()/4);
        SmartDashboard.putNumber("turret_encoder", TurretSubsystem.getInstance().getAngle());
        SmartDashboard.putNumber("rpm", ShooterSubsystem.getInstance().getShooterSpeed());
        SmartDashboard.putNumber("field-rot",
                AutomatedTurretSuperstructure.getInstance().getFieldRelativeRotation().getHeading());
        SmartDashboard.putNumber("gyro", Gyro.getInstance().getAngle360());
    }

    private void drive() {
        double left = -OI.getInstance().getXboxController().getY(GenericHID.Hand.kLeft) / 2;
        double right = -OI.getInstance().getXboxController().getY(GenericHID.Hand.kRight) / 2;
        DriveTrainTalon.getInstance().tankDrive(left, right);
    }

    private void shooterControl() {
        if (OI.getInstance().getJoystick1().getRawButtonPressed(6)) {
            ShooterSubsystem.getInstance().setShooterSpeed(ShooterSubsystem.currentSetpoint + 50);
        }
        if (OI.getInstance().getJoystick1().getRawButtonPressed(7)) {
            ShooterSubsystem.getInstance().setShooterSpeed(ShooterSubsystem.currentSetpoint - 50);
        }

        if (OI.getInstance().getJoystick1().getRawButtonPressed(11)) {
            ShooterSubsystem.getInstance().setShooterSpeed(ShooterSubsystem.currentSetpoint + 10);
        }
        if (OI.getInstance().getJoystick1().getRawButtonPressed(10)) {
            ShooterSubsystem.getInstance().setShooterSpeed(ShooterSubsystem.currentSetpoint - 10);
        }
    }
}