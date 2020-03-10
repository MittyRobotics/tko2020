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
import com.github.mittyrobotics.util.interfaces.IDualMotorSubsystem;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class WinchSubsystem extends SubsystemBase implements IDualMotorSubsystem {
    private static WinchSubsystem instance;

    private CANSparkMax leftWinchSpark, rightWinchSpark;

    private CANEncoder leftWinchEncoder, rightWinchEncoder;

    private PIDController leftController, rightController, auxController;

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
        leftWinchSpark = new CANSparkMax(ClimberConstants.LEFT_WINCH_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightWinchSpark = new CANSparkMax(ClimberConstants.RIGHT_WINCH_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftWinchSpark.restoreFactoryDefaults();
        rightWinchSpark.restoreFactoryDefaults();
        leftWinchSpark.setInverted(ClimberConstants.LEFT_WINCH_INVERSION);
        rightWinchSpark.setInverted(ClimberConstants.RIGHT_WINCH_INVERSION);
        this.leftWinchEncoder = leftWinchSpark.getEncoder();
        this.rightWinchEncoder = rightWinchSpark.getEncoder();
        leftController = new PIDController(0, 0, 0);
        rightController = new PIDController(0, 0, 0);
        auxController = new PIDController(0, 0, 0);
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Left Winch Position", getLeftPosition());
        SmartDashboard.putNumber("Right Winch Position", getRightPosition());
    }

    @Override
    public void overrideSetMotor(double leftPercent, double rightPercent) {
        leftWinchSpark.set(leftPercent);
        rightWinchSpark.set(rightPercent);
    }

    @Override
    public void resetEncoder() {
        leftWinchEncoder.setPosition(0);
        rightWinchEncoder.setPosition(0);
    }

    @Override
    public double getLeftPosition() {
        return leftWinchEncoder.getPosition();
    }

    @Override
    public double getRightPosition() {
        return rightWinchEncoder.getPosition();
    }

    public void setupWinchPID(double leftSetpoint, double rightSetpoint){
        leftController.setSetpoint(leftSetpoint);
        rightController.setSetpoint(rightSetpoint);
        auxController.setSetpoint(leftSetpoint - rightSetpoint);
    }
    public void runWinchControlLoop() {
        double lSpeed = leftController.calculate(getLeftPosition());
        double rSpeed = rightController.calculate(getRightPosition());
        double auxSpeed = auxController.calculate(getLeftPosition() - getRightPosition());
        setMotor(lSpeed + auxSpeed, rSpeed - auxSpeed);
    }


    @Override
    public void setMotor(double left, double right){
        if(WinchLockSubsystem.getInstance().isPistonExtended()){
            left = Math.min(left, 0);
            right = Math.min(right, 0);
        }
        overrideSetMotor(left, right);
    }

    public double getLeftError(){
        return leftController.getPositionError();
    }

    public double getRightError(){
        return rightController.getPositionError();
    }

    public double getAuxError(){
        return auxController.getPositionError();
    }
}