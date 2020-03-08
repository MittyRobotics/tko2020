package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.IntakePistonSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class RetractIntake extends InstantCommand {
    public RetractIntake(){
        super(()-> IntakePistonSubsystem.getInstance().retractPiston(), IntakePistonSubsystem.getInstance());
    }
}
