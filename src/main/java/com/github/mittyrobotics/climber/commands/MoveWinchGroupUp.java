package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.Constants;
import com.github.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class MoveWinchGroupUp extends ParallelCommandGroup {

    public MoveWinchGroupUp() {
        //TODO make sure intake is extended
        double pos = Constants.PISTON_DISTANCE;
        addCommands(new MoveWinch(pos, RobotSide.LEFT), new MoveWinch(pos, RobotSide.RIGHT));
    }
}
