package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.IntakeRaiseSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class KeepIntakePosition extends CommandBase {
    public KeepIntakePosition() {
        addRequirements(IntakeRaiseSubsystem.getInstance());
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        IntakeRaiseSubsystem.getInstance().setPositionPID(IntakeRaiseSubsystem.getInstance().getPosition());
    }

    @Override
    public void end(boolean interrupted) {
        IntakeRaiseSubsystem.getInstance().stop();
    }

    @Override
    public boolean isFinished() {
        return IntakeRaiseSubsystem.getInstance().getLimitReached();
    }
}
