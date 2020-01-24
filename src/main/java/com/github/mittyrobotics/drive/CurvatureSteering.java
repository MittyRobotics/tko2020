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

		double turn = (OI.getInstance().getXboxWheel().getX() * 450);
		double wheelWidth = 12.5;
		double radius = 0;

		if ((turn > -5) & (turn < 5))  {
			radius = 0;
		}
		else {
			radius = 360/turn;
		}

		double leftSpeed = 0;
		double rightSpeed = 0;

		if (radius > 0) {
			leftSpeed = 2 * Math.PI * (radius + wheelWidth); //leftSpeed bigger 33pi
			rightSpeed = 2 * Math.PI * (radius - wheelWidth); //-17pi
			leftSpeed = (leftSpeed/rightSpeed);
			rightSpeed = (rightSpeed/rightSpeed);


		}
		else if (radius < 0) {
			leftSpeed = 2 * Math.PI * (radius - (wheelWidth)); //rightSpeed bigger
			rightSpeed = 2 * Math.PI * (radius + (wheelWidth));
			rightSpeed = (rightSpeed/leftSpeed)/2;
			leftSpeed = (leftSpeed/leftSpeed)/2;
		}
		else {
			leftSpeed = 0;
			rightSpeed = 0;
		}

		//negative for right turns, positive for left turns

		double speed = -OI.getInstance().getJoystick1().getY();
		boolean brake = OI.getInstance().getJoystick1().getTrigger();

		if(brake){
			speed = 0;
			turn = 0;
		}


		if(Math.abs(speed) < 0.05){
			DriveTrainTalon.getInstance().tankDrive(OI.getInstance().getXboxWheel().getX()*2, -(OI.getInstance().getXboxWheel().getX())*2);
		}
		else {
			DriveTrainTalon.getInstance().tankDrive(((2*speed)/3) + (rightSpeed), ((2*speed)/3) + (leftSpeed));
		}
	}
	@Override
	public void end(boolean interrupted){

	}
	@Override
	public boolean isFinished() {
		return false;
	}
}


