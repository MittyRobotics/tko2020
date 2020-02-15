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
import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.turret.Turret;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class EasyVisionCommand extends CommandBase {

    public EasyVisionCommand() {
        super();
        addRequirements(Turret.getInstance());
//        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        VisionTarget target = Vision.getInstance().getLatestVisionTarget();
        double p = -0.10;
        Turret.getInstance().manualSetTurret(p * target.getTurretRelativeYaw().getHeading());
        double rpm = rpmEquation(target.getDistance());
        SmartDashboard.putNumber("vision_dist", target.getDistance());
        //Shooter.getInstance().setShooterSpeed(rpm);
    }

    private double rpmEquation(double distance) {
        return 9.766 * Math.pow(10, -3) * (distance * distance) - 2.4741 * distance + 3785.7830;
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
