package com.GitHub.mittyrobotics.climber.commands;

import com.GitHub.mittyrobotics.climber.PistonValue;
import com.GitHub.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class MoveHookGroupDown extends ParallelCommandGroup {
    public MoveHookGroupDown(){
        addCommands(new MoveHook(RobotSide.RIGHT, PistonValue.DOWN), new MoveHook(RobotSide.LEFT, PistonValue.DOWN));
    }
}
