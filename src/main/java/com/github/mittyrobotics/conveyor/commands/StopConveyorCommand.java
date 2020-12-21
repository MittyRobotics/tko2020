package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Stops the conveyor from running
 */
public class StopConveyorCommand extends InstantCommand {

    /**
     * Stops the {@link ConveyorSubsystem} from moving
     *
     * Requires the {@link ConveyorSubsystem}
     */
    public StopConveyorCommand(){
        super(()-> ConveyorSubsystem.getInstance().stopMotor(), ConveyorSubsystem.getInstance());
    }
}