package com.github.mittyrobotics.buffer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class LockBall extends CommandBase {
    private boolean isDone = false;

    public LockBall (){
        super();
        addRequirements(BufferSubsystem.getInstance());
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        BufferSubsystem.getInstance().bufferLock(Constants.TalonLockSpeed);
        isDone = BufferSubsystem.getInstance().isOptimalAngle() && BufferSubsystem.getInstance().isOptimalSpeed();

    }
    @Override
    public void end(boolean interrupted){
        CommandScheduler.getInstance().schedule(new ReleaseBall());
    }
    @Override
    public boolean isFinished(){
        return isDone;
    }
}
