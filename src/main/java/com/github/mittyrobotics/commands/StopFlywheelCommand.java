package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class StopFlywheelCommand extends CommandBase {
    public StopFlywheelCommand() {
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ShooterSubsystem.getInstance().setShooterPercent(0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
