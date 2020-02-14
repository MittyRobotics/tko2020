package com.github.mittyrobotics.buffer;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

//TODO need to test if release ball can happen all in one shot or if pauses are needed
public class ReleaseBall extends CommandBase {
    private boolean isDone = false;
//    private double t, currentTime;
    private double initialDistance, currentDistance;

    public ReleaseBall (){
        super();
        addRequirements(BufferSubsystem.getInstance());
    }

    @Override
    public void initialize() {
//        t = Timer.getFPGATimestamp();
//        currentTime = t;
        initialDistance = BufferSubsystem.getInstance().getBufferWheel().getSelectedSensorPosition();
        currentDistance = initialDistance;

    }

    @Override
    public void execute() { //using encoder to control distance it travels instead of time(not PID, since it needs constant speed, but just an encoder checker)

        if ((currentDistance- initialDistance)<Constants.RELEASE_SETPOINT) {
            BufferSubsystem.getInstance().bufferRelease(Constants.TalonReleaseSpeed);
        } else {
            isDone = true;
        }
        currentDistance = BufferSubsystem.getInstance().getBufferWheel().getSelectedSensorPosition();

//        if ((currentTime-t)<0.2) {
//            BufferSubsystem.getInstance().bufferRelease(Constants.TalonReleaseSpeed);
//        } else {
//            isDone = true;
//        }
//         currentTime = Timer.getFPGATimestamp();
    }

    @Override
    public boolean isFinished(){
        return isDone;
    }
}
