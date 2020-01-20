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

package com.github.mittyrobotics.drive;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrainSparks extends SubsystemBase {

    private static DriveTrainSparks instance;
    private CANSparkMax leftSpark1;
    private CANSparkMax leftSpark2;
    private CANSparkMax rightSpark1;
    private CANSparkMax rightSpark2;

    private DriveTrainSparks() {
        super();
        setName("DriveTrainSparks");
        //setDefaultCommand(new sampleCommand());
    }

    public static DriveTrainSparks getInstance() {
        if (instance == null) {
            instance = new DriveTrainSparks();
        }
        return instance;
    }

    @Override
    public void periodic() {

    }

    public void initHardware() {
        leftSpark1 = new CANSparkMax(Constants.LEFT_SPARK_1_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSpark2 = new CANSparkMax(Constants.LEFT_SPARK_2_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        rightSpark1 = new CANSparkMax(Constants.RIGHT_SPARK_1_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSpark2 = new CANSparkMax(Constants.RIGHT_SPARK_2_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftSpark1.restoreFactoryDefaults();
        leftSpark2.restoreFactoryDefaults();
        rightSpark1.restoreFactoryDefaults();
        rightSpark2.restoreFactoryDefaults();
    }

    public void tankDrive(double left, double right) {
        if (Math.abs(left) > 0.1) {
            leftSpark1.set(left);
            leftSpark2.set(left);
        } else {
            leftSpark1.stopMotor();
            leftSpark2.stopMotor();

        }

        if (Math.abs(right) > 0.1) {
            rightSpark1.set(right);
            rightSpark2.set(right);
        } else {
            rightSpark1.stopMotor();
            rightSpark2.stopMotor();
        }
    }

    public CANSparkMax getLeftSpark() {
        return leftSpark1;
    }

    public CANEncoder getLeftEncoder() {
        return leftSpark1.getEncoder();
    }
}