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

import com.github.mittyrobotics.autonomous.commands.ManualVisionTurretAim;
import com.github.mittyrobotics.autonomous.commands.VisionTurretAim;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.commands.AutoConveyorCommand;
import com.github.mittyrobotics.conveyor.commands.ConveyorOverrideOuttake;
import com.github.mittyrobotics.conveyor.commands.UnloadConveyorCommand;
import com.github.mittyrobotics.conveyor.commands.intake.ChangeIntakePistonStateCommand;
import com.github.mittyrobotics.conveyor.commands.intake.IntakeBallCommand;
import com.github.mittyrobotics.conveyor.commands.intake.OuttakeBallCommand;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.drivetrain.commands.ManualTankDriveCommand;
import com.github.mittyrobotics.drivetrain.commands.TankDriveCommand;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.shooter.commands.ChangeManualShooterSetpoint;
import com.github.mittyrobotics.shooter.commands.GainAdjustCommand;
import com.github.mittyrobotics.shooter.commands.ManualTurretCommand;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
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
     * OI {@link XboxController}
     */
    private XboxController xboxController;
    private XboxController xboxController2;

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
     * Returns the {@link XboxController} instance
     *
     * @return {@link XboxController} instance
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

    public void testSetupControls() {

//        DrivetrainSubsystem.getInstance().setDefaultCommand(new ManualTankDriveCommand());


        Button lowerSetpoint = new Button(() -> getXboxController().getBumperPressed(GenericHID.Hand.kLeft));
        Button raiseSetpoint = new Button(() -> getXboxController().getBumperPressed(GenericHID.Hand.kRight));
        lowerSetpoint.whenPressed(new ChangeManualShooterSetpoint(-50));
        raiseSetpoint.whenPressed(new ChangeManualShooterSetpoint(50));
    }

    /**
     * Setup controls
     */
    public void setupControls() {
        // DRIVER CONTROLS
        DrivetrainSubsystem.getInstance().setDefaultCommand(new ManualTankDriveCommand()); //TODO uncomment

        // OPERATOR CONTROLS
        // --------------------------

        // automatically index balls (no operator control by default)
        ConveyorSubsystem.getInstance().setDefaultCommand(new AutoConveyorCommand()); //TODO uncomment

        // automatic manual control for turret
        TurretSubsystem.getInstance().setDefaultCommand(new ManualTurretCommand());

        // automatically aim turret at vision target when left trigger held
        Button autoTurret = new Button(() -> getXboxController2().getTriggerAxis(GenericHID.Hand.kLeft) > 0.1);
        autoTurret.whenPressed(new VisionTurretAim());

        // automatically shoot and advance conveyor when right trigger held
        Button conveyorAndShoot = new Button(() -> getXboxController2().getTriggerAxis(GenericHID.Hand.kRight) > 0.1);
        conveyorAndShoot.whenPressed(new UnloadConveyorCommand());

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
        changeIntakePiston.whenPressed(new ChangeIntakePistonStateCommand());

//        Button increaseGain = new Button(() -> getXboxController2().getPOV() == 0);
//        increaseGain.whenPressed(new GainAdjustCommand(.1));

//        Button decreaseGain = new Button(() -> getXboxController2().getPOV() == 180);
//        decreaseGain.whenPressed(new GainAdjustCommand(-.1));

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