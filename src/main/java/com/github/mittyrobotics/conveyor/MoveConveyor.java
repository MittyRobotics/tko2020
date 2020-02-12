package com.github.mittyrobotics.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class MoveConveyor extends CommandBase {

    private double distance;

    public MoveConveyor(double distance){
        super();
        this.distance = distance;
        addRequirements(ConveyorSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        //ConveyorSubsystem.getInstance().moveConveyor(distance);
    }

    @Override
    public void execute() {
        //TODO this won't work logically, change it so the command just runs the conveyor to move a distance, and schedule the command based on conditions in periodic
        if (ConveyorSubsystem.getInstance().hasBallCountChanged()) {
            if (ConveyorSubsystem.getInstance().getTotalBallCount() < 5) {
                ConveyorSubsystem.getInstance().moveConveyor(distance);
            } else {
                ConveyorSubsystem.getInstance().setConveyorSpeed(0);
            }
        } else {
            ConveyorSubsystem.getInstance().setConveyorSpeed(0);
        }



//        if (ConveyorSwitches.getInstance().getSwitch1()) {
//            ConveyorSubsystem.getInstance().setConveyorSpeed(Constants.CONVEYOR_SPEED);
//        } else {
//            ConveyorSubsystem.getInstance().setConveyorSpeed(0);
//            isDone = true;
//        }
    }



    @Override
    public boolean isFinished(){
        return false;
    }

}
