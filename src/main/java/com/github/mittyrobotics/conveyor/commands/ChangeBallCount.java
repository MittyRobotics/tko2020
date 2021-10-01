package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ChangeBallCount extends InstantCommand {

    public ChangeBallCount(int increase){
        super(()-> ConveyorSubsystem.getInstance().updateBallCount(increase), ConveyorSubsystem.getInstance());
    }
}
