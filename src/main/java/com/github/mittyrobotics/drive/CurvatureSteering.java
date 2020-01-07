package com.github.mittyrobotics.drive;

import com.github.mittyrobotics.OI;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CurvatureSteering extends CommandBase {

	CurvatureSteering(){
		addRequirements(DriveTrainTalon.getInstance());
	}

	@Override
	public void initialize(){

	}

	@Override
	public void execute(){

		double turn = OI.getInstance().getXboxWheel().getX() * 450;
		double wheelWidth = 30;
		double radius = 360/turn;
		double leftSpeed = 0;
		double rightSpeed = 0;
		if (radius > 0) {
			leftSpeed = 2 * Math.PI * (radius + (1/2 * wheelWidth));
			rightSpeed = 2 * Math.PI * (radius - (1/2 * wheelWidth));

		}
		else if (radius < 0) {
			leftSpeed = 2 * Math.PI * (radius - (1/2 * wheelWidth));
			rightSpeed = 2 * Math.PI * (radius + (1/2 * wheelWidth));
		}
		turn = rightSpeed - leftSpeed;
		//negative for right turns, positive for left turns



		double speed = -OI.getInstance().getJoystick1().getY();
		boolean brake = OI.getInstance().getJoystick1().getTrigger();


		double newSpeed = speed;
		double newTurn = turn;
		if(Math.abs(speed) < 0.05){
			DriveTrainTalon.getInstance().tankDrive(newTurn, - newTurn);
		}
		DriveTrainTalon.getInstance().tankDrive(newSpeed + newTurn/2, newSpeed - newTurn/2);

	}
	@Override
	public void end(boolean interrupted){

	}
	@Override
	public boolean isFinished() {
		return false;
	}
}