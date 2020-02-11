package com.github.mittyrobotics.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class BallCheck extends CommandBase { //TODO don't make seperate command, run in periodic of ConveyorSubsystem
    private boolean previousEntranceSwitchValue;
    private static boolean ballCountHasChanged;

    public BallCheck(){
        super();
        addRequirements(ConveyorSwitches.getInstance());
    }

    @Override
    public void initialize() { previousEntranceSwitchValue = false; }

    @Override
    public void execute() {

        if (!previousEntranceSwitchValue && ConveyorSwitches.getInstance().getEntranceSwitch()) { //no ball before and now ball detected before conveyor
            ConveyorSubsystem.getInstance().updateBallCount(1);
            ballCountHasChanged = true;
        } else {
            ballCountHasChanged = false;
        }

        previousEntranceSwitchValue = ConveyorSwitches.getInstance().getEntranceSwitch();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
    public static boolean hasBallCountChanged() {return ballCountHasChanged;}

}
