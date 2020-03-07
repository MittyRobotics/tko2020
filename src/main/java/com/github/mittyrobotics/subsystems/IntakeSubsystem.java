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

package com.github.mittyrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.constants.ConveyorConstants;
import com.github.mittyrobotics.constants.IntakeConstants;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase implements IMotorSubsystem {
    private static IntakeSubsystem instance;
    private WPI_TalonSRX intakeWheel;

    private IntakeSubsystem() {
        super();
        setName("Intake");
    }

    public static IntakeSubsystem getInstance() {
        if (instance == null) {
            instance = new IntakeSubsystem();
        }
        return instance;
    }

    @Override
    public void initHardware() {
        intakeWheel = new WPI_TalonSRX(IntakeConstants.INTAKE_WHEEL_ID);
        intakeWheel.configFactoryDefault();
        intakeWheel.setInverted(IntakeConstants.INTAKE_WHEEL_INVERSION);
        intakeWheel.setNeutralMode(IntakeConstants.INTAKE_NEUTRAL_MODE);
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("Is Intaking", intakeWheel.get() > 0);
    }

    public void setMotor(double percent) {
        if (IntakePistonSubsystem.getInstance().isPistonExtended()) {
            overrideSetMotor(percent);
        } else {
            overrideSetMotor(0);
        }
    }

    @Override
    public void overrideSetMotor(double percent) {
        intakeWheel.set(percent);
    }

    public void setIntaking() {
        if (ConveyorSubsystem.getInstance().getBallCount() < ConveyorConstants.MAXIMUM_BALL_COUNT) {
            if (!ConveyorSubsystem.getInstance().getSwitch()) {
                setMotor(IntakeConstants.INTAKE_SPEED_FAST);
            } else {
                setMotor(IntakeConstants.INTAKE_SPEED_SLOW);
            }
        } else {
            stopMotor();
        }
    }

    public void setIntakingShooting() {
        setMotor(IntakeConstants.INTAKE_SPEED_SLOW);
    }

    public void setOuttaking() {
        setMotor(IntakeConstants.OUTTAKE_SPEED);
    }

    @Override
    public double getVelocity(){
        return intakeWheel.get();
    }

}