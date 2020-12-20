package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ClimberSubsystem;
import com.github.mittyrobotics.climber.RatchetSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Locks the {@link RatchetSubsystem}
 */
public class LockWinch extends InstantCommand {

    /**
     * Locks both winches for the {@link ClimberSubsystem}
     *
     * Command ends immediately
     *
     * Requires the {@link RatchetSubsystem}
     */
    public LockWinch(){
        super(()-> RatchetSubsystem.getInstance().lockWinch(), RatchetSubsystem.getInstance());
    }
}