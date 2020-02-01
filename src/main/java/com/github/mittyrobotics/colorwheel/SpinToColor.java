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

package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinToColor extends CommandBase {
    private double prevPosition;
    private boolean onColor = false;
    private boolean finished = false;

    public SpinToColor() {
        super();
        addRequirements(Spinner.getInstance(), ColorPiston.getInstance());
    }

    @Override
    public void initialize() {
        ColorPiston.getInstance().up();
    }

    @Override
    public void execute() {
        if (Spinner.getInstance().matching()) {
            prevPosition = Spinner.getInstance().getRevolutions();
            onColor = true;
        }
        if (onColor) {
            if (Spinner.getInstance().getRevolutions() - prevPosition > 1.0 / 16.0) {
                finished = true;
            }
        }


    }

    @Override
    public void end(boolean interrupted) {
        //turn off motor
        ColorPiston.getInstance().down();
        Spinner.getInstance().setMotorOff();
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
