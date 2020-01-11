package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ElevateDirection;
import com.github.mittyrobotics.climber.Hooks;
import com.github.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveHook extends CommandBase {

    private RobotSide side;
    private ElevateDirection direction;

    public MoveHook(RobotSide side, ElevateDirection direction) {
        super();

        this.side = side;
        this.direction = direction;

        addRequirements(Hooks.getInstance());
    }

    @Override
    public void initialize() {
        // if the specified side for the object is LEFT
        // push the piston up / down as specified in constructor
        if(side == RobotSide.LEFT) {
            if(direction == ElevateDirection.UP) {
                Hooks.getInstance().pushLeftUp();
            }
            else if (direction == ElevateDirection.DOWN) {
                Hooks.getInstance().pushLeftDown();
            }

        }
        // same as above but for right side of robot
        else if (side == RobotSide.RIGHT) {
            if(direction == ElevateDirection.UP) {
                Hooks.getInstance().pushRightUp();
            }
            else if (direction == ElevateDirection.DOWN) {
                Hooks.getInstance().pushRightDown();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
