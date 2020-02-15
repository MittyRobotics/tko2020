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
import com.github.mittyrobotics.autonomous.commands.EasyVisionCommand;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {

    private static Turret instance;
    private WPI_TalonSRX turretTalon;
    private DigitalInput limitSwitchLeft, limitSwitchRight;
    private PIDController turretController;


    private Turret() {
        super();
        setName("Turret");
    }

    public static Turret getInstance() {
        if (instance == null) {
            instance = new Turret();
        }
        return instance;
    }

    public void initHardware() {
        //Config talon
        turretTalon = new WPI_TalonSRX(Constants.TALON_ID);
        turretTalon.config_kP(0, Constants.TURRET_P);
        turretTalon.config_kI(0, Constants.TURRET_I);
        turretTalon.config_kD(0, Constants.TURRET_D);
//        limitSwitchLeft = new DigitalInput(Constants.TURRET_SWITCH_ID);
//        limitSwitchRight = new DigitalInput(Constants.TURRET_SWITCH_2_ID);
        turretTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);

        //Initialize PIDController
        turretController = new PIDController(Constants.TURRET_P, Constants.TURRET_I, Constants.TURRET_D);
        turretController.enableContinuousInput(0, Constants.REVOLUTION_TICKS - 1);

    }

    public void setTurretSpeed(double speed) {
        if (!limitSwitchLeft.get() && !limitSwitchRight.get()) {
            turretTalon.set(ControlMode.PercentOutput, speed);
        } else {
            if (limitSwitchLeft.get()) {
                if (speed > 0) {
                    turretTalon.set(ControlMode.PercentOutput, speed);
                } else {
                    turretTalon.set(ControlMode.PercentOutput, 0);
                }
            } else {
                if (speed < 0) {
                    turretTalon.set(ControlMode.PercentOutput, speed);
                } else {
                    turretTalon.set(ControlMode.PercentOutput, 0);
                }
            }
        }
    }

    public void setTurretAngle(double angle) {
        angle *= Constants.TICKS_PER_ANGLE;
        angle = capAngleSetpoint(angle);
        turretController.setSetpoint(angle);
    }

    public void changeTurretAngle(double angle) {
        setTurretAngle(angle + getAngle());
    }

    /**
     * Updates the turret's {@link PIDController} control loop.
     * <p>
     * This should be called periodically whenever the turret should be running an angle control loop.
     */
    public void updateTurretControlLoop() {
        manualSetTurret(turretController.calculate(getTurretPosition()));
    }

    private double capAngleSetpoint(double angle) {
        angle %= Constants.REVOLUTION_TICKS;
        if (angle < 0) {
            angle += Constants.REVOLUTION_TICKS;
        }
        return angle;
    }

    public void manualSetTurret(double speed) {
        if (Math.abs(speed) > 0.05) {
            turretTalon.set(ControlMode.PercentOutput, speed);
        } else {
            turretTalon.set(ControlMode.PercentOutput, 0);
        }
    }

    public double getTurretPosition() {
        return turretTalon.getSelectedSensorPosition();
    }

    public boolean getLimitSwitchValue() {
        return limitSwitchLeft.get();
    }

    public double getAngle() {
        return turretTalon.getSelectedSensorPosition() / Constants.TICKS_PER_ANGLE;
    }

    public double getEncoder() {
        return turretTalon.getSelectedSensorPosition();
    }

    public double getError() {
        return turretController.getPositionError();
    }
}
