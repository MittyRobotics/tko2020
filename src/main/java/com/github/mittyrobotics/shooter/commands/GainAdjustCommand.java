package com.github.mittyrobotics.shooter.commands;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class GainAdjustCommand extends InstantCommand {

    public GainAdjustCommand(double amt) {
        super(()-> AutonConstants.RANGE_SHOOTER_GAIN+=amt); //TODO add this
    }
}
