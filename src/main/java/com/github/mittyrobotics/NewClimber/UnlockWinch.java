package com.github.mittyrobotics.NewClimber;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class UnlockWinch extends InstantCommand {
    public UnlockWinch(){
        super(()-> RatchetSubsystem.getInstance().unlockWinch(), RatchetSubsystem.getInstance());
    }
}
