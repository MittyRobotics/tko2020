package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.PistonValue;
import com.github.mittyrobotics.climber.Hooks;
import com.github.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveHook extends CommandBase {

    private RobotSide side;
    private PistonValue value;


    public MoveHook(RobotSide side, PistonValue value) {
        super();
        this.side = side;
        this.value = value;
        addRequirements(Hooks.getInstance());
    }

    @Override
    public void initialize() {
        Hooks.getInstance().push(side, value);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
