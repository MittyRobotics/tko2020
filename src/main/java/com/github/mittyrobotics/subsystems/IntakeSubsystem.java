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

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.constants.IntakeConstants;
import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase implements ISubsystem {
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
        intakeWheel.setInverted(IntakeConstants.INTAKE_WHEEL_INVERSION);
        intakeWheel.configFactoryDefault();
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("Is Intaking", intakeWheel.get() > 0);
    }

    private void setPercentOutput(double percent) {
//        if (IntakePistonSubsystem.getInstance().isExtended()) {
        intakeWheel.set(ControlMode.PercentOutput, percent);
//        } else {
//            intakeWheel.set(ControlMode.PercentOutput, 0);
//        }
    }

    public void setIntaking() {
//        if (ConveyorSubsystem.getInstance().getTotalBallCount() < 5) {
        if (!ConveyorSubsystem.getInstance().getEntranceSwitch()) {
            setPercentOutput(IntakeConstants.INTAKE_SPEED_FAST);
        } else {
            setPercentOutput(IntakeConstants.INTAKE_SPEED_SLOW);
        }
//        } else {
//            setPercentOutput(0);
//        }
    }

    public void setIntakingShooting() {
        setPercentOutput(IntakeConstants.INTAKE_SPEED_SLOW);
    }

    public void setOuttaking() {
        setPercentOutput(IntakeConstants.OUTTAKE_SPEED);
    }

    public void stopIntake() {
        setPercentOutput(0);
    }

    public void setManual(double percent) {
        intakeWheel.set(percent);
    }

}