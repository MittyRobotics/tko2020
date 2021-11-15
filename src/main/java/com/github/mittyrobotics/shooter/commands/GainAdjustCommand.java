package com.github.mittyrobotics.shooter.commands;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class GainAdjustCommand extends InstantCommand {

    public GainAdjustCommand(double amt) {
        super(()-> {AutonConstants.RANGE_SHOOTER_GAIN+=amt; SmartDashboard.putNumber("shootGain", AutonConstants.RANGE_SHOOTER_GAIN);}); //TODO add this
    }
}
