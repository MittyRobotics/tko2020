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

package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.DriveTrainSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CurvatureDriveCommand extends CommandBase {
    private boolean isReversed;

    CurvatureDriveCommand() {
        //addRequirements(DriveTrainTalon.getInstance());
        addRequirements(DriveTrainSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        isReversed = false;
    }

    @Override
    public void execute() {

        double turn = (OI.getInstance().getXboxWheel().getX() * 450); //steering wheel
        double joystickSpeed = -OI.getInstance().getJoystick1().getY(); //joystick
        boolean brake = OI.getInstance().getJoystick1().getTrigger(); //brake
        final double radiusE = 5;
        double radius = Math.abs(450 / turn * radiusE); //radius of imaginary circle
        double halfWidthRobot = 12.625; //12.5 for falcon

        double leftSpeed = 0;
        double rightSpeed = 0;
        double threshold = 10;
        boolean inThreshold = Math.abs(turn) < threshold;
        double turnScale = 1;
        //testing for 10 degrees to the left
        if (turn >= threshold) {
            leftSpeed = (radius + halfWidthRobot) * (2 * Math.PI);
            rightSpeed = (radius - halfWidthRobot) * (2 * Math.PI);
            rightSpeed = (rightSpeed / leftSpeed) * turnScale;
            leftSpeed = 1 * turnScale;
        } else if (turn <= -threshold) {
            leftSpeed = (radius - halfWidthRobot) * (2 * Math.PI);
            rightSpeed = (radius + halfWidthRobot) * (2 * Math.PI);
            leftSpeed = (leftSpeed / rightSpeed) * turnScale;
            rightSpeed = 1 * turnScale;
        } else if (inThreshold) {
            turn = OI.getInstance().getXboxWheel().getX() * 450 / 120;
        }

        if (brake) {
            turn = 0;
            leftSpeed = 0;
            rightSpeed = 0;
            joystickSpeed = 0;
        }

        if (OI.getInstance().getJoystick1().getRawButtonPressed(2)) {
            isReversed = true;
        } else if (OI.getInstance().getJoystick1().getRawButtonPressed(1)) { //change button based on kito's preference
            isReversed = false;
        }

        if (isReversed) {
            joystickSpeed = -joystickSpeed;
            double temp = leftSpeed;
            leftSpeed = rightSpeed;
            rightSpeed = temp;
        }

        if (inThreshold) {
            //DriveTrainTalon.getInstance().tankDrive(joystickSpeed, joystickSpeed);
            DriveTrainSubsystem.getInstance().tankDrive(
                    joystickSpeed, joystickSpeed);
        } else if (Math.abs(joystickSpeed) < 0.1) {
            DriveTrainSubsystem.getInstance().tankDrive(turn / 350, -turn / 350);
            //DriveTrainTalon.getInstance().tankDrive(turn/350, -turn/350);
        } else {
            DriveTrainSubsystem.getInstance().tankDrive(leftSpeed * joystickSpeed, rightSpeed * joystickSpeed);
            //DriveTrainTalon.getInstance().tankDrive(leftSpeed * joystickSpeed, rightSpeed * joystickSpeed);
        }

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}