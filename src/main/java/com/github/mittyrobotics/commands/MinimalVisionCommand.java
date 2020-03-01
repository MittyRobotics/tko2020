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

import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.VisionTarget;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.subsystems.ShooterSubsystem;
import com.github.mittyrobotics.subsystems.TurretSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MinimalVisionCommand extends CommandBase {

    public MinimalVisionCommand() {
        super();
        addRequirements(TurretSubsystem.getInstance());
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if(!(OI.getInstance().getXboxController().getTriggerAxis(GenericHID.Hand.kRight) > 0.5)) {
            VisionTarget target = Vision.getInstance().getLatestVisionTarget();
            //VisionTarget target = new VisionTarget(new Transform(), new Rotation(), 15 * 12);
            System.out.println(target.getObserverYawToTarget().getHeading());
            double p = 0.10;
            TurretSubsystem.getInstance()
                    .overrideSetTurretPercent(p * target.getObserverYawToTarget().getHeading(), true);
            double rpm = rpmEquation(target.getObserverDistanceToTarget() / 12);
            ShooterSubsystem.getInstance().setShooterRpm(rpm);
        }
    }

    private double rpmEquation(double distance) {
        return 4800- 226 * (distance) + 15.1 * (distance * distance) - 0.291 * (distance * distance * distance);
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
