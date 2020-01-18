package com.github.mittyrobotics.buffer;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ReleaseBall extends CommandBase {
    private boolean isDone = false;
    private double t, currentTime;

    public ReleaseBall (){
        super();
        addRequirements(BufferSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        t = Timer.getFPGATimestamp();
        currentTime = t;
    }

    @Override
    public void execute() {
        if ((currentTime-t)<0.2) {
            BufferSubsystem.getInstance().bufferRelease(Constants.TalonReleaseSpeed);
        } else {
            isDone = true;
        }
         currentTime = Timer.getFPGATimestamp();
    }

    @Override
    public boolean isFinished(){
        return isDone;
    }
}
