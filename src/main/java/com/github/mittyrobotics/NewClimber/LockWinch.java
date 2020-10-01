package com.github.mittyrobotics.NewClimber;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class LockWinch extends InstantCommand {
    public LockWinch(){
        super(()-> RatchetSubsystem.getInstance().lockWinch(), RatchetSubsystem.getInstance());
    }
}
