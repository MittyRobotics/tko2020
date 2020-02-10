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
import com.github.mittyrobotics.autonomous.vision.AutomatedTurretSuperstructure;
import com.github.mittyrobotics.autonomous.vision.Vision;
import com.github.mittyrobotics.datatypes.motion.DifferentialDriveKinematics;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import com.github.mittyrobotics.shooter.Shooter;
import com.github.mittyrobotics.turret.Turret;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

    @Override
    public void robotInit() {
        OI.getInstance().digitalInputControls();

        Shooter.getInstance().initHardware();
        Turret.getInstance().initHardware();
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
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {
        shooterControl();
        //drive();

        SmartDashboard.putNumber("turret_encoder", Turret.getInstance().getAngle());
        SmartDashboard.putNumber("rpm", Shooter.getInstance().getShooterSpeed());
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
            Shooter.getInstance().setShooterSpeed(Shooter.currentSetpoint + 50);
        }
        if (OI.getInstance().getJoystick1().getRawButtonPressed(7)) {
            Shooter.getInstance().setShooterSpeed(Shooter.currentSetpoint - 50);
        }

        if (OI.getInstance().getJoystick1().getRawButtonPressed(11)) {
            Shooter.getInstance().setShooterSpeed(Shooter.currentSetpoint + 10);
        }
        if (OI.getInstance().getJoystick1().getRawButtonPressed(10)) {
            Shooter.getInstance().setShooterSpeed(Shooter.currentSetpoint - 10);
        }
    }
}