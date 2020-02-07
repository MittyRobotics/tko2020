package com.GitHub.mittyrobotics.climber.commands;

import com.GitHub.mittyrobotics.climber.Constants;
import com.GitHub.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class MoveWinchGroupUp extends ParallelCommandGroup {

    public MoveWinchGroupUp(){
        double pos = Constants.PISTON_DISTANCE;
        addCommands(new MoveWinch(pos, RobotSide.LEFT), new MoveWinch(pos, RobotSide.RIGHT));
    }
}
