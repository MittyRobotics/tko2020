package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class StopConveyorCommand extends InstantCommand {
    public StopConveyorCommand(){
        super(()-> ConveyorSubsystem.getInstance().stopMotor(), ConveyorSubsystem.getInstance());
    }
}