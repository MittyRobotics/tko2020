package com.github.mittyrobotics;

import com.github.mittyrobotics.buffer.LockBall;
import com.github.mittyrobotics.buffer.ReleaseBall;
import com.github.mittyrobotics.controls.controllers.XboxWheel;
import com.github.mittyrobotics.conveyor.ConveyorMacro;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.MoveConveyor;
import com.github.mittyrobotics.conveyor.ResetBalls;
import com.github.mittyrobotics.intake.IntakeBall;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

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
		Button intakeButton = new Button() {
			@Override
			public boolean get() {
				return getJoystick1().getTrigger();
			}
		};
		intakeButton.whenPressed(new IntakeBall());

		Button conveyorButton = new Button() {
			@Override
			public boolean get() {
				return getJoystick1().getRawButton(3);
			}
		};
		conveyorButton.whenPressed(new MoveConveyor(Constants.distance));

		Button lockBall = new Button() {
			@Override
			public boolean get() {
				return getJoystick1().getRawButton(4);
			}
		};
		lockBall.whenPressed(new LockBall());

		Button releaseBall = new Button() {
			@Override
			public boolean get() {
				return getJoystick1().getRawButton(5);
			}
		};
		releaseBall.whenPressed(new ReleaseBall());

		Button resetCounter = new Button() {
			@Override
			public boolean get() {
				return getJoystick1().getRawButton(6);
			}
		};
		resetCounter.whenPressed(new ResetBalls());


	}
}