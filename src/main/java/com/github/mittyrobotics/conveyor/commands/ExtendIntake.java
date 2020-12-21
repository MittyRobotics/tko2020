package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Extends the {@link IntakeSubsystem}
 */
public class ExtendIntake extends InstantCommand {

    /**
     * Extends the {@link IntakeSubsystem}
     *
     * Requires the {@link IntakePistonSubsystem}
     */
    public ExtendIntake(){
        super(()-> IntakePistonSubsystem.getInstance().extendPiston(), IntakePistonSubsystem.getInstance());
    }
}
