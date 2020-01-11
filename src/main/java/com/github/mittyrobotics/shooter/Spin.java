package com.github.mittyrobotics.shooter;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Spin extends CommandBase {
    private CANPIDController controller;
    private double speed;
    public Spin(double speed){
        super();
        this.speed = speed;
        addRequirements(Shooter.getInstance());
    }
    @Override
    public void initialize(){
        controller = new CANPIDController(Shooter.getInstance().getspark1());
        controller.setP(0);
        controller.setI(0);
        controller.setD(0);
        controller.setOutputRange(0, 0);
    }
    @Override
    public void execute(){
        controller.setReference(speed, ControlType.kVelocity);
    }
}
