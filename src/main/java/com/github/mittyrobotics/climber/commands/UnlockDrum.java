package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ClimberSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class UnlockDrum extends InstantCommand {

    public UnlockDrum() {
        super(()-> ClimberSubsystem.getInstance().unlockDrum(), ClimberSubsystem.getInstance());
    }
}
