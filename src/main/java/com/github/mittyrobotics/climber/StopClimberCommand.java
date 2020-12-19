package com.github.mittyrobotics.climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class StopClimberCommand extends InstantCommand {
    public StopClimberCommand() {
        super(() -> ClimberSubsystem.getInstance().stopSparks(), ClimberSubsystem.getInstance());
    }
}
