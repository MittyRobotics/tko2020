package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ExtendIntake extends InstantCommand {
    public ExtendIntake(){
        super(()-> IntakePistonSubsystem.getInstance().extendPiston(), IntakePistonSubsystem.getInstance());
    }
}
