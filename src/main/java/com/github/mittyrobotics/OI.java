package com.github.mittyrobotics;

import com.github.mittyrobotics.buffer.LockBall;
import com.github.mittyrobotics.buffer.ReleaseBall;
import com.github.mittyrobotics.controls.controllers.XboxWheel;
import com.github.mittyrobotics.conveyor.MoveConveyorAddBall;
import com.github.mittyrobotics.conveyor.ResetBalls;
import com.github.mittyrobotics.intake.IntakeBall;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class OI {
	private Button button1;
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
//		Trigger b1 = new Trigger() {
//			@Override
//			public boolean get() {
//				return getJoystick1().getTrigger();
//			}
//		};
//		b1.whileActiveOnce(new MoveConveyorAddBall(8));
//		Button b2 = new Button() {
//			@Override
//			public boolean get() {
//				return getJoystick1().getRawButton(2);
//			}
//		};
//		b2.whenPressed(new MoveConveyorAddBall(7));
//		Button b3 = new Button() {
//			@Override
//			public boolean get() {
//				return getJoystick1().getRawButton(3);
//			}
//		};
//		b3.whenPressed(new MoveConveyorAddBall(7.5));

	}
}