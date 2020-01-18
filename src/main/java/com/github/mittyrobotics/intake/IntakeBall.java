package com.github.mittyrobotics.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeBall extends CommandBase {


    public IntakeBall(){
        super();
        addRequirements(IntakeSubsystem.getInstance());
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() { //TODO Need to have else to make it stop
        if(IntakeSubsystem.getInstance().hasBall()){
            IntakeSubsystem.getInstance().intakeBall();
        }


    }

    @Override
    public boolean isFinished(){
        return false;
    }


}
