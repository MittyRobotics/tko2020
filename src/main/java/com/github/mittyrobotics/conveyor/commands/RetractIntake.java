package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class RetractIntake extends InstantCommand {
    public RetractIntake(){
        super(()-> IntakePistonSubsystem.getInstance().retractPiston(), IntakePistonSubsystem.getInstance());
    }
}
