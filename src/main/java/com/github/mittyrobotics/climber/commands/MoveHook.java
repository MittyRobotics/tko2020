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
        Hooks.getInstance().push(side, direction);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
