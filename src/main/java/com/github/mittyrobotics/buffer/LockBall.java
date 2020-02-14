package com.github.mittyrobotics.buffer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

//TODO change command based on how conveyor is going to move
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
//        isDone = BufferSubsystem.getInstance().isOptimalAngle() && BufferSubsystem.getInstance().isOptimalSpeed(); //TODO just make command return false, make a command group later for shooting that involves releasing the balls
        isDone = false;
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
