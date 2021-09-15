package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.IntakeRaiseSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Extends the {@link IntakeSubsystem}
 */
public class ExtendIntake extends InstantCommand {

    /**
     * Extends the {@link IntakeSubsystem}
     *
     * Requires the {@link IntakeRaiseSubsystem}
     */
    public ExtendIntake(){
        super(()-> IntakeRaiseSubsystem.getInstance().lowerIntake(), IntakeRaiseSubsystem.getInstance());
    }
}
