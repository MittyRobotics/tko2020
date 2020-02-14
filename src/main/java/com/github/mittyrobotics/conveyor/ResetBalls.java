package com.github.mittyrobotics.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ResetBalls extends CommandBase {

    public ResetBalls(){
        super();
        addRequirements(ConveyorSubsystem.getInstance());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        ConveyorSubsystem.getInstance().resetBallCount(); //TODO should be in initialize
    }


    @Override
    public boolean isFinished(){
        return true;
    }

}
