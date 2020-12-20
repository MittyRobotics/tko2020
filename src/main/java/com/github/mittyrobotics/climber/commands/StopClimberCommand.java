package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ClimberSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Stops the {@link ClimberSubsystem} from moving
 */
public class StopClimberCommand extends InstantCommand {

    /**
     * Stops both motors of the {@link ClimberSubsystem}
     *
     * Command ends immediately
     *
     * Requires the {@link ClimberSubsystem}
     */
    public StopClimberCommand() {
        super(() -> ClimberSubsystem.getInstance().stopSparks(), ClimberSubsystem.getInstance());
    }
}
