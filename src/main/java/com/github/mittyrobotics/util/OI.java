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

package com.github.mittyrobotics.util;

import com.github.mittyrobotics.climber.commands.DeextendClimber;
import com.github.mittyrobotics.climber.commands.ExtendClimber;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakeRaiseSubsystem;
import com.github.mittyrobotics.conveyor.commands.*;
import com.github.mittyrobotics.drivetrain.commands.ArcadeDriveCommand;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.shooter.commands.ChangeManualShooterSetpoint;
import com.github.mittyrobotics.shooter.commands.ManualTurretCommand;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * OI Class to manage all controllers and input
 */
public class OI {
    /**
     * {@link OI} instance
     */
    private static OI instance;

    /**
     * OI {@link XboxWheel}
     */
    private XboxWheel xboxWheel;

    /**
     * OI {@link XboxController}
     */
    private XboxController xboxController;

    /**
     * OI {@link Joystick}s
     */
    private Joystick joystick1, joystick2;

    /**
     * Variable to store if the robot is in auto shoot mode
     */
    private boolean inAutoShootMode = false;

    /**
     * Returns an {@link OI} instance
     *
     * @return {@link OI} instance
     */
    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    /**
     * Returns the {@link XboxWheel} instance
     *
     * @return {@link XboxWheel} instance
     */
    public XboxWheel getXboxWheel() {
        if (xboxWheel == null) {
            xboxWheel = new XboxWheel(OIConstants.XBOX_WHEEL_ID);
        }
        return xboxWheel;
    }

    /**
     * Returns the {@link XboxController} instance
     *
     * @return {@link XboxWheel} instance
     */
    public XboxController getXboxController() {
        if (xboxController == null) {
            xboxController = new XboxController(OIConstants.XBOX_CONTROLLER_ID);
        }
        return xboxController;
    }

    /**
     * Returns the first {@link Joystick} instance
     *
     * @return first {@link Joystick} instance
     */
    public Joystick getJoystick1() {
        if (joystick1 == null) {
            joystick1 = new Joystick(OIConstants.JOYSTICK_1_ID);
        }
        return joystick1;
    }

    /**
     * Returns the second {@link Joystick} instance
     *
     * @return second {@link Joystick} instance
     */
    public Joystick getJoystick2() {
        if (joystick2 == null) {
            joystick2 = new Joystick(OIConstants.JOYSTICK_2_ID);
        }
        return joystick2;
    }

    public void initAuton() {
        Button intakeLimitSwitch =
                new Button(() -> !IntakeRaiseSubsystem.getInstance().getSwitch(0) && !IntakeRaiseSubsystem.getInstance().getSwitch(1));
        intakeLimitSwitch.whenPressed(new KeepIntakePosition());
    }

    /**
     * Setup controls
     */
    public void setupControls() {
        DrivetrainSubsystem.getInstance().setDefaultCommand(new ArcadeDriveCommand());

        TurretSubsystem.getInstance().setDefaultCommand(new ManualTurretCommand());

        //SpinnerSubsystem.getInstance().setDefaultCommand(new ManualSpinColorWheelCommand());

        ConveyorSubsystem.getInstance().setDefaultCommand(new AutoConveyorIndexCommand());

        /*Button spinWheelColor = new Button(() -> getJoystick1().getTrigger());
        spinWheelColor.whenPressed(new SpinWheelMacro());*/

        Button autoTurret = new Button(() -> getXboxController().getTriggerAxis(GenericHID.Hand.kLeft) > 0.5);
//        autoTurret.whenHeld(new MinimalVisionCommand());

        Button autoShoot =
                new Button(() -> getXboxController().getTriggerAxis(GenericHID.Hand.kRight) > 0.5);
//        autoShoot.whenHeld(new ShootMacro());

        Button manualShootSpeedUp = new Button(() -> getXboxController().getYButton());
        manualShootSpeedUp
                .whenPressed(new ChangeManualShooterSetpoint(100));

        Button manualShootSpeedDown = new Button(() -> getXboxController().getAButton());
        manualShootSpeedDown
                .whenPressed(new ChangeManualShooterSetpoint(-100));

        Button changeIntake = new Button(() -> getXboxController().getBButton());
        changeIntake.whenPressed(new ChangeIntakeStateCommand());

        Button intake = new Button(() -> getXboxController().getBumper(GenericHID.Hand.kLeft));
        intake.whenHeld(new IntakeBallCommand());

        Button outtake = new Button(() -> getXboxController().getBumper(GenericHID.Hand.kRight));
        outtake.whenHeld(new OuttakeRollersCommand());
        outtake.whenHeld(new ReverseConveyor());

        Button extendClimber = new Button(() -> getXboxController().getAButton());
        Button deextendClimber = new Button(() -> getXboxController().getBButton());
        extendClimber.whenPressed(new ExtendClimber());
        deextendClimber.whenPressed(new DeextendClimber());

        /*Button colorPistonUp = new Button(() -> getJoystick1().getY() > 0.5);
        colorPistonUp.whenPressed(new SpinnerUpCommand());

        Button colorPistonDown = new Button(() -> getJoystick1().getY() < -0.5);
        colorPistonDown.whenPressed(new SpinnerDownCommand());*/
    }

    /**
     * Tells if the shooter is in automatic shoot mode or manual shoot mode
     *
     * @return true if shooter is in auto mode
     */
    public boolean inAutoShootMode() {
        return inAutoShootMode || DriverStation.getInstance().isAutonomous();
    }

    /**
     * Updates OI variables
     */
    public void updateOI(){
        inAutoShootMode = getXboxController().getTriggerAxis(GenericHID.Hand.kLeft) > 0.5;
    }
}