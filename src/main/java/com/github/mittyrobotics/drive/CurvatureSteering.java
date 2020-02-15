/*
 * MIT License
 *
 * Copyright (c) 2019 Mitty Robotics (Team 1351)
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

package com.github.mittyrobotics.drive;

import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CurvatureSteering extends CommandBase {

    CurvatureSteering() {
        addRequirements(DriveTrainTalon.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        double turn = OI.getInstance().getXboxWheel().getX() * 450;
        double wheelWidth = 30;
        double radius = 0;

        if ((turn > -5) & (turn < 5)) {
            radius = 0;
        } else {
            radius = 360 / turn;
        }
        double leftSpeed = 0;
        double rightSpeed = 0;

        if (radius > 0) {
            leftSpeed = 2 * Math.PI * (radius + (0.5 * wheelWidth));
            rightSpeed = 2 * Math.PI * (radius - (0.5 * wheelWidth));

        } else if (radius < 0) {
            leftSpeed = 2 * Math.PI * (radius - (0.5 * wheelWidth));
            rightSpeed = 2 * Math.PI * (radius + (0.5 * wheelWidth));
        } else {
            leftSpeed = 0;
            rightSpeed = 0;
        }
        turn = rightSpeed - leftSpeed;
        //negative for right turns, positive for left turns


        double speed = -OI.getInstance().getJoystick1().getY();
        boolean brake = OI.getInstance().getJoystick1().getTrigger();

        if (brake) {
            speed = 0;
            turn = 0;
        }


        double newSpeed = speed;
        double newTurn = turn;
        if (Math.abs(speed) < 0.05) {
            DriveTrainTalon.getInstance().tankDrive(newTurn, -newTurn);
        }
        DriveTrainTalon.getInstance().tankDrive(newSpeed + newTurn / 2, newSpeed - newTurn / 2);
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}