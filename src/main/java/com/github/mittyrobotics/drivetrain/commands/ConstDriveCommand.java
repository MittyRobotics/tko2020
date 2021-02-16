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

package com.github.mittyrobotics.drivetrain.commands;

import com.github.mittyrobotics.colorwheel.SpinnerSubsystem;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import edu.wpi.first.wpilibj2.command.RunCommand;

/**
 * Moves the drivetrain at a constant percent output
 */
public class ConstDriveCommand extends RunCommand {

    /**
     * Moves the drivetrain at a constant percent output
     *
     * Used to move the drivetrain when running the {@link SpinnerSubsystem}
     */
    public ConstDriveCommand(double leftSpeed, double rightSpeed) {
        super(() -> DrivetrainSubsystem.getInstance().setTankDrive(leftSpeed, rightSpeed, 0, 1),
                DrivetrainSubsystem.getInstance());
    }

}