package com.github.mittyrobotics.conveyor.commands.intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

// IMPORTANT: Change `extended` value usage in IntakePistonSubsystem in order to use this command

public class WaitExtendIntake extends SequentialCommandGroup {
    public WaitExtendIntake(boolean extended) {
        if(extended) {
            addCommands(new RetractIntake(),
                    new ChangeIntakeExtended(false)
            );
        } else {
            addCommands(new ExtendIntake(),
                    new WaitCommand(1),
                    new ChangeIntakeExtended(true)
            );
        }
    }
}
