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

package com.github.mittyrobotics.shooter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinFlywheel extends CommandBase {
    private double speed, threshold;
    private double prevSpeed;
    private boolean hasPassed = false, hasPassed2 = true, hasPassed3 = false;
    private double t, t2;

    public SpinFlywheel(double speed, double threshold) {
        super();
        this.speed = speed;
        this.threshold = threshold;
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
//        ShooterSubsystem.getInstance().setShooterSpeed(speed);
        ShooterSubsystem.getInstance().bangControl(speed, threshold);
        System.out.println("RPM: " + ShooterSubsystem.getInstance().getShooterSpeed());
//        if(Math.abs(ShooterSubsystem.getInstance().getShooterSpeed() - prevSpeed) > 100){
//            System.out.println("ERROR: " + (ShooterSubsystem.getInstance().getShooterSpeed() - prevSpeed));
//        }
//        prevSpeed = ShooterSubsystem.getInstance().getShooterSpeed();
//        if(ShooterSubsystem.getInstance().getShooterSpeed() > 3800 && !hasPassed){
//            hasPassed = true;
//        }
//        if(ShooterSubsystem.getInstance().getShooterSpeed() < 3800 && hasPassed){
//            t = Timer.getFPGATimestamp();
//            hasPassed2 = false;
//        }
//        if(ShooterSubsystem.getInstance().getShooterSpeed() > 3800 && !hasPassed2){
//            t2 = Timer.getFPGATimestamp() - t;
//            hasPassed3 = true;
//        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("TIME: " + t2);
    }

    @Override
    public boolean isFinished() {
        return DriverStation.getInstance().isDisabled();
    }
}
