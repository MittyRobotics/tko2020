package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ClimberSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.RunCommand;

/**
 * Manually move the {@link ClimberSubsystem} arms
 */
public class ManualArmCommand extends RunCommand {

    /**
     * Manually moves the {@link ClimberSubsystem} arms based on joysticks
     *
     * Requires the {@link ClimberSubsystem}
     */
    public ManualArmCommand() {
        super(
                () -> ClimberSubsystem.getInstance().overrideSetMotor(OI.getInstance().getJoystick1().getY(), OI.getInstance().getJoystick2().getY()),
                ClimberSubsystem.getInstance());
    }
}