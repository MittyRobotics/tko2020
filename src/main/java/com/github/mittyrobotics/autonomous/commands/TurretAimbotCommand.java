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

package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.autonomous.util.VisionTarget;
import com.github.mittyrobotics.autonomous.vision.AutomatedTurretSuperstructure;
import com.github.mittyrobotics.autonomous.vision.Vision;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurretAimbotCommand extends CommandBase {

    private double error;

    public TurretAimbotCommand() {
        super();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        VisionTarget target = Vision.getInstance().getCurrentVisionTarget();
        AutomatedTurretSuperstructure.getInstance().visionAim(target);
        this.error =
                AutomatedTurretSuperstructure.getInstance().getFieldRelativeRotation()
                        .subtract(target.getFieldRelativeYaw())
                        .getHeading();
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public double getError() {
        return error;
    }
}
