package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ClimberConstants;
import com.github.mittyrobotics.climber.ClimberSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class SetClimberPosition extends CommandBase {

    private boolean up;

    public SetClimberPosition(boolean up) {
        this.up = up;
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        if(up) {
            ClimberSubsystem.getInstance().lowerClimber();
        } else {
            ClimberSubsystem.getInstance().raiseClimber();
        }
    }

    @Override
    public void execute() {
        ClimberSubsystem.getInstance().runPositionPID();
    }

    @Override
    public void end(boolean interrupted) {
        ClimberSubsystem.getInstance().brake();
    }

    @Override
    public boolean isFinished() {
        return ClimberSubsystem.getInstance().getError() < ClimberConstants.CLIMBER_THRESHOLD;
    }
}