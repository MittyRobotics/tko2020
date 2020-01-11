package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class MoveWinchGroupUp extends ParallelCommandGroup {

    private final double upPosition = 5;

    public MoveWinchGroupUp(){
        addCommands(new MoveWinch(upPosition, RobotSide.LEFT), new MoveWinch(upPosition, RobotSide.RIGHT));
    }
}
