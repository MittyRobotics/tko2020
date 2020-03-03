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

import com.github.mittyrobotics.constants.ClimberConstants;
import com.github.mittyrobotics.constants.RobotSide;
import com.github.mittyrobotics.util.interfaces.ISubsystem;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class WinchSubsystem extends SubsystemBase implements ISubsystem {
    private static WinchSubsystem instance;

    private CANSparkMax leftWinch, rightWinch;

    private CANEncoder leftEncoder, rightEncoder;

    private CANPIDController leftPIDController, rightPIDController;

    private WinchSubsystem() {
        super();
    }

    public static WinchSubsystem getInstance() {
        if (instance == null) {
            instance = new WinchSubsystem();
        }
        return instance;
    }

    @Override
    public void initHardware() {
        leftWinch = new CANSparkMax(ClimberConstants.LEFT_WINCH_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightWinch = new CANSparkMax(ClimberConstants.RIGHT_WINCH_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftWinch.restoreFactoryDefaults();
        rightWinch.restoreFactoryDefaults();
        leftWinch.setInverted(ClimberConstants.LEFT_WINCH_INVERSION);
        rightWinch.setInverted(ClimberConstants.RIGHT_WINCH_INVERSION);
        this.leftEncoder = leftWinch.getEncoder();
        this.rightEncoder = rightWinch.getEncoder();
        this.leftPIDController = leftWinch.getPIDController();
        this.rightPIDController = rightWinch.getPIDController();
    }

    @Override
    public void updateDashboard() {

    }

    public double getEncoderTicks(RobotSide side) {
        if (side == RobotSide.LEFT) {
            return leftEncoder.getPosition();
        } else {
            return rightEncoder.getPosition();
        }
    }


    public void setSpeed(double speed, RobotSide side) {
        if (side == RobotSide.LEFT) {
            leftWinch.set(speed);
        } else {
            rightWinch.set(speed);
        }
    }
}