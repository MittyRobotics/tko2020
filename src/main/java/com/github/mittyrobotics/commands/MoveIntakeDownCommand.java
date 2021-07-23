package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class MoveIntakeDownCommand extends InstantCommand {
    public MoveIntakeDownCommand() {
        super(() -> IntakeSubsystem.getInstance().moveToPosition(0));
    }
}
