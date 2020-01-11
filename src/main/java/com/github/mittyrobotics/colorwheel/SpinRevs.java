package com.github.mittyrobotics.colorwheel;

import com.github.mittyrobotics.OI;
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
        Spinner.getInstance().setMotorFast();

    }
    @Override
    public void execute(){
        count++;
    }
    @Override
    public void end(boolean interrupted){
        Spinner.getInstance().setMotorOff();
        OI.getInstance().passedStage2();
    }
    @Override
    public boolean isFinished(){
        return count > (ONE_REV_TIME_MS*3)/20;
    }
}
