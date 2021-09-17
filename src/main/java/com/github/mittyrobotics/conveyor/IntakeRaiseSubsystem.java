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

package com.github.mittyrobotics.conveyor;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Intake Piston Subsystem to extend or retract the {@link IntakeSubsystem}
 */
public class IntakeRaiseSubsystem extends SubsystemBase implements IMotorSubsystem {

    /**
     * {@link IntakeSubsystem} instance
     */
    private static IntakeRaiseSubsystem instance;

    /**
     * Intake piston's {@link CANSparkMax}
     */
    private CANSparkMax raiseMotor;

    private DigitalInput lowerSwitch, raiseSwitch;

    private PIDController controller;

    private boolean isLowered;

    /**
     * Calls {@link SubsystemBase} constructor and names the subsystem "Intake Piston"
     */
    private IntakeRaiseSubsystem() {
        super();
        setName("Intake Raise");
    }

    /**
     * Returns the {@link IntakeRaiseSubsystem} instance.
     *
     * @return the {@link IntakeRaiseSubsystem} instance.
     */
    public static IntakeRaiseSubsystem getInstance() {
        if (instance == null) {
            instance = new IntakeRaiseSubsystem();
        }
        return instance;
    }

    /**
     * Update the intake piston's dashboard values
     */
    @Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("Is Intake Extended", isIntakeLowered());
        SmartDashboard.putNumber("Intake Encoder", getPosition());
        SmartDashboard.putBoolean("Intake Limit Switch 0", getSwitch(0));
        SmartDashboard.putBoolean("Intake Limit Switch 1", getSwitch(1));
    }

    /**
     * Initialize the intake piston's hardware
     */
    @Override
    public void initHardware() {
        raiseMotor = new CANSparkMax(IntakeConstants.INTAKE_RAISE_MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        raiseMotor.restoreFactoryDefaults();

        raiseMotor.setInverted(IntakeConstants.INTAKE_RAISE_INVERSION);
        raiseMotor.setIdleMode(IntakeConstants.INTAKE_RAISE_NEUTRAL_MODE);

        resetEncoder();

        controller = new PIDController(IntakeConstants.INTAKE_KP, IntakeConstants.INTAKE_KI, IntakeConstants.INTAKE_KD);

        lowerSwitch = new DigitalInput(IntakeConstants.INTAKE_LOWER_SWITCH_ID);
        raiseSwitch = new DigitalInput(IntakeConstants.INTAKE_RAISE_SWITCH_ID);

        lowerIntake();
    }

    @Override
    public void resetEncoder() {
        raiseMotor.getEncoder().setPosition(0);
    }

    public void runPositionPID(double position) {
        setMotor(controller.calculate(position));
    }

    @Override
    public double getPosition() {
        return raiseMotor.getEncoder().getPosition();
    }

    public boolean getSwitch(int id) {
        if(id == 0) return lowerSwitch.get();
        return raiseSwitch.get();
    }

    /**
     * Returns if the intake is extended
     *
     * @return if the {@link IntakeSubsystem} is extended
     */
    public boolean isIntakeLowered() {
        return isLowered;
    }

    /**
     * Extends the {@link IntakeSubsystem}
     */
    public void lowerIntake() {
        controller.setSetpoint(IntakeConstants.INTAKE_LOWERED_POSITION);
        isLowered = true;
        ConveyorSubsystem.getInstance().resetBallCount();
    }

    /**
     * Retracts the {@link IntakeSubsystem}
     */
    public void raiseIntake() {
        controller.setSetpoint(IntakeConstants.INTAKE_RAISED_POSITION);
        isLowered = false;
        ConveyorSubsystem.getInstance().resetBallCount();
    }

    @Override
    public void setMotor(double percent) {
        raiseMotor.set(percent);
    }

    @Override
    public void overrideSetMotor(double percent) {
        raiseMotor.set(percent);
    }

    public void stop() {
        overrideSetMotor(0);
    }

    public void testReset() {
        raiseIntake();
        while(getLimitReached()) {
            setMotor(IntakeConstants.INTAKE_MANUAL_RAISE_SPEED);
        }
        stop();
        resetEncoder();
    }

    public boolean getLimitReached() {
        return (isIntakeLowered() && getSwitch(0))
                || (!isIntakeLowered() && getSwitch(1));
    }
}