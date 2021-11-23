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

package com.github.mittyrobotics.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Drivetrain Subsystem to move the chassis
 */
public class DriveMotorSubsystem extends SubsystemBase {


    private static DriveMotorSubsystem instance;


    private final WPI_TalonFX[] motors = new WPI_TalonFX[2];

    private DriveMotorSubsystem() {
        super();
        setName("Drivetrain");
    }

    public static DriveMotorSubsystem getInstance() {
        if (instance == null) {
            instance = new DriveMotorSubsystem();
        }
        return instance;
    }

    public void initHardware() {

        motors[0] = new WPI_TalonFX(DriveConstants.MOTOR_1);
        motors[1] = new WPI_TalonFX(DriveConstants.MOTOR_2);

        motors[0].configFactoryDefault();
        motors[1].configFactoryDefault();

        motors[0].setInverted(true);
        motors[1].setInverted(true);

    }


    public void driveMotor(int index, double speed) {
        motors[index].set(speed);
    }

    public void brake(int index) {
        motors[index].set(0);
    }

    public void brake() {
        for(WPI_TalonFX m : motors) {
            m.set(0);
        }
    }
}