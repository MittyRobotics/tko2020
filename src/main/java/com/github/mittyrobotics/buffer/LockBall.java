package com.github.mittyrobotics.buffer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class LockBall extends CommandBase {

    public LockBall() {
        super();
        addRequirements(Buffer.getInstance());
    }

    @Override
    public void initialize() {
        Buffer.getInstance().bufferLock();
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
