package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ClimberConstants;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ExtendClimber extends SequentialCommandGroup {

    public ExtendClimber() {
        addCommands(
                new UnlockDrum(),
                new SetClimberPosition(ClimberConstants.CLIMBER_EXTENDED),
                new LockDrum()
        );
    }

}
