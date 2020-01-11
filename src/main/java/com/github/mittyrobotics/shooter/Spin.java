package com.github.mittyrobotics.shooter;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.shooter.Constants.*;

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
        controller.setP(ControllerP);
        controller.setI(ControllerI);
        controller.setD(ControllerD);
        controller.setOutputRange(ControllerMinRange, ControllerMaxRange);
        controller.setReference(speed, ControlType.kVelocity);
    }
    @Override
    public void end(boolean interrupted){
        controller.setReference(0, ControlType.kVelocity);
        Shooter.getInstance().getspark1().set(0);
    }
    @Override
    public boolean isFinished(){
        return false;
    }
}