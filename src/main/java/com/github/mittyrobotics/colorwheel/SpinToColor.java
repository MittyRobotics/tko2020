package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinToColor extends CommandBase {
    //TODO need to set target to 2+ colors from what the sensor detects
    //TODO use encoder count (position) to discover transition + x time (still need to find out), then start tracking color
    public SpinToColor() {
        super();
        addRequirements(Spinner.getInstance());
    }
    @Override
    public void initialize(){
        //set motor to slow velocity
        Spinner.getInstance().setMotorSlow();
    }
    @Override
    public void execute(){
        //wait until matching
    }
    @Override
    public void end(boolean interrupted){
        //turn off motor
        Spinner.getInstance().setMotorOff();
    }
    @Override
    public boolean isFinished(){
        //return current color matches target
        return Spinner.getInstance().matching();
    }
}
