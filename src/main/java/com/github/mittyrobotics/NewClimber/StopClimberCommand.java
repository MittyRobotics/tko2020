package com.github.mittyrobotics.NewClimber;

import com.github.mittyrobotics.NewClimber.ClimberSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class StopClimberCommand extends InstantCommand {
    public StopClimberCommand() {
        super(() -> ClimberSubsystem.getInstance().stopSparks(), ClimberSubsystem.getInstance());
    }
}
