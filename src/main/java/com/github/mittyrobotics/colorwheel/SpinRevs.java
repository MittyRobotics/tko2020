package com.github.mittyrobotics.colorwheel;

import com.github.mittyrobotics.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.colorwheel.Constants.TICKS_PER_INCH;

public class SpinRevs extends CommandBase {
    public SpinRevs() {
        super();
        addRequirements(Spinner.getInstance());
    }
    @Override
    public void initialize(){
        //sets motor to fast velocity
        Spinner.getInstance().zeroEncoder();
        Spinner.getInstance().setMotorFast();


    }
    @Override
    public void execute(){

    }
    @Override
    public void end(boolean interrupted){
        //turns off motor, updates status
        Spinner.getInstance().setMotorOff();
        OI.getInstance().passedStage2();
    }
    @Override
    public boolean isFinished(){
        //returns 3 revs completed (one inch times 100 inches for each revolution times 3 revolutions)
        return Spinner.getInstance().getPosition() > TICKS_PER_INCH * 100 * 3.5;
    }
}
