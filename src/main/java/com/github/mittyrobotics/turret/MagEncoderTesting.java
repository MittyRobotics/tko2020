package com.github.mittyrobotics.turret;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MagEncoderTesting extends CommandBase {
	private double setpoint;
	private PIDController controller;
	public MagEncoderTesting(double setpoint){
		super();
		this.setpoint = setpoint;
		addRequirements(TurretSubsystem.getInstance());
		TurretSubsystem.getInstance().zeroEncoder();
	}
	@Override
	public void initialize(){
		controller = new PIDController(Constants.TurretP, Constants.TurretI, Constants.TurretD);
		controller.enableContinuousInput(0, 4000/Constants.TICKS_PER_ANGLE);
		controller.setSetpoint(setpoint);
	}
	@Override
	public void execute(){
		TurretSubsystem.getInstance().manualSetTurret(controller.calculate(TurretSubsystem.getInstance().getAngle()));
	}
	@Override
	public void end(boolean interrupted){

	}
	@Override
	public boolean isFinished(){
		return false;
	}
}
