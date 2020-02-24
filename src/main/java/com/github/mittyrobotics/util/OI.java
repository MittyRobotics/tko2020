/*
 * MIT License
 *
 * Copyright (c) 2020 Mitty Robotics (Team 1351)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mittyrobotics.util;

import com.github.mittyrobotics.colorwheel.*;
import com.github.mittyrobotics.controls.controllers.XboxWheel;
import com.github.mittyrobotics.drive.DriveTrainFalcon;
import com.github.mittyrobotics.drive.JoystickDrive_CarSteering;
import com.github.mittyrobotics.intake.*;
import com.github.mittyrobotics.shooter.AutoSpinFlywheel;
import com.github.mittyrobotics.shooter.ManualSpinFlywheel;
import com.github.mittyrobotics.shooter.StopFlywheel;
import edu.wpi.first.wpilibj.GenericHID;
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
	public void setupControls(){
		DriveTrainFalcon.getInstance().setDefaultCommand(new JoystickDrive_CarSteering());

		Spinner.getInstance().setDefaultCommand(new ManualSpinWheel());

		Button spinWheel = new Button(()->getJoystick1().getTrigger());
		spinWheel.whenPressed(new SpinWheel());

		Button autoShoot = new Button(()->getXboxController().getTriggerAxis(GenericHID.Hand.kRight) > 0.5);
		autoShoot.whenPressed(new AutoSpinFlywheel());
		autoShoot.whenReleased(new StopFlywheel());

		Button manualShoot = new Button(()->getXboxController().getBumper(GenericHID.Hand.kRight));
		manualShoot.whenPressed(new ManualSpinFlywheel());
		manualShoot.whenReleased(new StopFlywheel());

		Button changeIntakePiston = new Button(()->getXboxController().getBButton());
		changeIntakePiston.whenPressed(new ChangeIntakePistonState());

		Button intake = new Button(()->getXboxController().getTriggerAxis(GenericHID.Hand.kLeft) > 0.5);
		intake.whenPressed(new IntakeBall());
		intake.whenReleased(new StopBall());

		Button outtake = new Button(()->getXboxController().getBumper(GenericHID.Hand.kLeft));
		outtake.whenPressed(new OuttakeRollers());
		outtake.whenReleased(new StopBall());
	}

}