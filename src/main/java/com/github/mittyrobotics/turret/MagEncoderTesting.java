package com.github.mittyrobotics.turret;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MagEncoderTesting extends CommandBase {
	private double setpoint;
	private PIDController controller;
	private double angleValue;
	private boolean isDone = false;
	public MagEncoderTesting(double setpoint){
		super();
		addRequirements(TurretSubsystem.getInstance());
		this.setpoint = setpoint;
//		TurretSubsystem.getInstance().zeroEncoder();

	}
	@Override
	public void initialize(){
		setpoint += TurretSubsystem.getInstance().getAngle();
		this.setpoint %= 4000/Constants.TICKS_PER_ANGLE;
		if(setpoint < 0){
			this.setpoint += 4000/Constants.TICKS_PER_ANGLE;
		}
		controller = new PIDController(Constants.TurretP, Constants.TurretI, Constants.TurretD);
		controller.enableContinuousInput(0, 4000/Constants.TICKS_PER_ANGLE);
//		TurretSubsystem.getInstance().zeroEncoder();
//		angleValue = TurretSubsystem.getInstance().getAngle();
//		isDone = false;
	}
	@Override
	public void execute(){
		System.out.println("This setpoint" + this.setpoint);
		System.out.println("Get setpoint: " + controller.getSetpoint());

//		if ((TurretSubsystem.getInstance().getAngle()<(setpoint+0.5)) && ((setpoint-0.5)< TurretSubsystem.getInstance().getAngle())) {
//			isDone = true;
//		} else {
			TurretSubsystem.getInstance().manualSetTurret(controller.calculate(TurretSubsystem.getInstance().getAngle(), setpoint));
//		}

//		TurretSubsystem.getInstance().changeAngle(40);


//		TurretSubsystem.getInstance().manualSetTurret(OI.getInstance().getJoystick1().getY());
//		System.out.println("Angle value" + (TurretSubsystem.getInstance().getAngle()-angleValue));
	}
	@Override
	public void end(boolean interrupted){
		controller.close();
		System.out.println("DONE WITH MAG");

	}
	@Override
	public boolean isFinished(){
		return DriverStation.getInstance().isDisabled();
	}
}
