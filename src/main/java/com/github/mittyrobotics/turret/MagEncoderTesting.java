package com.github.mittyrobotics.turret;

import com.github.mittyrobotics.OI;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MagEncoderTesting extends CommandBase {
	private double setpoint;
	private PIDController controller;
	public MagEncoderTesting(double setpoint){
		super();
		this.setpoint = setpoint;
		setpoint %= 4000/Constants.TICKS_PER_ANGLE;
		if(setpoint < 0){
			setpoint += 4000/Constants.TICKS_PER_ANGLE;
		}
		addRequirements(TurretSubsystem.getInstance());
		TurretSubsystem.getInstance().zeroEncoder();
	}
	@Override
	public void initialize(){
		controller = new PIDController(Constants.TurretP, Constants.TurretI, Constants.TurretD);
		controller.enableContinuousInput(0, 4000/Constants.TICKS_PER_ANGLE);
		controller.setSetpoint(setpoint);
		TurretSubsystem.getInstance().zeroEncoder();
	}
	@Override
	public void execute(){
		System.out.println(controller.calculate(TurretSubsystem.getInstance().getAngle()));
		TurretSubsystem.getInstance().manualSetTurret(controller.calculate(TurretSubsystem.getInstance().getAngle()));
//		TurretSubsystem.getInstance().manualSetTurret(OI.getInstance().getJoystick1().getY());
	}
	@Override
	public void end(boolean interrupted){

	}
	@Override
	public boolean isFinished(){
		return false;
	}
}
