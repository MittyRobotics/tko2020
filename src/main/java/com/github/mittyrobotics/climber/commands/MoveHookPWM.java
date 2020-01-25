package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.Constants;
import com.github.mittyrobotics.climber.ElevateDirection;
import com.github.mittyrobotics.climber.Hooks;
import com.github.mittyrobotics.climber.RobotSide;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.Calendar;


public class MoveHookPWM extends CommandBase {

    private RobotSide side;
    private ElevateDirection direction;

    private double activePercent, inactivePercent, cycleTime;
    private boolean pistonActive = false;

    public MoveHookPWM(RobotSide side, ElevateDirection direction, double activePercent) {
        super();
        this.side = side;
        this.direction = direction;
        this.activePercent = activePercent;
        addRequirements(Hooks.getInstance());
    }

    @Override
    public void initialize() {
        inactivePercent = 1 - activePercent;
        cycleTime = Timer.getFPGATimestamp();
    }


    @Override
    public void execute() {

        if(Timer.getFPGATimestamp() - cycleTime > inactivePercent / 10.0) {
            pistonActive = true;
            //TODO isn't active percent /10 + inactivePercent/10 always equal 0.1?
            //TODO why do you divide by ten
            //TODO logic seems overall ok (it's a bit confusing, but makes sense), but I think a solution using % would be more efficient and simpler
            if(Timer.getFPGATimestamp() - cycleTime > activePercent / 10.0 + inactivePercent / 10.0) {
                pistonActive = false;
                cycleTime = Timer.getFPGATimestamp();
            }
        }

        if(pistonActive) {
            System.out.println("Active");
            Hooks.getInstance().push(side, direction); //TODO maybe change the code to take in Value enum instead of direction enum to stop off (idk but just an idea)
        } else  {
            System.out.println("Inactive");
            Hooks.getInstance().off(side);
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
