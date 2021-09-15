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
        if(IntakeRaiseSubsystem.getInstance().getSwitch(0) || IntakeRaiseSubsystem.getInstance().getSwitch(1)) {
            IntakeRaiseSubsystem.getInstance().stop();
        } else {
            IntakeRaiseSubsystem.getInstance().setPositionPID(IntakeRaiseSubsystem.getInstance().getPosition());
        }
    }

    @Override
    public void end(boolean interrupted) {
        IntakeRaiseSubsystem.getInstance().stop();
    }
}
