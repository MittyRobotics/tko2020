package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ClimberConstants;
import com.github.mittyrobotics.climber.ClimberSubsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class SetClimberPosition extends CommandBase {

    private double setpoint;
    private boolean up;

    private PIDController controller;

    public SetClimberPosition(boolean up) {
        this.up = up;
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        if(up) {
            this.setpoint = ClimberConstants.CLIMBER_EXTENDED;
            ClimberSubsystem.getInstance().setClimberRaised(true);
        } else {
            this.setpoint = ClimberConstants.CLIMBER_DEEXTENDED;
            ClimberSubsystem.getInstance().setClimberRaised(false);
        }

        controller = new PIDController(ClimberConstants.POSITION_P, ClimberConstants.POSITION_I, ClimberConstants.POSITION_D);
        controller.setSetpoint(setpoint);
    }

    @Override
    public void execute(){
        double val = controller.calculate(ClimberSubsystem.getInstance().getPosition());
        ClimberSubsystem.getInstance().overrideSetMotor(val);
    }

    @Override
    public void end(boolean interrupted){
        ClimberSubsystem.getInstance().brake();
    }

    @Override
    public boolean isFinished(){
        return (Math.abs(controller.getPositionError()) < ClimberConstants.CLIMBER_THRESHOLD);
    }
}