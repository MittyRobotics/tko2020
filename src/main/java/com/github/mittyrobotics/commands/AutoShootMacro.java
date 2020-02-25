package com.github.mittyrobotics.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class AutoShootMacro extends ParallelCommandGroup {
    public AutoShootMacro(){
        addCommands(new VisionShooterSpeedCommand(), new UnloadConveyorCommand());
    }
}
