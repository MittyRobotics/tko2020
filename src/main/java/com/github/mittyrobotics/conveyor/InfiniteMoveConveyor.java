package com.github.mittyrobotics.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class InfiniteMoveConveyor extends CommandBase {

    private double speed;

    public InfiniteMoveConveyor(double speed){
        super();
        this.speed = speed;
        addRequirements(ConveyorSubsystem.getInstance());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        ConveyorSubsystem.getInstance().setConveyorSpeed(speed);
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}
