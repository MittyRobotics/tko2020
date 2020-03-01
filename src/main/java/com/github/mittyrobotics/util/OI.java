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

import com.github.mittyrobotics.commands.*;
import com.github.mittyrobotics.constants.OIConstants;
import com.github.mittyrobotics.constants.WheelColor;
import com.github.mittyrobotics.controls.controllers.XboxWheel;
import com.github.mittyrobotics.subsystems.*;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;

public class OI {
    private static OI instance;
    private XboxWheel xboxWheel;
    private XboxController xboxController;
    private Joystick joystick1;
    private Joystick joystick2;
    private XboxController controller2;

    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    public XboxWheel getXboxWheel() {
        if (xboxWheel == null) {
            xboxWheel = new XboxWheel(OIConstants.XBOX_WHEEL_ID);
        }
        return xboxWheel;
    }

    public XboxController getController2() {
        if (controller2 == null) {
            controller2 = new XboxController(0);
        }
        return controller2;
    }

    public XboxController getXboxController() {
        if (xboxController == null) {
            xboxController = new XboxController(OIConstants.XBOX_CONTROLLER_ID);
        }
        return xboxController;
    }

    public Joystick getJoystick1() {
        if (joystick1 == null) {
            joystick1 = new Joystick(OIConstants.JOYSTICK_1_ID);
        }
        return joystick1;
    }

    public Joystick getJoystick2() {
        if (joystick2 == null) {
            joystick2 = new Joystick(OIConstants.JOYSTICK_2_ID);
        }
        return joystick2;
    }

    public void setupControls() {
        DriveTrainSubsystem.getInstance().setDefaultCommand(new ArcadeDriveCommand());

        TurretSubsystem.getInstance().setDefaultCommand(new ManualTurretCommand());

        SpinnerSubsystem.getInstance().setDefaultCommand(new ManualSpinColorWheelCommand());

        Button spinWheelColor = new Button(()-> getJoystick1().getTrigger());
        spinWheelColor.whenPressed(new SpinWheelMacro());

        Button autoTurret = new Button(() -> getXboxController().getTriggerAxis(GenericHID.Hand.kLeft) > 0.5);
        autoTurret.whenHeld(new MinimalVisionCommand());

        Button autoShoot =
                new Button(() -> getXboxController().getTriggerAxis(GenericHID.Hand.kRight) > 0.5);
        autoShoot.whenHeld(new ShootMacro());


        Button manualShootSpeedUp = new Button(() -> getXboxController().getYButton());
        manualShootSpeedUp
                .whenPressed(new InstantCommand(() -> ShooterSubsystem.getInstance().changeManualRPMSetpoint(100)));

        Button manualShootSpeedDown = new Button(() -> getXboxController().getAButton());
        manualShootSpeedDown
                .whenPressed(new InstantCommand(() -> ShooterSubsystem.getInstance().changeManualRPMSetpoint(-100)));

        Button changeIntakePiston = new Button(() -> getXboxController().getBButton());
        changeIntakePiston.whenPressed(new ChangeIntakePistonCommand());

        Button intake = new Button(() -> getXboxController().getBumper(GenericHID.Hand.kLeft));
        intake.whenHeld(new IntakeBallCommand());

        Button outtake = new Button(() -> getXboxController().getBumper(GenericHID.Hand.kRight));
        outtake.whenHeld(new OuttakeRollersCommand());
        outtake.whenHeld(new ReverseConveyor());

        Button colorPistonUp = new Button(() -> getJoystick1().getY() > 0.5);
        colorPistonUp.whenPressed(new SpinnerUpCommand());
        Button colorPistonDown = new Button(() -> getJoystick1().getY() < -0.5);
        colorPistonDown.whenPressed(new SpinnerDownCommand());
    }

    public void testButtons(){
        //Drive
        Button brake = new Button(()->getController2().getStickButton(GenericHID.Hand.kLeft));
        brake.whenHeld(new BrakeDrivetrainCommand());

        ConveyorSubsystem.getInstance().setDefaultCommand(new ManualConveyorCommandTemp());

        //Color Wheel
        Button colorPistonUp = new Button(() -> getController2().getPOV() == 0);
        colorPistonUp.whenPressed(new SpinnerUpCommand());

        Button colorPistonDown = new Button(() -> getController2().getPOV() == 180);
        colorPistonDown.whenPressed(new SpinnerDownCommand());

        Button colorWheelSpinRevs = new Button(()->getController2().getBButton());
        colorWheelSpinRevs.whenPressed(new SpinWheelMacro());

        //Intaking
        Button intake = new Button(()->getController2().getBumper(GenericHID.Hand.kRight));
        intake.whenHeld(new IntakeBallCommand());
//        intake.whenHeld(new ConveyorFullEnter());
//        intake.whenHeld(new LockBallCommand());

        Button outtake = new Button(()->getController2().getBumper(GenericHID.Hand.kLeft));
        outtake.whenHeld(new OuttakeRollersCommand());
//        outtake.whenReleased(new StopRollersCommand());
//        outtake.whenHeld(new ReverseConveyor());
//        outtake.whenReleased(new UnloadConveyorCommand());

        Button changeIntakeState = new Button(()->getController2().getXButton());
        changeIntakeState.whenPressed(new ChangeIntakePistonState());
        //Shooting
        Button spinManual = new Button(()-> getController2().getXButton());
        spinManual.whenHeld(new ManualSpinnerButtonCommand(.5));

        Button setupShooter = new Button(()->getController2().getTriggerAxis(GenericHID.Hand.kLeft) > 0.5);
        setupShooter.whenHeld(new MinimalVisionCommand());
        setupShooter.whenReleased(new StopShooter());
        setupShooter.whenReleased(new ManualTurretButtonCommand(0));

        Button shoot = new Button(()->getController2().getTriggerAxis(GenericHID.Hand.kRight) > 0.5);
        shoot.whenHeld(new ShootMacro());
        shoot.whenReleased(new StopShooter());

        Button manualShootSpeedUp = new Button(() -> getController2().getYButton());
        manualShootSpeedUp
                .whenPressed(new InstantCommand(() -> ShooterSubsystem.getInstance().changeManualRPMSetpoint(100)));

        Button manualShootSpeedDown = new Button(() -> getController2().getAButton());
        manualShootSpeedDown
                .whenPressed(new InstantCommand(() -> ShooterSubsystem.getInstance().changeManualRPMSetpoint(-100)));

        //Turret
        Button manualTurretLeft = new Button(()-> getController2().getPOV() == 270);
        manualTurretLeft.whenHeld(new ManualTurretButtonCommand(-0.2));

        Button manualTurretRight = new Button(()-> getController2().getPOV() == 90);
        manualTurretRight.whenHeld(new ManualTurretButtonCommand(0.2));

        Button addBallCount = new Button(()->getController2().getStartButton());
        addBallCount.whenPressed(new InstantCommand(
                ()->ConveyorSubsystem.getInstance().updateBallCount(1)));

        Button subBallCount = new Button(()->getController2().getBackButton());
        subBallCount.whenPressed(new InstantCommand(()->ConveyorSubsystem.getInstance().updateBallCount(-1)));

        Button forceIn = new Button(()->getController2().getStickButton(GenericHID.Hand.kRight));
        forceIn.whenHeld(new ShoveBallCommand());

    }

    public void prospectControls(){
        DriveTrainSubsystem.getInstance().setDefaultCommand(new ArcadeDriveCommand());

        TurretSubsystem.getInstance().setDefaultCommand(new ManualTurretCommand());

        SpinnerSubsystem.getInstance().setDefaultCommand(new ManualSpinColorWheelCommand());

        ConveyorSubsystem.getInstance().setDefaultCommand(new ManualConveyorCommandTemp());

        //Color Wheel
        Button colorPistonUp = new Button(() -> getJoystick1().getY() < -0.5);
        colorPistonUp.whenPressed(new SpinnerUpCommand());

        Button colorPistonDown = new Button(() -> getJoystick1().getY() > 0.5);
        colorPistonDown.whenPressed(new SpinnerDownCommand());

        Button colorWheelSpinRevs = new Button(()->getJoystick1().getRawButton(1));
        colorWheelSpinRevs.whenPressed(new SpinRevsMacro());

        //Intaking
        Button intake = new Button(()->getXboxController().getBumper(GenericHID.Hand.kLeft));
        intake.whenHeld(new IntakeBallCommand());
//        intake.whenHeld(new ConveyorFullEnter());
//        intake.whenHeld(new LockBallCommand());


        Button outtake = new Button(()->getXboxController().getBumper(GenericHID.Hand.kRight));
        outtake.whenHeld(new OuttakeRollersCommand());
//        outtake.whenReleased(new StopRollersCommand());
        outtake.whenHeld(new ReverseConveyor());
        outtake.whenHeld(new SetShooterRpmCommand(-800));
        outtake.whenReleased(new UnloadConveyorCommand());
        outtake.whenReleased(new SetShooterRpmCommand(0));

        Button changeIntakeState = new Button(()->getXboxController().getXButton());
        changeIntakeState.whenPressed(new ChangeIntakePistonState());
        //Shooting

        Button setupShooter = new Button(()->getXboxController().getTriggerAxis(GenericHID.Hand.kLeft) > 0.5);
        setupShooter.whenHeld(new MinimalVisionCommand());
        setupShooter.whenReleased(new StopShooter());
        setupShooter.whenReleased(new ManualTurretButtonCommand(0));

        Button shoot = new Button(()->getXboxController().getTriggerAxis(GenericHID.Hand.kRight) > 0.5);
        shoot.whenHeld(new ShootMacro());
        shoot.whenReleased(new StopShooter());

        Button manualShootSpeedUp = new Button(() -> getXboxController().getYButton());
        manualShootSpeedUp
                .whenPressed(new InstantCommand(() -> ShooterSubsystem.getInstance().changeManualRPMSetpoint(100)));

        Button manualShootSpeedDown = new Button(() -> getXboxController().getAButton());
        manualShootSpeedDown
                .whenPressed(new InstantCommand(() -> ShooterSubsystem.getInstance().changeManualRPMSetpoint(-100)));

        //Turret

        Button addBallCount = new Button(()->getController2().getStartButton());
        addBallCount.whenPressed(new InstantCommand(()->ConveyorSubsystem.getInstance().updateBallCount(1)));

        Button subBallCount = new Button(()->getController2().getBackButton());
        subBallCount.whenPressed(new InstantCommand(()->ConveyorSubsystem.getInstance().updateBallCount(-1)));

        Button forceIn = new Button(()->getController2().getBButton());
        forceIn.whenHeld(new ShoveBallCommand());
    }


}