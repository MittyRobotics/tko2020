package com.github.mittyrobotics.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class InfiniteMoveConveyor extends CommandBase {

    private double speed;

    public InfiniteMoveConveyor(double speed) {
        super();
        this.speed = speed;
        addRequirements(Conveyor.getInstance());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        Conveyor.getInstance().setConveyorSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
