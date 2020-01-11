package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinToColor extends CommandBase {
    protected SpinToColor() {
        super();
    }
    @Override
    public void initialize(){
        Spinner.getInstance().setMotorSlow();
    }
    @Override
    public void execute(){

    }
    @Override
    public void end(boolean interrupted){
        Spinner.getInstance().setMotorOff();
    }
    @Override
    public boolean isFinished(){
        return ;
    }
}
