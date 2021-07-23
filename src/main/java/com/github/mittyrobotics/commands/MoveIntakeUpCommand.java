package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class MoveIntakeUpCommand extends InstantCommand {
    public MoveIntakeUpCommand() {
        super(() -> IntakeSubsystem.getInstance().moveToPosition(0));
    }
}
