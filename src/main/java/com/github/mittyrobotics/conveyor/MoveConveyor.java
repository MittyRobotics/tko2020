package com.github.mittyrobotics.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveConveyor extends CommandBase {

    private boolean isDone = false;

    public MoveConveyor(){
        super();
        addRequirements(ConveyorSubsystem.getInstance());
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (ConveyorSwitches.getInstance().getSwitch1()) {
            ConveyorSubsystem.getInstance().setConveyorSpeed(Constants.CONVEYOR_SPEED);
        } else {
            ConveyorSubsystem.getInstance().setConveyorSpeed(0);
            isDone = true;
        }
    }

    @Override
    public boolean isFinished(){
        return isDone;
    }

}
