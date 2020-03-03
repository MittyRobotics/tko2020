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

import com.github.mittyrobotics.constants.WheelColor;
import com.github.mittyrobotics.subsystems.SpinnerSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinToColorCommand extends CommandBase {
    private int green = 0;
    private WheelColor color;

    public SpinToColorCommand() {
        super();
        addRequirements(SpinnerSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        color = SpinnerSubsystem.getInstance().getGameMessage();
        WheelColor cur = SpinnerSubsystem.getInstance().getColor();
        System.out.println(cur);

        if ((cur == WheelColor.Green && color == WheelColor.Blue) ||
                (cur == WheelColor.Blue && color == WheelColor.Yellow) ||
                (cur == WheelColor.Yellow && color == WheelColor.Red) ||
                (cur == WheelColor.Red && color == WheelColor.Green)) {
            SpinnerSubsystem.getInstance().setMotorSlow(true);
        } else {
            SpinnerSubsystem.getInstance().setMotorSlow(false);
        }
    }

    @Override
    public void execute() {
        if (SpinnerSubsystem.getInstance().getColor() == WheelColor.Green) {
            green += 1;
        } else {
            green = 0;
        }


    }

    @Override
    public void end(boolean interrupted) {
        //turn off motor
        SpinnerSubsystem.getInstance().stopMotor();

    }

    @Override
    public boolean isFinished() {
        if (color == WheelColor.Green) {
            return green > 3;
        } else {
            return SpinnerSubsystem.getInstance().getColor() == color || color == WheelColor.None;
        }
    }
}