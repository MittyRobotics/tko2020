package com.github.mittyrobotics.turret;

import com.github.mittyrobotics.shooter.Shooter;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.turret.Constants.*;

public class Spin extends CommandBase {
    private CANSparkMax controller;
    private double speed;
    public Spin(double speed){
        super();
        this.speed = speed;
        addRequirements(Shooter.getInstance());
    }
    @Override
    public void initialize(){
        controller = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);
        controller.restoreFactoryDefaults();
        controller.getPIDController().setP(ControllerP);
        controller.getPIDController().setI(ControllerI);
        controller.getPIDController().setD(ControllerD);
        controller.getPIDController().setOutputRange(ControllerMinRange, ControllerMaxRange);
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