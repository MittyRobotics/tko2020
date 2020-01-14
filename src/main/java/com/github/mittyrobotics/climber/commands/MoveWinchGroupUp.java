package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class MoveWinchGroupUp extends ParallelCommandGroup {

    public MoveWinchGroupUp(){
        double upPosition = 5; //TODO should be in Constants class
        addCommands(new MoveWinch(upPosition, RobotSide.LEFT), new MoveWinch(upPosition, RobotSide.RIGHT));
    }
}
