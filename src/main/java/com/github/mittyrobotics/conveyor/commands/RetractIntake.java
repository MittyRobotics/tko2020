package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.IntakeRaiseSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Retracts the {@link IntakeSubsystem}
 */
public class RetractIntake extends InstantCommand {

    /**
     * Retracts the {@link IntakeSubsystem}
     *
     * Requires the {@link IntakeRaiseSubsystem}
     */
    public RetractIntake(){
        super(()-> IntakeRaiseSubsystem.getInstance().raiseIntake(), IntakeRaiseSubsystem.getInstance());
    }
}
