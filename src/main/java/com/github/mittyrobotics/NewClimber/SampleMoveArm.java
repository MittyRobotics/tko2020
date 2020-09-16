package com.github.mittyrobotics.NewClimber;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.controller.PIDController;

public class SampleMoveArm {
    public void move(double setpoint){
        WPI_TalonSRX talonSRX1 = new WPI_TalonSRX(0);
        WPI_TalonSRX talonSRX2 = new WPI_TalonSRX(1);
        PIDController primaryController1 = new PIDController(1, 0, 0);
        PIDController primaryController2 = new PIDController(1, 0, 0);
        PIDController auxController = new PIDController(1, 0, 0);
        primaryController1.setSetpoint(setpoint);
        primaryController2.setSetpoint(setpoint);
        auxController.setSetpoint(0);
        while (true){
            double auxSpeed = auxController.calculate(talonSRX1.get() - talonSRX2.get());
            double primarySpeed1 = primaryController1.calculate(talonSRX1.get());
            double primarySpeed2 = primaryController2.calculate(talonSRX2.get());
            talonSRX1.set(primarySpeed1 + auxSpeed);
            talonSRX2.set(primarySpeed2 - auxSpeed);
        }
    }
}
