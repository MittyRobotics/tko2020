package com.github.mittyrobotics.conveyor2;

import edu.wpi.first.wpilibj2.command.RunCommand;

public class ConveyorMoveCommand extends RunCommand {
    public ConveyorMoveCommand(double speed) {
        super(()->Conveyor2Subsystem.getInstance().setMotor(speed), Conveyor2Subsystem.getInstance());
    }
}
