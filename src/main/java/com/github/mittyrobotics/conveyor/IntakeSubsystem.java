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

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Intake subsystem to move balls into the {@link ConveyorSubsystem}
 */
public class IntakeSubsystem extends SubsystemBase implements IMotorSubsystem {
    /**
     * {@link IntakeSubsystem} instance
     */
    private static IntakeSubsystem instance;

    /**
     * Intake's {@link WPI_TalonSRX}
     */
    private WPI_TalonSRX intakeWheel;

    /**
     * Calls {@link SubsystemBase} constructor and names the subsystem "Intake"
     */
    private IntakeSubsystem() {
        super();
        setName("Intake");
    }

    /**
     * Returns the {@link IntakeSubsystem} instance.
     *
     * @return the {@link IntakeSubsystem} instance.
     */
    public static IntakeSubsystem getInstance() {
        if (instance == null) {
            instance = new IntakeSubsystem();
        }
        return instance;
    }

    /**
     * Initialize the intake's hardware
     */
    @Override
    public void initHardware() {
        intakeWheel = new WPI_TalonSRX(IntakeConstants.INTAKE_WHEEL_ID);
        intakeWheel.configFactoryDefault();
        intakeWheel.setInverted(IntakeConstants.INTAKE_WHEEL_INVERSION);
        intakeWheel.setNeutralMode(IntakeConstants.INTAKE_NEUTRAL_MODE);
    }

    /**
     * Update the intake's dashboard values
     */
    @Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("Is Intaking", intakeWheel.get() > 0);
    }

    /**
     * Sets the motor to a spin at a percentage of the {@link IntakePistonSubsystem} is extended
     *
     * @param percent the percentage to run the motor at
     */
    public void setMotor(double percent) {
        if (IntakePistonSubsystem.getInstance().isPistonExtended()) {
            overrideSetMotor(percent);
        } else {
            overrideSetMotor(0);
        }
    }

    /**
     * Runs the intake wheels regardless of the {@link IntakePistonSubsystem} state
     *
     * @param percent the percentage to run the motor at
     */
    @Override
    public void overrideSetMotor(double percent) {
        intakeWheel.set(percent);
    }

    public void setIntaking(double speed) {
        if (ConveyorSubsystem.getInstance().getBallCount() < ConveyorConstants.MAXIMUM_BALL_COUNT) {
            setMotor(speed);
        } else {
            stopMotor();
        }
    }

    /**
     * Sets the intake to move at the speed needed when shooting balls
     */
    public void setIntakingShooting() {
        setMotor(IntakeConstants.INTAKE_SPEED_SLOW);
    }

    /**
     * Sets the intake ot move at the outtaking ball speed
     */
    public void setOuttaking() {
        setMotor(IntakeConstants.OUTTAKE_SPEED);
    }

    /**
     * Gets the current velocity of the intake
     *
     * @return current intake wheel velocity in {@link ControlMode#PercentOutput}
     */
    @Override
    public double getVelocity() {
        return intakeWheel.get();
    }

}