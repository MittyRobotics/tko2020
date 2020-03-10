package com.github.mittyrobotics.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimberArmsDownMacro extends SequentialCommandGroup {
    public ClimberArmsDownMacro(){
        addCommands(new WindWinchCommand(), new LockWinchCommand());
    }
}
