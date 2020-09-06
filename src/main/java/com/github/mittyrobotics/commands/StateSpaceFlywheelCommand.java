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

import com.github.mittyrobotics.datatypes.units.Conversions;
import com.github.mittyrobotics.motion.statespace.LinearQuadraticRegulator;
import com.github.mittyrobotics.motion.statespace.MatrixUtils;
import com.github.mittyrobotics.motion.statespace.motors.Motor;
import com.github.mittyrobotics.motion.statespace.motors.NEOMotor;
import com.github.mittyrobotics.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import org.apache.commons.math3.util.MathUtils;
import org.ejml.simple.SimpleMatrix;

public class StateSpaceFlywheelCommand extends CommandBase {
    private double setpointRPM;
    private double previousTime;
    private double startTime;

    public StateSpaceFlywheelCommand(double setpointRPM) {
        this.setpointRPM = setpointRPM;
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        Motor motor = new NEOMotor(2);
        double momentOfInertia = 0.04482798121311679;
        double gearReduction = 1;
        double maxVoltage = 12;
        double dt = 0.02;

    }

    private double previousVelocity = 0;
    @Override
    public void execute() {
        double currentTime = System.currentTimeMillis();
        double deltaTime = (currentTime - previousTime) / 1000.0;
        this.previousTime = currentTime;

        double currentRPM = ShooterSubsystem.getInstance().getVelocity();

        double voltage = 0;

        SmartDashboard.putNumber("Shooter RPM Setpoint", setpointRPM);
        SmartDashboard.putNumber("Shooter RPM", currentRPM);
        SmartDashboard.putNumber("Shooter Percent Output", voltage);

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}