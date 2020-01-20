package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ElevateDirection;
import com.github.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class MoveHookGroupDown extends ParallelCommandGroup {
    public MoveHookGroupDown(){
        addCommands(new MoveHook(RobotSide.RIGHT, ElevateDirection.DOWN), new MoveHook(RobotSide.LEFT, ElevateDirection.DOWN));
    }
}
