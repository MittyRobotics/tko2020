package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.BufferSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LockBallCommand extends CommandBase {

    public LockBallCommand() {
        super();
        addRequirements(BufferSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        BufferSubsystem.getInstance().bufferLock();
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
