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
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.constants.BufferConstants;
import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BufferSubsystem extends SubsystemBase implements ISubsystem {
    private static BufferSubsystem instance;
    private WPI_TalonSRX bufferWheel;

    private BufferSubsystem() {
        super();
        setName("Buffer");
    }

    public static BufferSubsystem getInstance() {
        if (instance == null) {
            instance = new BufferSubsystem();
        }
        return instance;
    }

    public void initHardware() {
        bufferWheel = new WPI_TalonSRX(BufferConstants.BUFFER_WHEEL_ID);
        bufferWheel.configFactoryDefault();
        bufferWheel.setInverted(BufferConstants.BUFFER_WHEEL_INVERSION);
        bufferWheel.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        bufferWheel.setSensorPhase(BufferConstants.BUFFER_WHEEL_ENCODER_INVERSION);
    }

    public void resetEncoder() {
        bufferWheel.setSelectedSensorPosition(0);
    }

    @Override
    public void updateDashboard() {

    }

    private void moveWheel(double speed) {
        bufferWheel.set(ControlMode.PercentOutput, speed);

    }

    public void manualBufferSpeed(double speed) {
        if (Math.abs(speed) > 0.1) {
            bufferWheel.set(ControlMode.PercentOutput, speed);
//            System.out.println("Buffer Percent Output: " + speed);
        } else {
            bufferWheel.set(ControlMode.PercentOutput, 0);
        }

    }

    public double getBufferPosition() {
        return bufferWheel.getSelectedSensorPosition();
    }

    public void bufferLock() {
        moveWheel(BufferConstants.LOCK_SPEED);
    }

    public void bufferRelease() {
        moveWheel(BufferConstants.RELEASE_SPEED);
    }
}