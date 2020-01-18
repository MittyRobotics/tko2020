package com.github.mittyrobotics;

import com.github.mittyrobotics.controls.controllers.XboxWheel;
import com.github.mittyrobotics.drive.Drive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

public class OI {
	private XboxWheel xboxWheel;
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
	public XboxWheel getXboxWheel(){
		if(xboxWheel == null){
			xboxWheel = new XboxWheel(Constants.XBOX_WHEEL_ID);
		}
		return xboxWheel;
	}
	public XboxController getXboxController(){
		if(xboxController == null){
			xboxController = new XboxController(Constants.XBOX_CONTROLLER_ID);
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
//		Button initializeButton = new Button() {
//			@Override
//			public boolean get() {
//				return getJoystick1().getTrigger();
//			}
//		};
//
//		initializeButton.whenPressed(new Drive());
	}
}