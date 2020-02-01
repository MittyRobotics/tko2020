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
        addRequirements(TurretSubsystem.getInstance());
        this.setpoint = setpoint;
//		TurretSubsystem.getInstance().zeroEncoder();

    }

    @Override
    public void initialize() {
        setpoint += TurretSubsystem.getInstance().getAngle();
        this.setpoint %= 4000 / Constants.TICKS_PER_ANGLE;
        if (setpoint < 0) {
            this.setpoint += 4000 / Constants.TICKS_PER_ANGLE;
        }
        controller = new PIDController(Constants.TurretP, Constants.TurretI, Constants.TurretD);
        controller.enableContinuousInput(0, 4000 / Constants.TICKS_PER_ANGLE);
//		TurretSubsystem.getInstance().zeroEncoder();
//		angleValue = TurretSubsystem.getInstance().getAngle();
//		isDone = false;
    }

    @Override
    public void execute() {
        System.out.println("This setpoint" + this.setpoint);
        System.out.println("Get setpoint: " + controller.getSetpoint());

//		if ((TurretSubsystem.getInstance().getAngle()<(setpoint+0.5)) && ((setpoint-0.5)< TurretSubsystem.getInstance().getAngle())) {
//			isDone = true;
//		} else {
        TurretSubsystem.getInstance()
                .manualSetTurret(controller.calculate(TurretSubsystem.getInstance().getAngle(), setpoint));
//		}

//		TurretSubsystem.getInstance().changeAngle(40);


//		TurretSubsystem.getInstance().manualSetTurret(OI.getInstance().getJoystick1().getY());
//		System.out.println("Angle value" + (TurretSubsystem.getInstance().getAngle()-angleValue));
    }

    @Override
    public void end(boolean interrupted) {
        controller.close();
        System.out.println("DONE WITH MAG");

    }

    @Override
    public boolean isFinished() {
        return DriverStation.getInstance().isDisabled();
    }
}
