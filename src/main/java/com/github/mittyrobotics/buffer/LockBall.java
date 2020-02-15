package com.github.mittyrobotics.buffer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class LockBall extends CommandBase {
    private boolean isDone = false;

    public LockBall() {
        super();
        addRequirements(Buffer.getInstance());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        Buffer.getInstance().bufferLock(-.5);
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
