package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ClimberConstants;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DeextendClimber extends SequentialCommandGroup {

    public DeextendClimber() {
        addCommands(
                new UnlockDrum(),
                new SetClimberPosition(ClimberConstants.CLIMBER_DEEXTENDED),
                new LockDrum()
        );
    }

}
