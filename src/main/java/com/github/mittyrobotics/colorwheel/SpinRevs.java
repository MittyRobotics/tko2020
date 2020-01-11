package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.colorwheel.Constants.ONE_REV_TIME_MS;

public class SpinRevs extends CommandBase {
    int count;
    public SpinRevs() {
        super();
        addRequirements(Spinner.getInstance());
    }
    @Override
    public void initialize(){
        Spinner.getInstance().setMotorOn();

    }
    @Override
    public void execute(){
        count++;
    }
    @Override
    public void end(boolean interrupted){
        Spinner.getInstance().setMotorOff();
    }
    @Override
    public boolean isFinished(){
        return count > (ONE_REV_TIME_MS*3.2)/20;
    }
}
