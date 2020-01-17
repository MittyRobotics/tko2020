package com.github.mittyrobotics.turret;

import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.CommandBase;

//Spin is just a reference for Spark documentation
public class Spin extends CommandBase {
    private CANSparkMax controller;
    private double speed;
    public Spin(double speed){
        super();
        this.speed = speed;
        addRequirements(ShooterSubsystem.getInstance());
    }
    @Override
    public void initialize(){
        controller = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);
        controller.restoreFactoryDefaults();
        controller.getPIDController().setP(Constants.TurretP);
        controller.getPIDController().setI(Constants.TurretI);
        controller.getPIDController().setD(Constants.TurretD);
        controller.getPIDController().setOutputRange(Constants.TurretOutputMin, Constants.TurretOutputMax);
        controller.getPIDController().setReference(speed, ControlType.kVelocity);
    }



    @Override
    public void end(boolean interrupted){
        controller.getPIDController().setReference(0, ControlType.kVelocity);
//        Shooter.getInstance().getSpark1().set(0);
    }
    @Override
    public boolean isFinished(){
        return false;
    }
}