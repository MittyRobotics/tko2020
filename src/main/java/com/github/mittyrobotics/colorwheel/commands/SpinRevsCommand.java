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

import com.github.mittyrobotics.colorwheel.ColorWheelConstants;
import com.github.mittyrobotics.colorwheel.SpinnerSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;


/**
 * Spins the {@link SpinnerSubsystem} certain amount of revolutions
 */
public class SpinRevsCommand extends CommandBase {

    /**
     * The initial position of the spinner when the command started
     */
    private double initPos;

    /**
     * The amount of revolutions to spin
     */
    private final double revs;

    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link SpinnerSubsystem}
     *
     * Sets the amount of revolutions to spin to the default assigned in {@link ColorWheelConstants}
     */
    public SpinRevsCommand() {
        this(ColorWheelConstants.DEFUALT_REVS);
    }

    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link SpinnerSubsystem}
     *
     * @param revs the amount of revolutions to spin
     */
    public SpinRevsCommand(double revs) {
        super();
        this.revs = revs;
        addRequirements(SpinnerSubsystem.getInstance());
    }

    /**
     * Assigns the initial wheel position and sets the motor to move fast
     */
    @Override
    public void initialize() {
        initPos = SpinnerSubsystem.getInstance().getPosition();
        SpinnerSubsystem.getInstance().setMotorFast();
    }

    /**
     * Stops the motor
     *
     * @param interrupted whether the command was interrupted or ended because {@link #isFinished()} returned true
     */
    @Override
    public void end(boolean interrupted) {
        SpinnerSubsystem.getInstance().stopMotor();
    }

    /**
     * Returns if the command should end
     *
     * @return if the wheel has made more than the inputted revolutions
     */
    @Override
    public boolean isFinished() {
        return Math.abs(SpinnerSubsystem.getInstance().getPosition() - initPos) >= revs;
    }
}