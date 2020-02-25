package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.ColorPistonSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinnerUpCommand extends CommandBase {
    public SpinnerUpCommand() {
        addRequirements(ColorPistonSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ColorPistonSubsystem.getInstance().up();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}