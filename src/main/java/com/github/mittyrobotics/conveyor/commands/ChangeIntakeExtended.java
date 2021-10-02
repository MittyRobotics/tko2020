package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Extends the {@link IntakeSubsystem}
 */
public class ChangeIntakeExtended extends InstantCommand {

    /**
     * Extends the {@link IntakeSubsystem}
     *
     * Requires the {@link IntakePistonSubsystem}
     */
    public ChangeIntakeExtended(boolean extended) {
        super(()-> IntakePistonSubsystem.getInstance().setPistonExtended(extended), IntakePistonSubsystem.getInstance());
    }
}
