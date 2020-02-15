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

package com.github.mittyrobotics.turret;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase implements ISubsystem {
    /**
     * {@link Turret} instance.
     */
    private static Turret instance;
    /**
     * Turret {@link WPI_TalonSRX}
     */
    private WPI_TalonSRX turretTalon;
    /**
     * Left and right limit switches for the turret in the form of a {@link DigitalInput}.
     */
    private DigitalInput limitSwitchLeft, limitSwitchRight;
    /**
     * Turret's {@link PIDController}. Calculates percent output values to apply to the motor based on a PID loop.
     * <p>
     * Needs to be updated periodically with the {@link #updateTurretControlLoop()} method.
     */
    private PIDController turretController;

    private Turret() {
        super();
        setName("Turret");
    }

    /**
     * Returns the {@link Turret}'s {@link SubsystemBase} instance.
     *
     * @return the {@link Turret}'s {@link SubsystemBase} instance.
     */
    public static Turret getInstance() {
        if (instance == null) {
            instance = new Turret();
        }
        return instance;
    }

    /**
     * Initializes the turret's hardware.
     */
    @Override
    public void initHardware() {
        //Config talon
        turretTalon = new WPI_TalonSRX(Constants.TALON_ID);
        turretTalon.config_kP(0, Constants.TURRET_P);
        turretTalon.config_kI(0, Constants.TURRET_I);
        turretTalon.config_kD(0, Constants.TURRET_D);
//      limitSwitchLeft = new DigitalInput(Constants.TURRET_SWITCH_ID);
//      limitSwitchRight = new DigitalInput(Constants.TURRET_SWITCH_2_ID);
        turretTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);

        //Initialize PIDController
        turretController = new PIDController(Constants.TURRET_P, Constants.TURRET_I, Constants.TURRET_D);
        turretController.enableContinuousInput(0, Constants.REVOLUTION_TICKS - 1);

    }

    @Override
    public void updateDashboard() {

    }

    /**
     * Set the turret's percent output, limiting it's range from the limit switches.
     *
     * @param percent the turret motor percent output.
     */
    public void setTurretPercent(double percent) {
        if (!limitSwitchLeft.get() && !limitSwitchRight.get()) {
            turretTalon.set(ControlMode.PercentOutput, percent);
        } else {
            if (limitSwitchLeft.get()) {
                if (percent > 0) {
                    turretTalon.set(ControlMode.PercentOutput, percent);
                } else {
                    turretTalon.set(ControlMode.PercentOutput, 0);
                }
            } else {
                if (percent < 0) {
                    turretTalon.set(ControlMode.PercentOutput, percent);
                } else {
                    turretTalon.set(ControlMode.PercentOutput, 0);
                }
            }
        }
    }

    /**
     * Sets the turret's robot-relative angle.
     *
     * @param angle the robot-relative angle to set the turret to.
     */
    public void setTurretAngle(double angle) {
        angle = capAngleSetpoint(angle);
        angle *= Constants.TICKS_PER_ANGLE;
        turretController.setSetpoint(angle);
    }

    /**
     * Changes the turret's robot-relative angle by <code>angle</code>.
     * <p>
     * Follows standard polar coordinate system, 0 is straight forward, negative angle changes it to the right,
     * positive angle changes it to the left.
     *
     * @param angle the angle to change the turret's robot-relative angle by.
     */
    public void changeTurretAngle(double angle) {
        setTurretAngle(angle + getAngle());
    }

    /**
     * Updates the turret's {@link PIDController} control loop.
     * <p>
     * This should be called periodically whenever the turret is automated.
     */
    public void updateTurretControlLoop() {
        setTurretPercent(turretController.calculate(getEncoderPosition()));
    }

    /**
     * Caps the angle setpoint from 0 to the maximum angle of the turret. Used for the {@link PIDController}'s
     * continuous mode.
     *
     * @param angle the angle to cap.
     * @return the capped angle.
     */
    private double capAngleSetpoint(double angle) {
        angle %= Constants.REVOLUTION_TICKS / Constants.TICKS_PER_ANGLE;
        if (angle < 0) {
            angle += Constants.REVOLUTION_TICKS / Constants.TICKS_PER_ANGLE;
        }
        return angle;
    }

    /**
     * Set the turret's percent output. Overrides limit switches (NOT RECOMMENDED!).
     *
     * @param percent          the turret motor percent output.
     * @param iKnowWhatImDoing do you know what you are doing?
     */
    public void overrideSetTurretPercent(double percent, boolean iKnowWhatImDoing) {
        if (iKnowWhatImDoing) {
            turretTalon.set(ControlMode.PercentOutput, percent);
        }
    }

    /**
     * Returns the turret's encoder position in ticks.
     *
     * @return the turret's encoder position in ticks.
     */
    public double getEncoderPosition() {
        return turretTalon.getSelectedSensorPosition();
    }

    /**
     * Returns the left limit switch's value.
     *
     * @return the left limit switch's value.
     */
    public boolean getLeftLimitSwitch() {
        return limitSwitchLeft.get();
    }

    /**
     * Returns the right limit switch's value.
     *
     * @return the right limit switch's value.
     */
    public boolean getRightLimitSwitch() {
        return limitSwitchRight.get();
    }

    /**
     * Returns the turret's robot-relative angle calculated by the encoder value divided by a ticks per inch constant.
     *
     * @return the turret's robot-relative angle
     */
    public double getAngle() {
        return turretTalon.getSelectedSensorPosition() / Constants.TICKS_PER_ANGLE;
    }

    /**
     * Returns the turret's {@link PIDController} error.
     * <p>
     * This is the difference between the setpoint and the actual value of the encoder, measured in ticks.
     *
     * @return the turret's {@link PIDController} error.
     */
    public double getError() {
        return turretController.getPositionError();
    }

    /**
     * Returns the turret's {@link PIDController} setpoint.
     * <p>
     * This is the setpoint of the PID loop measured in ticks.
     *
     * @return the turret's {@link PIDController} setpoint.
     */
    public double getSetpoint() {
        return turretController.getSetpoint();
    }
}
