package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ClimberSubsystem;
import com.github.mittyrobotics.climber.RatchetSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Unlocks the {@link RatchetSubsystem}
 */
public class UnlockWinch extends InstantCommand {

    /**
     * Unlocks both winches for the {@link ClimberSubsystem}
     *
     * Command ends immediately
     *
     * Requires the {@link RatchetSubsystem}
     */
    public UnlockWinch(){
        super(()-> RatchetSubsystem.getInstance().unlockWinch(), RatchetSubsystem.getInstance());
    }
}
