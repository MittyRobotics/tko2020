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

package com.github.mittyrobotics.colorwheel.commands;

import com.github.mittyrobotics.colorwheel.WheelColor;
import com.github.mittyrobotics.colorwheel.SpinnerSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Spins the control panel to the designated color
 */
public class SpinToColorCommand extends CommandBase {
    /**
     * Counter to keep track of # of greens
     *
     * Green is tracked differently since the sensor may read green between yellow and blue
     */
    private int green = 0;

    /**
     * The target color
     */
    private WheelColor color;

    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link SpinnerSubsystem}
     */
    public SpinToColorCommand() {
        super();
        addRequirements(SpinnerSubsystem.getInstance());
    }

    /**
     * Initializes the target color based on {@link edu.wpi.first.wpilibj.DriverStation} input and sets the motor to spin slowly
     */
    @Override
    public void initialize() {
        color = SpinnerSubsystem.getInstance().getGameMessage();
        SpinnerSubsystem.getInstance().setMotorSlow(color);
    }

    /**
     * Checks if the color green was found, increments if found multiple times in a row
     *
     * For green the color must be found multiple times in a row since the color sensor may accidentally detect green
     */
    @Override
    public void execute() {
        if (SpinnerSubsystem.getInstance().getColor() == WheelColor.Green) {
            green++;
        } else {
            green = 0;
        }
    }

    /**
     * Stops the motor
     */
    @Override
    public void end(boolean interrupted) {
        SpinnerSubsystem.getInstance().stopMotor();
    }

    /**
     * Returns if the command should finish
     *
     * @return if the target color is found
     */
    @Override
    public boolean isFinished() {
        if (color == WheelColor.Green) {
            return green > 3;
        } else {
            return SpinnerSubsystem.getInstance().getColor() == color || color == WheelColor.None;
        }
    }
}