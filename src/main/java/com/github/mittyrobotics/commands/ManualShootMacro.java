package com.github.mittyrobotics.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ManualShootMacro extends ParallelCommandGroup {
    public ManualShootMacro(){
        addCommands(new ManualSpinFlywheelCommand(), new UnloadConveyorCommand());
    }
}
