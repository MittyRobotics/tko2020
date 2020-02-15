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

package com.github.mittyrobotics;

import com.github.mittyrobotics.colorwheel.ColorTesting;
import com.github.mittyrobotics.colorwheel.SpinRevs;
import com.github.mittyrobotics.colorwheel.SpinToColor;
import com.github.mittyrobotics.colorwheel.WheelColor;
import com.github.mittyrobotics.controls.controllers.XboxWheel;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

public class OI {
	private XboxWheel xboxWheel;
	private XboxController xboxController;
	private Joystick joystick1;
	private Joystick joystick2;
	private static OI instance;
	private static boolean stage3 = false;

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
		Button spinRevButton = new Button() {
			@Override
			public boolean get() {
				return getJoystick1().getRawButton(3)
						//&& !stage3
				;
			}
		};

		Button blue = new Button() {
			@Override
			public boolean get() {
				return getJoystick1().getRawButton(6);
			}
		};
		Button red = new Button() {
			@Override
			public boolean get() {
				return getJoystick1().getRawButton(7);
			}
		};
		Button yellow = new Button() {
			@Override
			public boolean get() {
				return getJoystick1().getRawButton(11);
			}
		};
		Button green = new Button() {
			@Override
			public boolean get() {
				return getJoystick1().getRawButton(10);
			}
		};

		/*Button spinColorButton = new Button() {
			@Override
			public boolean get() {
				return getJoystick1().getRawButtonPressed(3) && stage3;
			}
		};*/


		//UNCOMMENT FOR COLOR CALIBRATION
		/*Button colorTestingButton = new Button() {
			@Override
			public boolean get() {
				return getJoystick1().getRawButtonPressed(3);
			}
		};

		colorTestingButton.whenPressed(new ColorTesting());
		*/
		blue.whenPressed(new SpinToColor(WheelColor.Blue));
		red.whenPressed(new SpinToColor(WheelColor.Red));
		yellow.whenPressed(new SpinToColor(WheelColor.Yellow));
		green.whenPressed(new SpinToColor(WheelColor.Green));

		spinRevButton.whenPressed(new SpinRevs());
	}
	public void passedStage2(){
		stage3 = true;
	}

}