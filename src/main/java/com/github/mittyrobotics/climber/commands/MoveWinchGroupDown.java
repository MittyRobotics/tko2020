package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class MoveWinchGroupDown extends ParallelCommandGroup {

    public MoveWinchGroupDown(){
        double upPosition = -5; //TODO should be in constants class
        addCommands(new MoveWinch(upPosition, RobotSide.LEFT), new MoveWinch(upPosition, RobotSide.RIGHT));
    }
}

