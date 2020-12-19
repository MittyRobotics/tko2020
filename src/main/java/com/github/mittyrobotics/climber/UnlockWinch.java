package com.github.mittyrobotics.climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class UnlockWinch extends InstantCommand {
    public UnlockWinch(){
        super(()-> RatchetSubsystem.getInstance().unlockWinch(), RatchetSubsystem.getInstance());
    }
}
