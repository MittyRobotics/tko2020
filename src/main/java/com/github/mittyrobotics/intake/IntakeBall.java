package com.github.mittyrobotics.intake;

import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
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
        if (ConveyorSubsystem.getInstance().getTotalBallCount() < 5) {
            if (IntakeSubsystem.getInstance().isExtended()) {
                IntakeSubsystem.getInstance().intakeBall(Constants.Intakespeed);
            } else {
                IntakeSubsystem.getInstance().extendIntake();
                IntakeSubsystem.getInstance().intakeBall(Constants.Intakespeed);
            }
        }
        else{
            if(!IntakeSubsystem.getInstance().isExtended()){
                IntakeSubsystem.getInstance().intakeBall(0);
            } else {
                IntakeSubsystem.getInstance().retractIntake();
                IntakeSubsystem.getInstance().intakeBall(0);
            }

        }


    }

    @Override
    public boolean isFinished(){
        return false;
    }


}
