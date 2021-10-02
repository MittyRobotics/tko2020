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

import com.github.mittyrobotics.autonomous.commands.VisionTurretAim;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.conveyor.commands.*;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.drivetrain.commands.TankDriveCommand;
import com.github.mittyrobotics.shooter.commands.AutoShootMacro;
import com.github.mittyrobotics.shooter.commands.ManualTurretShooter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
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
    private XboxController xboxController2;

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

    public XboxController getXboxController2() {
        if (xboxController2 == null) {
            xboxController2 = new XboxController(OIConstants.XBOX_CONTROLLER_2_ID);
        }
        return xboxController2;
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

    public void updateControls(){
        // intake balls when right bumper held
        Button intake = new Button(() -> getXboxController2().getBumperPressed(GenericHID.Hand.kRight));
        intake.whenPressed(new IntakeBallCommand());


        // outtake balls when left bumper held
        Button outtake = new Button(() -> getXboxController2().getBumperPressed(GenericHID.Hand.kLeft));
        outtake.whenHeld(new OuttakeBallCommand());
    }

    /**
     * Setup controls
     */
    public void setupControls() {
        // DRIVER CONTROLS
        DrivetrainSubsystem.getInstance().setDefaultCommand(new TankDriveCommand());

        // OPERATOR CONTROLS
        // --------------------------

        // automatically index balls (no operator control by default)
        ConveyorSubsystem.getInstance().setDefaultCommand(new AutoConveyorCommand());

        // automatically aim turret at vision target when left trigger held
        Button autoTurret = new Button(() -> getXboxController2().getTriggerAxis(GenericHID.Hand.kLeft) > 0.1);
        autoTurret.whenPressed(new VisionTurretAim());

        // automatically shoot and advance conveyor when right trigger held
        Button conveyorAndShoot = new Button(() -> getXboxController2().getTriggerAxis(GenericHID.Hand.kRight) > 0.1);
        conveyorAndShoot.whenHeld(new AutoShootMacro());

        // intake balls when right bumper held
        Button intake = new Button(() -> getXboxController2().getBumperPressed(GenericHID.Hand.kRight));
        intake.whenPressed(new IntakeBallCommand());

        // outtake balls when left bumper held
        Button outtake = new Button(() -> getXboxController2().getBumperPressed(GenericHID.Hand.kLeft));
        outtake.whenPressed(new OuttakeBallCommand());

        // override conveyor indexing and intake to outtake, when X button pressed
        Button conveyorOverride = new Button(() -> getXboxController2().getXButton());
        conveyorOverride.whenPressed(new ConveyorOverrideOuttake());

        // extend/retract intake with Y button
        Button changeIntakePiston = new Button(() -> getXboxController2().getYButton());
        changeIntakePiston.whenPressed(new WaitExtendIntake(IntakePistonSubsystem.getInstance().getPistonExtended()));

        // lower ball count manually with left d-pad
        Button lowerBallCount = new Button(() -> getXboxController2().getPOV() == 270);
        lowerBallCount.whenPressed(new ChangeBallCount(-1));

        // increase ball count manually with right d-pad
        Button raiseBallCount = new Button(() -> getXboxController2().getPOV() == 90);
        raiseBallCount.whenPressed(new ChangeBallCount(1));

        // hold A to override turret and shooter. turret is controlled with left joystick x-axis, shooter is controlled by up/down d-pad
        Button overrideTurretShooter = new Button(() -> getXboxController2().getAButton());
        overrideTurretShooter.whenHeld(new ManualTurretShooter());




//        TurretSubsystem.getInstance().setDefaultCommand(new ManualTurretCommand());
//
//        Button autoTurret = new Button(() -> getXboxController().getTriggerAxis(GenericHID.Hand.kLeft) > 0.5);
//        autoTurret.whenHeld(new MinimalVisionCommand());
//
//        Button autoShoot =
//                new Button(() -> getXboxController().getTriggerAxis(GenericHID.Hand.kRight) > 0.5);
//        autoShoot.whenHeld(new ShootMacro());
//
//
//        Button manualShootSpeedUp = new Button(() -> getXboxController().getYButton());
//        manualShootSpeedUp
//                .whenPressed(new ChangeManualShooterSetpoint(100));
//
//        Button manualShootSpeedDown = new Button(() -> getXboxController().getAButton());
//        manualShootSpeedDown
//                .whenPressed(new ChangeManualShooterSetpoint(-100));
//
//
//        Button intake = new Button(() -> getXboxController().getBumper(GenericHID.Hand.kLeft));
//        intake.whenHeld(new IntakeBallCommand());
//
//        Button outtake = new Button(() -> getXboxController().getBumper(GenericHID.Hand.kRight));
//        outtake.whenHeld(new OuttakeRollersCommand());
//        outtake.whenHeld(new ReverseConveyor());

        /*Button colorPistonUp = new Button(() -> getJoystick1().getY() > 0.5);
        colorPistonUp.whenPressed(new SpinnerUpCommand());

        Button colorPistonDown = new Button(() -> getJoystick1().getY() < -0.5);
        colorPistonDown.whenPressed(new SpinnerDownCommand());

        Button spinWheelColor = new Button(() -> getJoystick1().getTrigger());
        spinWheelColor.whenPressed(new SpinWheelMacro());*/
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