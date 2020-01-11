package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class MoveWinchGroupDown extends ParallelCommandGroup {

    private final double upPosition = -5;
    public MoveWinchGroupDown(){
        addCommands(new MoveWinch(upPosition, RobotSide.LEFT), new MoveWinch(upPosition, RobotSide.RIGHT));
    }
}

