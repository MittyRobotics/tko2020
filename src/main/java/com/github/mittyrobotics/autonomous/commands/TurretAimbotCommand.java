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

import com.github.mittyrobotics.autonomous.datatypes.VisionTarget;
import com.github.mittyrobotics.autonomous.AutomatedTurretSuperstructure;
import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.shooter.Shooter;
import com.github.mittyrobotics.turret.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurretAimbotCommand extends CommandBase {

    private double error;

    public TurretAimbotCommand() {
        super();
        addRequirements(Turret.getInstance());
        addRequirements(Shooter.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (Vision.getInstance().isSafeToUseVision()) {
            //Get latest vision target
            VisionTarget target = Vision.getInstance().getLatestVisionTarget();
            //Set automated turret aim
            AutomatedTurretSuperstructure.getInstance().setVisionAim(target);
            //Set shooter speed
            Shooter.getInstance().setShooterSpeed(
                    AutomatedTurretSuperstructure.getInstance().computeShooterRPMFromDistance(target.getDistance()));
            this.error = target.getTurretRelativeYaw().getHeading();
        } else {
            //If no vision target is detected, lock the target onto the last detected vision target position
            AutomatedTurretSuperstructure.getInstance().setFieldRelativeAimRotation(
                    AutomatedTurretSuperstructure.getInstance().getFieldRelativeRotation());
            this.error = 9999;
        }
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
