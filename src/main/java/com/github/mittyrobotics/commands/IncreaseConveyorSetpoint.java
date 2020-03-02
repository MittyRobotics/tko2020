package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class IncreaseConveyorSetpoint extends InstantCommand {
    public IncreaseConveyorSetpoint(){
        super(()-> ConveyorSubsystem.getInstance().increaseSetpoint(), ConveyorSubsystem.getInstance());
    }
}
