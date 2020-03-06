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

import com.github.mittyrobotics.LinearActuator.LinearActuator;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends TimedRobot {

    //Joystick js = new Joystick(0);
    XboxController js = new XboxController(0);
    boolean toggle = false;

    @Override
    public void robotInit() {
        LinearActuator.getInstance().initHardware();
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void testInit() {

    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopPeriodic() {

        if (js.getAButtonPressed()) {
            LinearActuator.getInstance().set(0.5);
        } else if(js.getYButtonPressed()) {
            LinearActuator.getInstance().set(0);
        } else if(js.getBButtonPressed()) {
            toggle = !toggle;
        }

        if(toggle) {
            LinearActuator.getInstance().set(1-((js.getY(GenericHID.Hand.kRight) + 1) / 2));
        }
    }

    @Override
    public void testPeriodic() {

    }
}