package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class OuttakeRollersCommand extends CommandBase {
    public OuttakeRollersCommand() {
        addRequirements(IntakeSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        IntakeSubsystem.getInstance().outtakeBall();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
