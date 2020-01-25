package com.github.mittyrobotics.conveyor;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ConveyorMacro extends ParallelCommandGroup {
    public ConveyorMacro(){
        addCommands(new BallCheck(), new MoveConveyor(Constants.CONVEYOR_DISTANCE_IN_INCHES));
    }
}
