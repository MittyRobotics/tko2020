package com.github.mittyrobotics.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;

import javax.crypto.Mac;


public class SetTurretAngle extends CommandBase {
    private double angle;

    public SetTurretAngle(double angle) {
        super();
        this.angle = angle;
        addRequirements(TurretSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        TurretSubsystem.getInstance().changeAngle(angle);
    }

    @Override
    public void end(boolean interrupted) {
        TurretSubsystem.getInstance().manualSetTurret(0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(TurretSubsystem.getInstance().getError()) < .25;
    }
}