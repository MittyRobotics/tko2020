package com.github.mittyrobotics.colorwheel;

import com.github.mittyrobotics.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.colorwheel.Constants.ONE_REV_TIME_MS;

public class SpinRevs extends CommandBase {
    //counter
    int count;

    public SpinRevs() {
        super();
        addRequirements(Spinner.getInstance());
    }
    @Override
    public void initialize(){
        //sets motor to fast velocity
        Spinner.getInstance().setMotorFast();

    }
    @Override
    public void execute(){
        //each cycle is 20 ms
        count++;
    }
    @Override
    public void end(boolean interrupted){
        //turns off motor, updates status
        Spinner.getInstance().setMotorOff();
        OI.getInstance().passedStage2();
    }
    @Override
    public boolean isFinished(){
        //returns 3 revs completed (one rev time times 3 revs, divided by 20 ms for each cycle)
        return count > (ONE_REV_TIME_MS*3)/20;
    }
}
