package com.github.mittyrobotics.climber;

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
        ClimberSubsystem.getInstance().setSetpoint(leftSetpoint,rightSetpoint);
    }

    @Override
    public void execute(){

    }

    @Override
    public void end(boolean interrupted){
        ClimberSubsystem.getInstance().stopSparks();
    }

    @Override
    public boolean isFinished(){
        //TODO make 5 a constant
        if ((Math.abs(leftSetpoint - ClimberSubsystem.getInstance().getLeftEncoderPosition()) < 5) && (Math.abs(rightSetpoint - ClimberSubsystem.getInstance().getRightEncoderPosition()) < 5)) {
            return true;
        } else {return false;}
    }
}
