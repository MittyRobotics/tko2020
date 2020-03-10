package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.ClimberPistonSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ClimberArmsUpCommand extends InstantCommand {
    public ClimberArmsUpCommand(){
        super(()-> ClimberPistonSubsystem.getInstance().extendPiston(), ClimberPistonSubsystem.getInstance());
    }
}
