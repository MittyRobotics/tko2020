package com.github.mittyrobotics;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class OI {
	private XboxController xboxController;
	private Joystick joystick1;
	private Joystick joystick2;
	private static OI instance;
	public static OI getInstance(){
		if(instance == null){
			instance = new OI();
		}
		return instance;
	}
	public XboxController getXboxController(){
		if(xboxController == null){
			xboxController = new XboxController(Constants.XBOX_ID);
		}
		return xboxController;
	}
	public Joystick getJoystick1(){
		if(joystick1 == null){
			joystick1 = new Joystick(Constants.JOYSTICK_1_ID);
		}
		return joystick1;
	}
	public Joystick getJoystick2(){
		if(joystick2 == null){
			joystick2 = new Joystick(Constants.JOYSTICK_2_ID);
		}
		return joystick2;
	}
	public void digitalInputControls(){

	}
}