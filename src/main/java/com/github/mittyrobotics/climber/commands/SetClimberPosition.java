package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ClimberConstants;
import com.github.mittyrobotics.climber.ClimberSubsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class SetClimberPosition extends CommandBase {

    private final double setpoint;

    private PIDController controller;

    public SetClimberPosition(double setpoint) {
        this.setpoint = setpoint;
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        controller = new PIDController(ClimberConstants.POSITION_P, ClimberConstants.POSITION_I, ClimberConstants.POSITION_D);
        controller.setSetpoint(setpoint);
    }

    @Override
    public void execute(){
        double val = controller.calculate(ClimberSubsystem.getInstance().getPosition());
        ClimberSubsystem.getInstance().setMotor(val);
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