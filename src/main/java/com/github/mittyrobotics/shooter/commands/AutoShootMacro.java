package com.github.mittyrobotics.shooter.commands;

import com.github.mittyrobotics.conveyor.commands.IntakeBallShootingCommand;
import com.github.mittyrobotics.conveyor.commands.UnloadConveyorCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoShootMacro extends SequentialCommandGroup {
    public AutoShootMacro() {
        addCommands(
                //new ParallelRaceGroup(new WaitUntilShooterSpeedCommand(50), new WaitCommand(1)),
                parallel(
                        new UnloadConveyorCommand(), new IntakeBallShootingCommand()
                )
        );
    }

}