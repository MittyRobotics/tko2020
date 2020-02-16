package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinRevs extends CommandBase {
    private double initPos;
    public SpinRevs() {
        super();
        addRequirements(Spinner.getInstance());
    }
    @Override
    public void initialize(){
        //sets motor to fast velocity
        System.out.println("Starting");
        ColorPiston.getInstance().up();
        initPos = Spinner.getInstance().getRevolutions() ;
    }
    @Override
    public void execute(){
        Spinner.getInstance().setMotorFast();
    }
    @Override
    public void end(boolean interrupted){
        //turns off motor, updates status
        Spinner.getInstance().setMotorOff();
        ColorPiston.getInstance().down();
        System.out.println("END");
    }
    @Override
    public boolean isFinished(){
        return Spinner.getInstance().getRevolutions()-initPos > 3.5;
    }
}