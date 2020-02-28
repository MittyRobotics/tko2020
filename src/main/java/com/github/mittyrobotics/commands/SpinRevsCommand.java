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

import com.github.mittyrobotics.subsystems.SpinnerSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.constants.ColorWheelConstants.REVS;

public class SpinRevsCommand extends CommandBase {

    boolean done;
    private double initPos;

    public SpinRevsCommand() {
        super();
        addRequirements(SpinnerSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        initPos = SpinnerSubsystem.getInstance().getRevolutions();
        done = false;
    }

    @Override
    public void execute() {
        SpinnerSubsystem.getInstance().setMotorFast();
        if (Math.abs(SpinnerSubsystem.getInstance().getRevolutions()-initPos)> REVS) {
            done = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        //turns off motor, updates status
        SpinnerSubsystem.getInstance().setMotorOff();
    }

    @Override
    public boolean isFinished() {
        return done;
    }
}