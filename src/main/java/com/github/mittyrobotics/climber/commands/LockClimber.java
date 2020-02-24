package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.Hooks;
import com.github.mittyrobotics.climber.WinchLock;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LockClimber extends CommandBase {
    public LockClimber() {
        addRequirements(WinchLock.getInstance(), Hooks.getInstance());
    }

    @Override
    public void initialize() {
        WinchLock.getInstance().lockWinch();
        Hooks.getInstance().pullHooks();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
