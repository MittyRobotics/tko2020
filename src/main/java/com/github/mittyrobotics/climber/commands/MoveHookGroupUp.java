package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.PistonValue;
import com.github.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class MoveHookGroupUp extends ParallelCommandGroup {
    public MoveHookGroupUp() {
        addCommands(new MoveHook(RobotSide.RIGHT, PistonValue.UP), new MoveHook(RobotSide.LEFT, PistonValue.UP));
    }
}