package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.HooksSubsystem;
import com.github.mittyrobotics.subsystems.WinchLockSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LockClimberCommand extends CommandBase {
    public LockClimberCommand() {
        addRequirements(WinchLockSubsystem.getInstance(), HooksSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        WinchLockSubsystem.getInstance().lockWinch();
        HooksSubsystem.getInstance().pullHooks();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
