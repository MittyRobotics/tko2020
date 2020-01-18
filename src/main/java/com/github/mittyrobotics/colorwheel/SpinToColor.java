package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.colorwheel.Constants.TICKS_PER_INCH;

public class SpinToColor extends CommandBase {
    int prevTicks;
    boolean onColor = false;
    boolean finished = false;

    public SpinToColor() {
        super();
        addRequirements(Spinner.getInstance());
    }
    @Override
    public void initialize(){

    }
    @Override
    public void execute(){
        if(Spinner.getInstance().matching()){
            prevTicks = Spinner.getInstance().getPosition();
            onColor = true;
        }
        if(onColor){
            if(Spinner.getInstance().getPosition() - prevTicks > TICKS_PER_INCH / 2) {
                finished = true;
            }
        }


    }
    @Override
    public void end(boolean interrupted){
        //turn off motor

        Spinner.getInstance().setMotorOff();
    }
    @Override
    public boolean isFinished(){
        return finished;
    }
}
