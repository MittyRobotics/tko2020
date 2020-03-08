package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class StopConveyorCommand extends InstantCommand {
    public StopConveyorCommand(){
        super(()-> ConveyorSubsystem.getInstance().stopMotor(), ConveyorSubsystem.getInstance());
    }
}