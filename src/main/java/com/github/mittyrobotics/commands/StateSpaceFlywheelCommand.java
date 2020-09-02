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
import com.github.mittyrobotics.motion.controllers.StateSpaceController;
import com.github.mittyrobotics.motion.statespace.KalmanFilter;
import com.github.mittyrobotics.motion.statespace.LinearQuadraticRegulator;
import com.github.mittyrobotics.motion.statespace.MatrixUtils;
import com.github.mittyrobotics.motion.statespace.Plant;
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
    private StateSpaceController loop;
    private double previousTime;
    private double startTime;

    public StateSpaceFlywheelCommand(double setpointRPM) {
        this.setpointRPM = setpointRPM;
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {
//        System.out.println(MatrixUtils.discreteAlgebraicRiccatiEquation(new SimpleMatrix(new double[][]{{5}}),
//                new SimpleMatrix(new double[][]{{5}}), new SimpleMatrix(new double[][]{{5}}),
//                new SimpleMatrix(new double[][]{{5}})));
        Motor motor = new NEOMotor(2);
        double momentOfInertia = 0.04482798121311679;
        double gearReduction = 1;
        double maxVoltage = 12;
        double dt = 0.02;

        double modelVelocityAccuracyGain = 1.0;
        double measurementAccuracyGain = 0.01;
        double velocityTolerance = 0.4;
        double voltageTolerance = 12;
        double qWeight = 0.0000005;

        Plant plant = Plant.createFlywheelPlant(motor, momentOfInertia, gearReduction, maxVoltage, dt);

        KalmanFilter observer = new KalmanFilter(plant,
                new SimpleMatrix(new double[][]{{modelVelocityAccuracyGain}}),
                new SimpleMatrix(new double[][]{{measurementAccuracyGain}}));

        LinearQuadraticRegulator controller = new LinearQuadraticRegulator(plant,
                new SimpleMatrix(new double[][]{{velocityTolerance}}),
                new SimpleMatrix(new double[][]{{voltageTolerance}}), qWeight);

        this.loop = new StateSpaceController(plant, controller, observer);

        this.previousTime = System.currentTimeMillis();
        this.startTime = System.currentTimeMillis();
    }

    private double previousVelocity = 0;
    @Override
    public void execute() {
        double currentTime = System.currentTimeMillis();
        double deltaTime = (currentTime - previousTime) / 1000.0;
        this.previousTime = currentTime;

        double currentRPM = ShooterSubsystem.getInstance().getVelocity();
        double voltage =
                loop.calculate(new SimpleMatrix(new double[][]{{currentRPM * Conversions.RPM_TO_RAD_PER_SECOND}}),
                        new SimpleMatrix(new double[][]{{setpointRPM * Conversions.RPM_TO_RAD_PER_SECOND}}), deltaTime)
                        .get(0) / 12.0;
//        voltage = MathUtil.clamp(voltage, -.3, .3);

//        double voltage = 5.0 / 12.0;

        System.out.println(voltage + ", " + currentRPM * Conversions.RPM_TO_RAD_PER_SECOND + ", " + (System.currentTimeMillis() - startTime) + ", " + (currentRPM * Conversions.RPM_TO_RAD_PER_SECOND - previousVelocity)/deltaTime);
        previousVelocity = currentRPM*Conversions.RPM_TO_RAD_PER_SECOND;

        SmartDashboard.putNumber("Shooter RPM Setpoint", setpointRPM);
        SmartDashboard.putNumber("Shooter RPM", currentRPM);
        SmartDashboard.putNumber("Shooter Percent Output", voltage);

        ShooterSubsystem.getInstance().overrideSetMotor(voltage);
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}