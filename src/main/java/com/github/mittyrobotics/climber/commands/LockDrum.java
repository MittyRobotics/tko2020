package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ClimberSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class LockDrum extends InstantCommand {

    public LockDrum() {
        super(()-> ClimberSubsystem.getInstance().lockDrum(), ClimberSubsystem.getInstance());
    }
}
