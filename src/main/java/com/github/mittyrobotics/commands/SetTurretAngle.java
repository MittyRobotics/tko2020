package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetTurretAngle extends CommandBase {
    private double angle;
    private double threshold;
    public SetTurretAngle(double angle, double threshold){
        this.angle = angle;
        this.threshold = threshold;
        addRequirements(TurretSubsystem.getInstance());
    }

    public SetTurretAngle(double angle){
        this(angle, 2);
    }

    @Override
    public void initialize(){
        TurretSubsystem.getInstance().setTurretAngle(angle);
    }

    @Override
    public void execute(){
        TurretSubsystem.getInstance().updateTurretControlLoop();
    }

    @Override
    public void end(boolean interrupted){
        TurretSubsystem.getInstance().stopMotor();
    }

    @Override
    public boolean isFinished(){
        return Math.abs(TurretSubsystem.getInstance().getError()) < threshold;
    }
}
