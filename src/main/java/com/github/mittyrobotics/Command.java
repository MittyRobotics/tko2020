package com.github.mittyrobotics;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.github.mittyrobotics.datatypes.motion.MotionState;
import com.github.mittyrobotics.motionprofile.TrapezoidalMotionProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Command extends CommandBase {
    private double t;
    private TrapezoidalMotionProfile trapezoidalMotionProfile;
    public Command(){
        addRequirements(com.github.mittyrobotics.Subsystem.getInstance());
    }
    @Override
    public void initialize(){
        trapezoidalMotionProfile = Subsystem.getInstance().getPidthing();
        t = Timer.getFPGATimestamp();

    }
    @Override
    public void execute(){
        MotionState now = trapezoidalMotionProfile.getMotionStateAtTime(Timer.getFPGATimestamp()-t);
        Subsystem.getInstance().manualSlide(now.getPosition()* Constants.TICKS_PER_INCH);
    }
    @Override
    public void end(boolean interrupted){
        System.out.println("end");

    }
    @Override
    public boolean isFinished(){
        return false;
    }
}

