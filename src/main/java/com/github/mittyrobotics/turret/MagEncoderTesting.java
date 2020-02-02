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

package com.github.mittyrobotics.turret;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MagEncoderTesting extends CommandBase {
    private double setpoint;
    private PIDController controller;
    private double angleValue;
    private boolean isDone = false;

    public MagEncoderTesting(double setpoint) {
        super();
        System.out.println(setpoint + " Origin Set");
        addRequirements(TurretSubsystem.getInstance());
        this.setpoint = setpoint;
    }

    @Override
    public void initialize() {
        setpoint *= Constants.TICKS_PER_ANGLE;
        System.out.println("Set: " + setpoint);
        this.setpoint += TurretSubsystem.getInstance().getEncoderValue();
        this.setpoint %= 3911;
        if(this.setpoint < 0){
            this.setpoint += 3911;
        }
        controller = new PIDController(Constants.TurretP, Constants.TurretI, Constants.TurretD);
        controller.enableContinuousInput(0, 3911-1);
//		TurretSubsystem.getInstance().zeroEncoder();
//		angleValue = TurretSubsystem.getInstance().getAngle();
//		isDone = false;
        System.out.println("Set1: " + setpoint);
    }

    @Override
    public void execute() {
        TurretSubsystem.getInstance()
                .manualSetTurret(controller.calculate(TurretSubsystem.getInstance().getEncoderValue(),
                        setpoint));
    }

    @Override
    public void end(boolean interrupted) {
        //controller.close();
        System.out.println("DONE WITH MAG");

    }

    @Override
    public boolean isFinished() {
        return Math.abs(setpoint - TurretSubsystem.getInstance().getEncoderValue())/Constants.TICKS_PER_ANGLE < 2;
    }
}
