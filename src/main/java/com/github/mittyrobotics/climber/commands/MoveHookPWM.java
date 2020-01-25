package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.Constants;
import com.github.mittyrobotics.climber.ElevateDirection;
import com.github.mittyrobotics.climber.Hooks;
import com.github.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.Calendar;


public class MoveHookPWM extends CommandBase {

    RobotSide side; //TODO make all of these private
    ElevateDirection direction;
    double activePercent, inactivePercent, startTime;
    boolean toggle; //TODO what is this for?

    public MoveHookPWM(RobotSide side, ElevateDirection direction) {
        super();
        this.side = side;
        this.direction = direction;
        addRequirements(Hooks.getInstance());
    }

    @Override
    public void initialize() { //TODO make this a paramter
        activePercent = Constants.PISTON_ACTIVE_PERCENT;
        inactivePercent = 1 - Constants.PISTON_ACTIVE_PERCENT;
        startTime = Calendar.getInstance().getTimeInMillis(); //TODO DO NOT USE CALENDAR. Use Timer.getFPGATimestamp() (it is frc's timer)
    }

    @Override
    public void execute() { //TODO You never set it to go off when it is not active
        if(Calendar.getInstance().getTimeInMillis() - startTime > activePercent * 100) { //TODO not sure how this logic performs PWM
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
