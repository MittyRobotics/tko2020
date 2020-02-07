package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.Constants;
import com.github.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class MoveWinchGroupDown extends ParallelCommandGroup {

    public MoveWinchGroupDown(){
        double pos = -1*Constants.PISTON_DISTANCE;
        addCommands(new MoveWinch(pos, RobotSide.LEFT), new MoveWinch(pos, RobotSide.RIGHT));
    }
}

