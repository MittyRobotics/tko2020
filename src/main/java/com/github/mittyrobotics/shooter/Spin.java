package com.github.mittyrobotics.shooter;

import com.github.mittyrobotics.turret.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.CommandBase;

//Spin is just a reference for Spark documentation
//TODO change this code to use the setShooterSpeed() function in ShooterSubsystem instead
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
        controller.getPIDController().setP(com.github.mittyrobotics.turret.Constants.TurretP);
        controller.getPIDController().setI(com.github.mittyrobotics.turret.Constants.TurretI);
        controller.getPIDController().setD(com.github.mittyrobotics.turret.Constants.TurretD);
        controller.getPIDController().setOutputRange(com.github.mittyrobotics.turret.Constants.TurretOutputMin, Constants.TurretOutputMax);
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