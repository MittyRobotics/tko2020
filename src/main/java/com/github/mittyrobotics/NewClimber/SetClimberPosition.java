package com.github.mittyrobotics.NewClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetClimberPosition extends CommandBase {
    private double leftSetpoint, rightSetpoint;
    public SetClimberPosition(double leftSetpoint, double rightSetpoint){
        this.leftSetpoint = leftSetpoint;
        this.rightSetpoint = rightSetpoint;
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize(){
        ClimberSubsystem.getInstance().setSetpoint(0,0);
    }

    @Override
    public void execute(){
        ClimberSubsystem.getInstance().setSetpoint(leftSetpoint,rightSetpoint);
    }

    @Override
    public void end(boolean interrupted){
        ClimberSubsystem.getInstance().stopSparks();
    }
}
