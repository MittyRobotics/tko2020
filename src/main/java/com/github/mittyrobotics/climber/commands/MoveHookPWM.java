package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.Constants;
import com.github.mittyrobotics.climber.ElevateDirection;
import com.github.mittyrobotics.climber.Hooks;
import com.github.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.Calendar;


public class MoveHookPWM extends CommandBase {

    RobotSide side;
    ElevateDirection direction;
    double activePercent, inactivePercent, startTime;
    boolean toggle;

    public MoveHookPWM(RobotSide side, ElevateDirection direction) {
        super();
        this.side = side;
        this.direction = direction;
        addRequirements(Hooks.getInstance());
    }

    @Override
    public void initialize() {
        activePercent = Constants.PISTON_ACTIVE_PERCENT;
        inactivePercent = 1 - Constants.PISTON_ACTIVE_PERCENT;
        startTime = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public void execute() {
        if(Calendar.getInstance().getTimeInMillis() - startTime > activePercent * 100) {
            Hooks.getInstance().push(side, direction);
        }
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
