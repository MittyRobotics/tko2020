package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.ClimberPistonSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class LiftClimberCommand extends InstantCommand {
    public LiftClimberCommand(){
        super(()-> ClimberPistonSubsystem.getInstance().extendPiston(), ClimberPistonSubsystem.getInstance());
    }
}