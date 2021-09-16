package com.github.mittyrobotics.climber.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DeextendClimber extends SequentialCommandGroup {

    public DeextendClimber() {
        addCommands(
                new LockDrum(),
                new SetClimberPosition(false)
        );
    }

}
