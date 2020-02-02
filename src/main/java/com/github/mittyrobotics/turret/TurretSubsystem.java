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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    private static TurretSubsystem instance;
    private WPI_TalonSRX talon1;
    private DigitalInput limitSwitch, limitSwitch2;
    private PIDController controller;

    private TurretSubsystem() {
        super();
        setName("Turret");
    }

    public static TurretSubsystem getInstance() {
        if (instance == null) {
            instance = new TurretSubsystem();
        }
        return instance;
    }

    public void initHardware() {
        //Config talon
        talon1 = new WPI_TalonSRX(Constants.TalonID);
        talon1.config_kP(0, Constants.TurretP);
        talon1.config_kI(0, Constants.TurretI);
        talon1.config_kD(0, Constants.TurretD);
//      limitSwitch = new DigitalInput(Constants.TurretSwitchID);
//      limitSwitch2 = new DigitalInput(Constants.TurretSwitch2ID);
        talon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        //TODO maybe this will fix it:
//      talon1.configFeedbackNotContinuous(true, 0);

        //Initialize PIDController
        controller = new PIDController(Constants.TurretP, Constants.TurretI, Constants.TurretD);
        controller.enableContinuousInput(0, Constants.REVOLUTION_TICKS - 1);
    }

    public void setTurretSpeed(double speed) {
        if (!limitSwitch.get() && !limitSwitch2.get()) {
            talon1.set(ControlMode.PercentOutput, speed);
        } else {
            if (limitSwitch.get()) {
                if (speed > 0) {
                    talon1.set(ControlMode.PercentOutput, speed);
                } else {
                    talon1.set(ControlMode.PercentOutput, 0);
                }
            } else {
                if (speed < 0) {
                    talon1.set(ControlMode.PercentOutput, speed);
                } else {
                    talon1.set(ControlMode.PercentOutput, 0);
                }
            }
        }
    }

    public void setTurretAngle(double angle) {
        angle *= Constants.TICKS_PER_ANGLE;
        angle = capAngleSetpoint(angle);
        controller.setSetpoint(angle);
    }

    public void changeTurretAngle(double angle) {
        setTurretAngle(angle + getAngle());
    }

    /**
     * Updates the turret's {@link PIDController} control loop.
     *
     * This should be called periodically whenever the turret should be running an angle control loop.
     */
    public void updateTurretControlLoop(){
        manualSetTurret(controller.calculate(getEncoderValue()));
    }

    private double capAngleSetpoint(double angle){
        angle %= Constants.REVOLUTION_TICKS;
        if (angle < 0) {
            angle += Constants.REVOLUTION_TICKS;
        }
        return angle;
    }

    public void manualSetTurret(double speed) { //TODO for testing purposes
        if (Math.abs(speed) > 0.05) {
            talon1.set(ControlMode.PercentOutput, speed);
        } else {
            talon1.set(ControlMode.PercentOutput, 0);
        }
    }

    public double getEncoderValue() {
        return talon1.getSelectedSensorPosition();
    }

    public boolean getLimitSwitchValue() {
        return limitSwitch.get();
    }

    public double getAngle() {
        return talon1.getSelectedSensorPosition() / Constants.TICKS_PER_ANGLE;
    }

    public double getError() {
        return controller.getPositionError();
    }
}
