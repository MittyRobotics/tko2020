package com.GitHub.mittyrobotics.climber.commands;

import com.GitHub.mittyrobotics.climber.Constants;
import com.GitHub.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class MoveWinchGroupDown extends ParallelCommandGroup {

    public MoveWinchGroupDown(){
        double pos = -1*Constants.PISTON_DISTANCE;
        addCommands(new MoveWinch(pos, RobotSide.LEFT), new MoveWinch(pos, RobotSide.RIGHT));
    }
}

