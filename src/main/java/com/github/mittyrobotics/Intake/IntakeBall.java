package com.github.mittyrobotics.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeBall extends CommandBase {


    public IntakeBall(){
        super();
        addRequirements(IntakeSubsystem.getInstance());
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if(IntakeSubsystem.getInstance().hasBall()){
            IntakeSubsystem.getInstance().intakeBall();
        }


    }

    @Override
    public boolean isFinished(){
        return false;
    }


}
