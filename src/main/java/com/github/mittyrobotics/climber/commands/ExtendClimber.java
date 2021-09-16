package com.github.mittyrobotics.climber.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ExtendClimber extends SequentialCommandGroup {

    public ExtendClimber() {
        addCommands(
                new SetClimberPosition(true),
                new UnlockDrum()
        );
    }

}
